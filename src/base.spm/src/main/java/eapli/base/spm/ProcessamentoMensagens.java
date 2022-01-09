package eapli.base.spm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.LinhaProducao;
import java.time.LocalDateTime;

public class ProcessamentoMensagens {

    private boolean FLAG = true;

    public void processarMensagens(String[] args) {
        Timestamp inicio = Utils.obterDataHoraInicio(args);
        Timestamp fim = Utils.obterDataHoraFim(args);
        if (!fim.after(inicio)) {
            throw new IllegalArgumentException("A data e hora de inicio é superior à data e hora de fim!");
        }
        ArrayList<LinhaProducao> lista = (ArrayList<LinhaProducao>) PersistenceContext.repositories().linhasProducao()
                .findAll();
        LinkedList<LinhaProducao> listaLinhas = new LinkedList<>(Utils.obterLinhasProducao(args, lista));
        Thread[] linhas = new Thread[listaLinhas.size()];
        ProcessamentoMensagensLinha processamento = new ProcessamentoMensagensLinha(inicio, fim, listaLinhas);
        for (int i = 0; i < linhas.length; i++) {
            LinhaProducao linhaProducao = listaLinhas.get(i);
            linhas[i] = new Thread(processamento);
            linhas[i].start();
        }
        for (int i = 0; i < linhas.length; i++) {
            try {
                linhas[i].join();
            } catch (InterruptedException e) {
                System.out.println("Erro ao terminar as Threads!");
            }
        }
    }

    public void processarMensagensRecorrente(String[] args) throws InterruptedException {
        //---------
        Timestamp inicio = Timestamp.valueOf("2019-06-10 00:00:01");
        ArrayList<LinhaProducao> lista = (ArrayList<LinhaProducao>) PersistenceContext.repositories().linhasProducao()
                .findAll();
        
        LinkedList<LinhaProducao> listaLinhas = new LinkedList<>(Utils.obterLinhasProducao(args, lista));
        Thread[] linhas = new Thread[listaLinhas.size()];
        ProcessamentoMensagensLinha processamento = new ProcessamentoMensagensLinha(inicio, 5, listaLinhas);
        //---------

        //inicio ciclo infinito
        while (FLAG = true) {
            if (FLAG == false) {
                break;
            }
            
            //run
            for (int i = 0; i < linhas.length; i++) {
                LinhaProducao linhaProducao = listaLinhas.get(i);
                linhas[i] = new Thread(processamento);
                linhas[i].start();
            }
            //paragem do ciclo conssequente join da thread
//          //sleep 5 min para todas as instancias de linhas
//            for (int i = 0; i < linhas.length; i++) {
//                LinhaProducao linhaProducao = listaLinhas.get(i);
//                linhas[i] = new Thread(processamento);
//                linhas[i].sleep(300000);
//                System.out.println(linhas[i].getName() + "sleep");
//            }
        }
        for (int i = 0; i < linhas.length; i++) {
            try {
                linhas[i].join();
            } catch (InterruptedException e) {
                System.out.println("Erro ao terminar as Threads!");
            }
        }
    }
    
    //sair do loop ao desativar a flag
    public void desativarSPMrecorrente() {
        FLAG = false;
    }
    

    public void breakProcessarMensRec() {

    }

}
