package eapli.base.smm;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.EstadoMaquina;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class MonitorizacaoMaquina implements Runnable {

    private final DatagramSocket sock;

    private final String endereco;

    private final Integer timeout;

    private final Integer sleeptime;

    private ListaMaquinas lista;

    public MonitorizacaoMaquina(DatagramSocket s, String e, Integer t, Integer st, ListaMaquinas l) {
        sock = s;
        endereco = e;
        timeout = t;
        lista = l;
        sleeptime = st;
    }

    @Override
    public void run() {
        byte[] mensagem = new byte[6 + 300];
        int i;
        boolean first = true;

        /* É criado um timeout */
        try {
            sock.setSoTimeout(1000 * timeout);
        } catch (SocketException e) {
            System.out.println("Ocorreu um erro na Thread com id " + Thread.currentThread().getId());
            throw new IllegalArgumentException();
        }

        /* É criado um endereço de rede */
        InetAddress serverIP = null;
        try {
            serverIP = InetAddress.getByName(endereco);
        } catch (UnknownHostException ex) {
            System.out.println("Endereço de rede inválido: " + endereco);
            throw new IllegalArgumentException();
        }

        /*
		 * É criado um packet com a mensagem e endereço de rede para enviar a mesma, na
		 * porta 9999 desse endereço
         */
        DatagramPacket udpPacket = new DatagramPacket(mensagem, mensagem.length, serverIP, 9999);

        try {
            while (true) {

                /* É criada uma mensagem com o Formato Geral de Mensagens */
                String HELLO_REQUEST = "hello";
                int length = HELLO_REQUEST.length();
                mensagem[0] = 0;
                mensagem[1] = 0;
                mensagem[2] = 0;
                mensagem[3] = 0;
                mensagem[4] = (byte) (length & 0xFF);
                mensagem[5] = (byte) ((length >> 8) & 0xFF);
                byte[] raw = HELLO_REQUEST.getBytes();
                for (i = 0; i < length; i++) {
                    mensagem[6 + i] = raw[i];
                }
                
                sock.send(udpPacket);

                try {
                    sock.receive(udpPacket);

                    /* É obtida a resposta do endereço da máquina */
                    byte[] resposta = udpPacket.getData();

                    /* Se resposta for ACK, lê a mensagem. Senão, não lê e volta a mandar um request */
                    if (resposta[1] == (byte) 150) {
                        int statusSize = ((resposta[5]) << 8) | (resposta[4]);
                        byte[] statusBytes = new byte[statusSize];
                        for (i = 0; i < statusSize; i++) {
                            statusBytes[i] = resposta[6 + i];
                        }
                        String status = new String(statusBytes);
                        int maquina = ((resposta[3]) << 8) | (resposta[2]);

                        /* Adiciona máquina á lista geral de máquinas */
                        if (first) {
                            if (!lista.adicionarMaquina(maquina)) {
                                throw new IllegalArgumentException();
                            }
                            first = false;
                        }

                        // Criação da instância de EstadoMaquina, com maquina e status. Persistir a
                        // instância na base
                        EstadoMaquina estado = new EstadoMaquina(maquina, status);
                        PersistenceContext.repositories().estadosMaquina().save(estado);
                        System.out.println("O último estado da máquina com endereço " + endereco + " foi persistido!");

                    } else {
                        System.out.println("A máquina com endereço " + endereco + " recusou o pedido!");
                    }

                    /* A Thread espera 30 segundos até poder mandar um novo request */
                    try {
                        Thread.sleep(sleeptime * 1000);
                    } catch (InterruptedException e) {
                        System.out.println("Ocorreu um erro na Thread com id " + Thread.currentThread().getId());
                    }

                } catch (SocketTimeoutException st) {
                    System.out.println("A máquina com endereço " + endereco + " não se encontra disponível!");
                }
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Existe pelo menos 2 máquinas com o mesmo número de identificação único!");
        } catch (Exception ex) {
            System.out.println("Ocorreu um erro no sistema!");
        }
    }
}
