package eapli.base.smm;

import eapli.base.smm.base.Application;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MonitorizacaoMaquinas {

    static int port_base;
    static DatagramSocket sock[];
    static Integer timeout = Application.settings().getSMMTimeout();
    static Integer sleeptime = Application.settings().getSMMSleeptime();
    static List<Integer> listaMaquinas = new ArrayList<>();

    public void monitorizarMaquinas(String args[]) {

        /* A porta base do server é passada por parâmetro. No caso, corresponde ao argumento 0 */
        port_base = Integer.parseInt(args[0]);

        /*
			 * É criada uma lista de endereços de rede e adicionados alguns para serem
			 * monitorizados
         */
        Set<String> enderecosT = new HashSet<>();
        int argsLen = args.length;

        /* Verifica se foram passados endereços por parâmetro*/
        if (argsLen - 1 == 0) {
            throw new IllegalArgumentException("Não foi especificado nenhum endereço de rede!");
        }

        for (int i = 1; i < argsLen; i++) {
            enderecosT.add(args[i]);
        }

        ArrayList<String> enderecos = new ArrayList<>(enderecosT);

        /* É criado um array com socks para cada endereço */
        int i;
        sock = new DatagramSocket[enderecos.size()];

        /* É associado a cada sock a porta para comunicarem com outras aplicações. Cada sock terá uma porta diferente */
        for (i = 0; i < enderecos.size(); i++) {
            try {
                sock[i] = new DatagramSocket(port_base + 100 * i);
            } catch (SocketException ex) {
                System.out.println("Falha ao ligar a porta " + port_base);
                do {
                    sock[i].close();
                    i--;
                } while (i > -1);
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException iae) {
                throw new IllegalArgumentException("O número da porta não se encontra dentro do intervalo válido!");
            }
        }

        ListaMaquinas listaMaquinas = new ListaMaquinas();

        System.out.println("Os dados foram validados com sucesso!");

        /* É criado um array para guardar as Threads que serão criadas */
        Thread[] listaThreads = new Thread[enderecos.size()];

        /* São criadas threads para cada sock e endereço */
        for (i = 0; i < enderecos.size(); i++) {
            listaThreads[i] = new Thread(
                    new MonitorizacaoMaquina(sock[i], enderecos.get(i), timeout, sleeptime, listaMaquinas));
            listaThreads[i].start();
        }

        /* A main thread espera que todas as outras terminem antes de terminar */
        try {
            for (i = 0; i < enderecos.size(); i++) {
                listaThreads[i].join();
            }
        } catch (InterruptedException ie) {
            System.out.println("Falha ao terminar ao fechar as Threads!");
        }

    }
}
