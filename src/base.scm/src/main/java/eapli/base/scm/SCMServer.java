package eapli.base.scm;

import eapli.base.comunicacao.domain.Mensagem;
import eapli.base.comunicacao.repositories.MensagemRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.Maquina;
import eapli.base.producao.repositories.MaquinaRepository;
import java.util.Arrays;
import java.util.Formatter;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.springframework.data.jpa.repository.query.EscapeCharacter;

import java.io.DataOutputStream;

public class SCMServer implements Runnable {

    private Socket s;
    private DataOutputStream sOut;
    private DataInputStream sIn;
    private static final int MAX_BYTES = 300;
    private final MaquinaRepository maquinaRepository = PersistenceContext.repositories().maquinas();
    private final MensagemRepository mensagemRepository = PersistenceContext.repositories().mensagens();
    private static final char PREFIXO_MAQUINA = 'T';

    public SCMServer(Socket cli_s) {
        s = cli_s;
    }

    public void run() {
        long f, i, num, sum;
        InetAddress clientIP;

        clientIP = s.getInetAddress();
        System.out.println("New client connection from " + clientIP.getHostAddress() + ", port number " + s.getPort());
        try {

            sOut = new DataOutputStream(s.getOutputStream());
            sIn = new DataInputStream(s.getInputStream());

            recolherMensagens(sOut, sIn);

            System.out
                    .println("Client " + clientIP.getHostAddress() + ", port number: " + s.getPort() + " disconnected");
            s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void recolherMensagens(DataOutputStream sOut, DataInputStream sIn) throws IOException {

        byte[] recetor = new byte[MAX_BYTES];
        int countAux = 0;

        for (;;) {
            Arrays.fill(recetor, (byte) 0);
            sIn.read(recetor);
            int tamanho = ((recetor[5] & 0xff) << 8) | (recetor[4] & 0xff);

            if (tamanho > 2 && tamanho < MAX_BYTES) {
                if (verifyMachineExists(recetor)) {

                    byte[] mensagemFinalArray = new byte[tamanho];
                    for (int i = 0; i < tamanho; i++) {
                        mensagemFinalArray[i] = recetor[6 + i];
                    }

                    String mensagemFinal = new String(mensagemFinalArray);
                    // se mensagem final é exit ou sair; id maquina continua a ser o mesmo ou existe

                    if (!mensagemFinal.contains("exit") && !mensagemFinal.contains("sair") && countAux > 0) {
                        try {
                            Mensagem mens = ThreadMensagens.getTipoMensagem(mensagemFinal);
                            mensagemRepository.save(mens);
                            System.out.println(mensagemFinal);
                            System.out.println("Mensagem importada com sucesso.");
                        } catch (Exception e) {
                            System.out.println("Formatação da seguinte mensagem incorreta.");
                            System.out.println(mensagemFinal);
                        }
                    }

                } else {
                    System.out.println("Erro ao importar.");
                    break;
                }
                countAux++;
            }
        }

    }

    private boolean verifyMachineExists(byte[] recetor) throws IOException {
        byte[] sender = new byte[MAX_BYTES];
        byte[] raw = new byte[MAX_BYTES];

        Integer a;
        String mensagem;
        int length;

        byte[] id = new byte[2];
        id[0] = recetor[2];
        id[1] = recetor[3];
        short idMaquinaShort = ByteBuffer.wrap(id).order(ByteOrder.LITTLE_ENDIAN).getShort();
        String idMaquinaString = String.format("%c%d", PREFIXO_MAQUINA,idMaquinaShort);
        int tamanho = ((recetor[5] & 0xff) << 8) | (recetor[4] & 0xff);
        byte[] nome = new byte[tamanho];
        for (int i = 0; i < tamanho; i++) {
            nome[i] = recetor[6 + i];
        }
        boolean existsId = maquinaRepository.containsOfIdentity(idMaquinaString);
        if (existsId) {
            a = 150;
            mensagem = "ack";
            length = mensagem.length();
            sender[0] = 0;
            sender[1] = a.byteValue();
            sender[2] = recetor[2];
            sender[3] = recetor[3];
            sender[4] = (byte) (length);
            sender[5] = (byte) ((length >> 8));
            raw = mensagem.getBytes();
            for (int i = 0; i < length; i++) {
                sender[6 + i] = raw[i];
            }
            sOut.write(sender);
        } else {
            System.out.println("Máquina "+idMaquinaString+" não encontrada.");
            a = 151;
            mensagem = "nack";
            length = mensagem.length();
            sender[0] = 0;
            sender[1] = a.byteValue();
            sender[2] = recetor[2];
            sender[3] = recetor[3];
            sender[4] = (byte) (length);
            sender[5] = (byte) ((length >> 8));
            raw = mensagem.getBytes();
            for (int i = 0; i < length; i++) {
                sender[6 + i] = raw[i];
            }
            sOut.write(sender);

        }
        return existsId;

    }
}