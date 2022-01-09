package eapli.base.spm;

import eapli.base.comunicacao.domain.Mensagem;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.stock.domain.Deposito;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.Produto;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidacaoMensagens {
    
    private int erros;

    public void validarMensagens(ArrayList<Mensagem> listaMsg) {

        ArrayList<Produto> listaProdutos = (ArrayList<Produto>) PersistenceContext.repositories().produtos().findAll();
        ArrayList<Deposito> listaDepositos = (ArrayList<Deposito>) PersistenceContext.repositories().deposito().findAll();
        ArrayList<OrdemProducao> listaOrdens = (ArrayList<OrdemProducao>) PersistenceContext.repositories().ordensProducao().findAll();
        ArrayList<MateriaPrima> listaMateriasPrimas = (ArrayList<MateriaPrima>) PersistenceContext.repositories().materiasPrimas().findAll();

        for (Mensagem msg : listaMsg) {
            if (msg.getIdOrdemProducao() != null) {
                verificarSeOrdemExiste(listaOrdens, msg);
            }
            Field[] attributes = msg.getClass().getDeclaredFields();
            for (Field attribute : attributes) {
                if (attribute.getName().equals("idMateriaPrima")) {
                    verificarSeMateriaPrimaExiste(listaMateriasPrimas, msg);
                }
                if (attribute.getName().equals("idDeposito")) {
                    verificarSeDepositoExiste(listaDepositos, msg);
                }
                if (attribute.getName().equals("idProduto")) {
                    verificarSeProdutoExiste(listaProdutos, msg);
                }
            }
        }
        
        if(erros > 0) {
            throw new IllegalArgumentException("Não é possível continuar o processamento, por culpa de erros de dados nas mensagens!");
        }
    }

    private void verificarSeOrdemExiste(ArrayList<OrdemProducao> lista, Mensagem m) {
        boolean val = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdOrdemProducao().equals(m.getIdOrdemProducao())) {
                val = true;
            }
        }
        if (!val) {
            CriacaoNotificacoesErroService ne = new CriacaoNotificacoesErroService();
            ne.criarNotificacoesErro(ValidacaoMensagensConstants.MENSAGEM_ORDEM_INEXISTENTE, m);
            erros++;
        }
    }

    private void verificarSeMateriaPrimaExiste(ArrayList<MateriaPrima> lista, Mensagem m) {
        boolean val = false;
        Method p = getMethod("getIdMateriaPrima", m);
        if (p != null) {
            for (int i = 0; i < lista.size(); i++) {
                try {
                    if (lista.get(i).getCodigoInterno().equals(p.invoke(m))) {
                        val = true;
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(ValidacaoMensagens.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!val) {
                CriacaoNotificacoesErroService ne = new CriacaoNotificacoesErroService();
                ne.criarNotificacoesErro(ValidacaoMensagensConstants.MENSAGEM_MATERIA_PRIMA_INEXISTENTE, m);
                erros++;
            }
        }
    }

    private void verificarSeDepositoExiste(ArrayList<Deposito> lista, Mensagem m) {
        boolean val = false;
        boolean nul = false;
        Method p = getMethod("getIdDeposito", m);
        if (p != null) {
            for (int i = 0; i < lista.size(); i++) {
                try {
                    if(p.invoke(m) == null && m.getClass().getName().equals("MensagemConsumo")) {
                        nul = true;
                    }
                    if (lista.get(i).getCodigoDeposito().equals(p.invoke(m))) {
                        val = true;
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(ValidacaoMensagens.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!val && !nul) {
                CriacaoNotificacoesErroService ne = new CriacaoNotificacoesErroService();
                ne.criarNotificacoesErro(ValidacaoMensagensConstants.MENSAGEM_DEPOSITO_INEXISTENTE, m);
                erros++;
            }
        }
    }

    private void verificarSeProdutoExiste(ArrayList<Produto> lista, Mensagem m) {
        boolean val = false;
        Method p = getMethod("getIdProduto", m);
        if (p != null) {
            for (int i = 0; i < lista.size(); i++) {
                try {
                    if (lista.get(i).getCodigoFabrico().equals(p.invoke(m))) {
                        val = true;
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(ValidacaoMensagens.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!val) {
                CriacaoNotificacoesErroService ne = new CriacaoNotificacoesErroService();
                ne.criarNotificacoesErro(ValidacaoMensagensConstants.MENSAGEM_PRODUTO_INEXISTENTE, m);
                erros++;
            }
        }
    }

    private Method getMethod(String nome, Mensagem m) {
        Method p = null;
        try {
            p = m.getClass().getMethod(nome);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(ValidacaoMensagens.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

}

