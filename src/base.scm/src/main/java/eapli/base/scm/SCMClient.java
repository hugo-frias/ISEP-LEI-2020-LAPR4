package eapli.base.scm;

import java.io.*;
import java.net.*;
import java.nio.ByteOrder;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

import org.springframework.data.jpa.repository.query.EscapeCharacter;

import java.nio.ByteBuffer;

import java.io.DataOutputStream;

public class SCMClient implements Runnable {
    private SSLSocket s;
    private static final int MAX_BYTES = 300;
    private short idMaquina;
    private String PATH_CONFIGURACAO = ".//base.scm//fileConf";
    private static final int CODE_CONFIG = 2;
    private static final int CODE_HELLO = 0;
    private static final char PREFIXO_MAQUINA = 'T';

    public SCMClient(SSLSocket socket, short idMaquina) {
        s = socket;
        this.idMaquina = idMaquina;
    }

    public void run() {

        try {

            s.startHandshake();

            DataOutputStream sOut = new DataOutputStream(s.getOutputStream());
            DataInputStream sIn = new DataInputStream(s.getInputStream());

            enviarConfiguracaoAMaquina(sOut, sIn);

            s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void enviarConfiguracaoAMaquina(DataOutputStream sOut, DataInputStream sIn) {
        System.out.println("A enviar pedido de configuração.");
        enviaPedidoConfiguracao(sOut);
        if (checkResposta(sIn)) {
            System.out.println("A enviar configuração.");
            enviaConfiguracao(sOut);
            if(checkResposta(sIn)){
                System.out.println("Configuração recebida pela máquina.");
            } else{
                System.out.println("A máquina rejeitou a configuração.");
            }
        } else {
            System.out.println("Request denied.");
        }
    }

    private void enviaPedidoConfiguracao(DataOutputStream sOut) {
        try{
        sOut.write(criarMensagem("HELLO", idMaquina, CODE_HELLO));

        } catch (IOException ex) {
            System.out.println("Erro ao enviar pedido de configuração.");
            ex.printStackTrace();
        }

    }

    private void enviaConfiguracao(DataOutputStream sOut) {
        String configuracao = getConfiguracao();
        if (!configuracao.equals(null) && configuracao.length()==16) {
            try{
            sOut.write(criarMensagem(configuracao, idMaquina, CODE_CONFIG));
            System.out.println("Configuração enviada.");
            } catch (IOException ex) {
            System.out.println("Erro ao enviar configuração.");
            }
        } else{
            System.out.println("Erro ao importar configuração.");
        }
    }

    private String getConfiguracao() {
        try {
            File ficheiro = new File(PATH_CONFIGURACAO + "//conf." +PREFIXO_MAQUINA+ idMaquina);
            Scanner scan = new Scanner(ficheiro);
            if (scan.hasNextLine()) {
                String configuracao = scan.nextLine();
                scan.close();
                return configuracao;
            } else {
                System.out.println("Formatação do ficheiro incorreta.");
                scan.close();
                return null;
            }
            
        } catch (IOException ex) {
            System.out.println("Ficheiro não encontrado.");
            return null;
        }
    }

    private boolean checkResposta(DataInputStream sIn) {
        try {
            byte[] resposta = new byte[MAX_BYTES];
            sIn.readFully(resposta);
            int code = 0;
            code = Byte.toUnsignedInt(resposta[1]);
            
            if (code == 151) {
                System.out.println("ERRO - código NACK recebido.");
                return false;
            } else {
                System.out.println("código ACK recebido.");
                return true;
            }
        } catch (IOException ex) {
            return false;
        }
    }

    private byte[] criarMensagem(String mensagem, short id, int code) {
        byte[] sender = new byte[MAX_BYTES];
        byte[] idAux = new byte[2];
        idAux =  ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(id).array();
        Integer codeAux = code;
        int length = mensagem.length();
        sender[0] = 0;
        sender[1] = codeAux.byteValue();
        sender[2] = idAux[0];
        sender[3] = idAux[1];
        sender[4] = (byte) (length);
        sender[5] = (byte) ((length >> 8));
        byte[] raw = mensagem.getBytes();
        for (int i = 0; i < length; i++) {
            sender[6 + i] = raw[i];
        }
        return sender;
    }

}