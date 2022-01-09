package eapli.base.spm;

import eapli.base.comunicacao.domain.Mensagem;
import eapli.base.comunicacao.repositories.MensagemRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.LinhaProducao;
import eapli.base.producao.domain.Maquina;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import static java.lang.Thread.sleep;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

public class ProcessamentoMensagensLinha implements Runnable {

    private final Timestamp dataHoraInicio;

    private final Timestamp dataHoraFim;

    private final LinkedList<LinhaProducao> linhaProducao;

    private final boolean recorrente;

    private final int recorrencia;

    private final OrdemProducaoRepository ordRepo;

    private final MensagemRepository msgRepo;

    private static boolean FLAG = true;
 
    public ProcessamentoMensagensLinha(Timestamp dataHoraInicio, Timestamp dataHoraFim, LinkedList<LinhaProducao> linhaProducao) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.linhaProducao = linhaProducao;
        this.recorrente = false;
        this.recorrencia = 0;
        this.ordRepo = PersistenceContext.repositories().ordensProducao();
        this.msgRepo = PersistenceContext.repositories().mensagens();
    }

    public ProcessamentoMensagensLinha(Timestamp dataHoraInicio, int recorrencia, LinkedList<LinhaProducao> linhaProducao) {
        this.dataHoraInicio = dataHoraInicio;
        this.recorrencia = recorrencia;
        this.linhaProducao = linhaProducao;
        this.recorrente = true;
        this.dataHoraFim = null;
        this.ordRepo = PersistenceContext.repositories().ordensProducao();
        this.msgRepo = PersistenceContext.repositories().mensagens();
    }

    public Timestamp getDataHoraInicio() {
        return dataHoraInicio;
    }

    public Timestamp getDataHoraFim() {
        return dataHoraFim;
    }

    public synchronized LinhaProducao getLinhaProducao() {
        LinhaProducao linha = linhaProducao.getFirst();
        linhaProducao.removeFirst();
        return linha;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Timestamp inicio = getDataHoraInicio();
                Timestamp fim = getDataHoraFim();
                LinhaProducao linha = getLinhaProducao();
                ArrayList<Mensagem> listaMsg = getListaMensagens(inicio, fim, linha);
                OrdemProducao ordem = processarMensagens(listaMsg, linha);
                if(recorrente) {
                    try {
                        processarMensagens(listaMsg, linha);
                        sleep(recorrencia*1000); //Em segundos
                        break;
                    } catch (InterruptedException ex) {
                        break;
                    }
                } else {
                    System.out.println("O processamento foi efetuado com sucesso!");
                    break;
                }
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private ArrayList<Mensagem> getListaMensagens(Timestamp inicio, Timestamp fim, LinhaProducao linhaProducao) {
        LinkedList<Maquina> listaMaquinas = new LinkedList<>(linhaProducao.getListaMaquinas());
        LinkedList<String> listaMaquinasIds = new LinkedList<>();
        for (int i = 0; i < listaMaquinas.size(); i++) {
            listaMaquinasIds.add(listaMaquinas.get(i).getCodigoInterno());
        }
        String linha = linhaProducao.getIdLinhaProducao();
        if (listaMaquinas.isEmpty()) {
            throw new IllegalArgumentException("Não existem máquinas para a linha de produção com id " + linha + "!");
        }
        Mensagem ultima = PersistenceContext.repositories().mensagens().findPrimeiraMensagemAntesDeTimestamp(inicio);
        if (ultima != null) {
            throw new IllegalArgumentException("Existem mensagens anteriores à data e hora iniciais que ainda não foram processadas!");
        }
        ArrayList<Mensagem> listaMsg = msgRepo.findByProcessadasPorMaquinasETempo(inicio, fim, listaMaquinasIds);
        if (listaMsg == null) {
            throw new IllegalArgumentException("Não existem mensagens no sistema para os parâmetros recebidos!");
        }
        if (listaMsg.isEmpty()) {
            throw new IllegalArgumentException("Não existem mensagens no sistema para os parâmetros recebidos!");
        }
        return listaMsg;
    }

    private OrdemProducao processarMensagens(ArrayList<Mensagem> listaMsg, LinhaProducao linhaProducao) {
        OrdemProducao ordem;
        ValidacaoMensagens vMsg = new ValidacaoMensagens();
        vMsg.validarMensagens(listaMsg);
        EnriquecimentoMensagens eMsg = new EnriquecimentoMensagens();
        String idOrdem = eMsg.enriquecerMensagens(listaMsg, linhaProducao);
        synchronized (this) {
            AtualizacaoOrdemProducao aOrdens = new AtualizacaoOrdemProducao();
            ordem = aOrdens.atualizarOrdemProducao(listaMsg, idOrdem, linhaProducao);
            ordRepo.save(ordem);
        }
        persistirMensagensEnriquecidas(listaMsg);
        return ordem;
    }

    private void persistirMensagensEnriquecidas(ArrayList<Mensagem> lista) {
        lista.forEach((m) -> {
            m.processar();
            msgRepo.save(m);
        });
    }

}
