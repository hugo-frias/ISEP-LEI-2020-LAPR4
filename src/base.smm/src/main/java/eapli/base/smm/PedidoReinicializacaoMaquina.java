package eapli.base.smm;

import eapli.base.smm.base.Application;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoReinicializacaoMaquina {

    static int port_base;
    static String endereco;
    static DatagramSocket sock;
    static Integer timeout = Application.settings().getSMMTimeout();
    static Integer sleeptime = Application.settings().getSMMSleeptime();

    public void reiniciarMaquina(String args[]) {

        /* A porta base do server é passada por parâmetro. No caso, corresponde ao argumento 1 */
        port_base = Integer.parseInt(args[1]);

        int argsLen = args.length;

        /* Verifica se foi passado 1 endereço por parâmetro*/
        if (argsLen - 1 == 0) {
            throw new IllegalArgumentException("Não foi especificado nenhum endereço de rede!");
        }

        endereco = args[2];

        /* É criado um sock para o endereço */
        int i;
        try {
            sock = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(PedidoReinicializacaoMaquina.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Os dados foram validados com sucesso!");

        /* É criado um array de bytes que irá suportar a mensagem a ser enviada */
        byte[] mensagem = new byte[6 + 300];

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

        /* É obtido o endereço de rede a partir do argumento passados */
        InetAddress serverIP = null;
        try {
            serverIP = InetAddress.getByName(endereco);
        } catch (UnknownHostException ex) {
            System.out.println("Endereço de rede inválido: " + endereco);
            throw new IllegalArgumentException();
        }

        /* É criado um packet com a mensagem e endereço de rede para onde a mesma será enviada, na porta 9999 desse endereço*/
        DatagramPacket udpPacket = new DatagramPacket(mensagem, mensagem.length, serverIP, port_base);

        try {
            while (true) {
                sock.send(udpPacket);

                try {
                    sock.receive(udpPacket);

                    /* É obtida a resposta do endereço da máquina */
                    byte[] resposta = udpPacket.getData();

                    /* Se resposta for ACK, lê a mensagem. Senão, não lê e volta a mandar um request */
                    if (resposta[1] == (byte) 150) {

                        int id = ((resposta[3]) << 8) | (resposta[2]);

                        /* É criada uma mensagem com o Formato Geral de Mensagens */
                        String RESET_REQUEST = "reset";
                        length = RESET_REQUEST.length();
                        mensagem[0] = 0;
                        mensagem[1] = (byte) 3;
                        mensagem[2] = (byte) (id & 0xFF);
                        mensagem[3] = (byte) ((id >> 8) & 0xFF);
                        mensagem[4] = (byte) (length & 0xFF);
                        mensagem[5] = (byte) ((length >> 8) & 0xFF);
                        raw = RESET_REQUEST.getBytes();
                        for (i = 0; i < length; i++) {
                            mensagem[6 + i] = raw[i];
                        }
                        
                        /* É criado um packet com a nova mensagem, para ser novamente enviada para o endereço da máquina*/
                        udpPacket = new DatagramPacket(mensagem, mensagem.length, serverIP, port_base);
                        sock.send(udpPacket);

                        /* O server espera pela resposta da máquina. Se exceder o timeout, o server volta a enviar um pedido hello*/
                        sock.receive(udpPacket);
                        
                        /* É obtida a resposta do endereço da máquina*/
                        resposta = udpPacket.getData();
                        
                        /* Se a resposta for ACK, significa que o pedido reset foi aceite. Senão, recusado*/
                        if(resposta[1] == (byte) 150) {
                            System.out.println("O pedido de reset foi aceite com sucesso!");
                        } else {
                            System.out.println("O pedido de reset foi rejeitado!");
                        }
                        
                        /* Após a receção da resposta, o sistema termina*/
                        break;

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
        } catch (Exception ex) {
            System.out.println("Ocorreu um erro no sistema!");
        }

    }
}
