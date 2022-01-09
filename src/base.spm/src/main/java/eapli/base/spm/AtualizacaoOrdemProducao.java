package eapli.base.spm;

import eapli.base.comunicacao.domain.Mensagem;
import eapli.base.comunicacao.domain.MensagemConsumo;
import eapli.base.comunicacao.domain.MensagemEntregaProducao;
import eapli.base.comunicacao.domain.MensagemEstorno;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.ExecucaoOrdemProducao;
import eapli.base.producao.domain.LinhaProducao;
import eapli.base.producao.domain.Maquina;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.domain.TempoExecucao;
import eapli.base.stock.domain.Deposito;
import eapli.base.stock.domain.Estorno;
import eapli.base.stock.domain.FichaProducao;
import eapli.base.stock.domain.Lote;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.MovimentoStock;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.repositories.DepositoRepository;
import eapli.base.stock.repositories.LoteRepository;
import eapli.base.stock.repositories.MateriaPrimaRepository;
import eapli.base.stock.repositories.ProdutoRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AtualizacaoOrdemProducao {

    private final ProdutoRepository repoP = PersistenceContext.repositories().produtos();
    private final MateriaPrimaRepository repoMp = PersistenceContext.repositories().materiasPrimas();
    private final LoteRepository repoL = PersistenceContext.repositories().lotes();
    private final DepositoRepository repoD = PersistenceContext.repositories().deposito();

    public OrdemProducao atualizarOrdemProducao(ArrayList<Mensagem> lista, String idOrdem, LinhaProducao linha) {
        OrdemProducao ordem = PersistenceContext.repositories().ordensProducao().ofIdentity(idOrdem).get();
        ExecucaoOrdemProducao execucao = ordem.getExecucaoOrdemProducao();
        atualizarOrdemComMaquinasETempo(lista, execucao, linha);
        atualizarOrdemComLinhaProducao(execucao, linha);
        atualizarOrdemComConsumos(lista, execucao);
        FichaProducao ficha = ordem.getProduto().getFichaProducao();
        if (ficha == null) {
            throw new IllegalArgumentException("Não existe nenhuma ficha de produção associada ao produto!");
        }
        atualizarOrdemComDesviosMateriaPrima(ordem, ficha);
        atualizarOrdemComDesviosProduto(ordem, ficha);
        atualizarOrdemComLotes(lista, execucao);
        atualizarOrdemComEstornos(lista, execucao);
        atualizarOrdemComProdutosDepositos(lista, execucao);
        atualizarOrdemComMovimentosStockEntrada(lista, execucao);
        atualizarOrdemComMovimentosStockSaida(lista, execucao);
        atualizarOrdemComEstado(lista, execucao, linha, ordem);
        return ordem;
    }

    private void atualizarOrdemComMaquinasETempo(ArrayList<Mensagem> lista, ExecucaoOrdemProducao execucao,
            LinhaProducao linha) {
        ArrayList<Maquina> listaMaquinas = new ArrayList<>(linha.getListaMaquinas());
        TempoExecucao tempo;
        for (Maquina maquina : listaMaquinas) {
            for (Mensagem m : lista) {
                if (m.getClass().getSimpleName().equals("MensagemInicioAtividade")
                        && m.getIdMaquina().equals(maquina.getCodigoInterno())) {
                    tempo = new TempoExecucao(m.getDataHora(), null);
                    execucao.adicionarMaquinaComTempo(maquina, tempo);
                }
                if (m.getClass().getSimpleName().equals("MensagemFimAtividade")
                        && m.getIdMaquina().equals(maquina.getCodigoInterno())) {
                    tempo = execucao.getListaMaquinasTempo().get(maquina);
                    tempo.setFim(m.getDataHora());
                    tempo.calcularTotalBruto();
                    tempo.calcularTotalEfetivo();
                }
                if (m.getClass().getSimpleName().equals("MensagemParagem")
                        && m.getIdMaquina().equals(maquina.getCodigoInterno())) {
                    tempo = execucao.getListaMaquinasTempo().get(maquina);
                    tempo.adicionarParagem(m.getDataHora());
                }
                if (m.getClass().getSimpleName().equals("MensagemRetomaAtividade")
                        && m.getIdMaquina().equals(maquina.getCodigoInterno())) {
                    tempo = execucao.getListaMaquinasTempo().get(maquina);
                    tempo.adicionarRetoma(m.getDataHora());
                }
            }
        }
    }

    private void atualizarOrdemComLinhaProducao(ExecucaoOrdemProducao execucao, LinhaProducao linha) {
        execucao.setLinhaProducao(linha);
    }

    private void atualizarOrdemComConsumos(ArrayList<Mensagem> lista, ExecucaoOrdemProducao execucao) {
        for (Mensagem m : lista) {
            if (m.getClass().getSimpleName().equals("MensagemConsumo")) {
                MensagemConsumo m1 = (MensagemConsumo) m;
                MateriaPrima mp = repoMp.ofIdentity(m1.getIdMateriaPrima()).get();
                if (mp == null) {
                    Produto p = repoP.ofIdentity(m1.getIdMateriaPrima()).get();
                    execucao.adicionarConsumoProduto(p, m1.getQuantidade());
                } else {
                    execucao.adicionarConsumoMateriaPrima(mp, m1.getQuantidade());
                }
            }
        }
    }

    private void atualizarOrdemComDesviosMateriaPrima(OrdemProducao ordem, FichaProducao ficha) {
        int quantidade, quantidadeConsumo, quantidadeDesvio;
        Map<MateriaPrima, Integer> consumosMateriaPrima = ordem.getExecucaoOrdemProducao()
                .getListaConsumosMateriaPrima();
        Map<MateriaPrima, Integer> listaMateriasPrimas = ficha.getListaMateriasPrimas();
        for (MateriaPrima mp : listaMateriasPrimas.keySet()) {
            quantidade = listaMateriasPrimas.get(mp);
            for (MateriaPrima id : consumosMateriaPrima.keySet()) {
                quantidadeConsumo = consumosMateriaPrima.get(id);
                quantidadeDesvio = quantidadeConsumo - quantidade;
                ordem.getExecucaoOrdemProducao().adicionarDesvioMateriaPrima(id, quantidadeDesvio);
            }
        }
    }

    private void atualizarOrdemComDesviosProduto(OrdemProducao ordem, FichaProducao ficha) {
        int quantidade, quantidadeConsumo, quantidadeDesvio;
        Map<Produto, Integer> consumosProduto = ordem.getExecucaoOrdemProducao().getListaConsumosProduto();
        Map<Produto, Integer> listaProdutos = ficha.getListaProdutos();
        for (Produto mp : listaProdutos.keySet()) {
            quantidade = listaProdutos.get(mp);
            for (Produto id : consumosProduto.keySet()) {
                quantidadeConsumo = consumosProduto.get(id);
                quantidadeDesvio = quantidadeConsumo - quantidade;
                ordem.getExecucaoOrdemProducao().adicionarDesvioProduto(id, quantidadeDesvio);
            }
        }
    }

    private void atualizarOrdemComLotes(ArrayList<Mensagem> lista, ExecucaoOrdemProducao execucao) {
        Lote l = null;
        for (Mensagem m : lista) {
            if (m.getClass().getSimpleName().equals("MensagemEntregaProducao")) {
                Method mIdLote = null, mIdProduto = null, mQuantidade = null;
                try {
                    mIdLote = m.getClass().getMethod("getIdLote");
                    mIdProduto = m.getClass().getMethod("getIdProduto");
                    mQuantidade = m.getClass().getMethod("getQuantidade");
                } catch (NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
                String idLote = null, idProduto = null;
                int quantidade = 0;
                try {
                    idLote = (String) mIdLote.invoke(m);
                    idProduto = (String) mIdProduto.invoke(m);
                    quantidade = (int) mQuantidade.invoke(m);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException e) {
                    e.printStackTrace();
                }
                if (idLote != null) {
                    Optional<Lote> loteRepo = repoL.ofIdentity(idLote);
                    if (!loteRepo.isPresent()) {
                        l = new Lote(idProduto, idLote);
                    } else {
                        l = loteRepo.get();
                    }
                    if (!execucao.getListaLotes().contains(l)) {
                        l.AdicionarQuantidade(quantidade);
                        execucao.adicionarLote(l);
                        execucao.adicionarQuantidadeProduzida(quantidade);
                    } else {
                        l = execucao.getListaLotes().get(execucao.getListaLotes().indexOf(l));
                        l.AdicionarQuantidade(quantidade);
                        execucao.adicionarQuantidadeProduzida(quantidade);
                    }
                }
            }
        }
    }

    private void atualizarOrdemComEstornos(ArrayList<Mensagem> lista, ExecucaoOrdemProducao execucao) {
        for (Mensagem m : lista) {
            if (m.getClass().getSimpleName().equals("MensagemEstorno")) {
                List<Deposito> listaDepositos = execucao.getListaDepositos();
                MensagemEstorno m1 = (MensagemEstorno) m;
                String idDeposito = m1.getIdDeposito();
                Deposito d = repoD.ofIdentity(idDeposito).get();
                if (!listaDepositos.contains(d)) {
                    execucao.adicionarDeposito(d);
                }
                Deposito d1 = listaDepositos.get(listaDepositos.indexOf(d));
                Estorno estorno = new Estorno(m1.getIdMateriaPrima(), m1.getQuantidade());
                d1.adicionarEstorno(estorno);              
            }
        }
    }

    private void atualizarOrdemComProdutosDepositos(ArrayList<Mensagem> lista, ExecucaoOrdemProducao execucao) {
        for (Mensagem m : lista) {
            if (m.getClass().getSimpleName().equals("MensagemEntregaProducao")) {
                List<Deposito> listaDepositos = execucao.getListaDepositos();
                MensagemEntregaProducao m1 = (MensagemEntregaProducao) m;
                String idDeposito = m1.getIdDeposito();
                Deposito d = repoD.ofIdentity(idDeposito).get();
                if (!listaDepositos.contains(d)) {
                    execucao.adicionarDeposito(d);
                }
                Deposito d1 = listaDepositos.get(listaDepositos.indexOf(d));
                Produto p = repoP.ofIdentity(m1.getIdProduto()).get();
                d1.adicionarProduto(p, m1.getQuantidade());             
            }
        }
    }

    private void atualizarOrdemComMovimentosStockEntrada(ArrayList<Mensagem> lista, ExecucaoOrdemProducao execucao) {
        for (Mensagem m : lista) {
            if (m.getClass().getSimpleName().equals("MensagemEntregaProducao")) {
                MensagemEntregaProducao m1 = (MensagemEntregaProducao) m;
                MovimentoStock l = new MovimentoStock(m1.getIdDeposito(), m1.getIdProduto(), m1.getQuantidade(), 0,m1.getDataHora());
                execucao.adicionarMovimentoStock(l);
            }
        }
    }

    private void atualizarOrdemComMovimentosStockSaida(ArrayList<Mensagem> lista, ExecucaoOrdemProducao execucao) {
        for (Mensagem m : lista) {
            if (m.getClass().getSimpleName().equals("MensagemConsumo")) {
                MensagemConsumo m1 = (MensagemConsumo) m;
                MovimentoStock l = new MovimentoStock(m1.getIdDeposito(), m1.getIdMateriaPrima(), 0, m1.getQuantidade(),m1.getDataHora());
                execucao.adicionarMovimentoStock(l);
            }
        }
    }

    private void atualizarOrdemComEstado(ArrayList<Mensagem> lista, ExecucaoOrdemProducao execucao, LinhaProducao linha, OrdemProducao ordem) {
        ArrayList<Maquina> listaMaquinas = new ArrayList<>(linha.getListaMaquinas());
        String idMaquinaInicial = listaMaquinas.get(0).getCodigoInterno();
        String idMaquinaFinal = listaMaquinas.get(listaMaquinas.size() - 1).getCodigoInterno();
        for (Mensagem m : lista) {
            if (m.getIdMaquina().equals(idMaquinaInicial) && m.getClass().getSimpleName().equals("MensagemInicioAtividade")) {
                TempoExecucao tempo = new TempoExecucao(m.getDataHora(), null);
                execucao.adicionarTempoExecucao(tempo);
                ordem.setDataEmissao(m.getDataHora().toLocalDateTime().toLocalDate());
                execucao.emExecucao();
            }
            if (m.getIdMaquina().equals(idMaquinaFinal) && m.getClass().getSimpleName().equals("MensagemFimAtividade")) {
                TempoExecucao tempo = execucao.getTempoExecucao();
                tempo.setFim(m.getDataHora());
                tempo.calcularTotalBruto();
                Map<Timestamp, Timestamp> listaParagens = execucao.obterListaParagens();
                tempo.setListaParagens(listaParagens);
                tempo.calcularTotalEfetivo();
                execucao.concluida();
            }
        }
    }
}
