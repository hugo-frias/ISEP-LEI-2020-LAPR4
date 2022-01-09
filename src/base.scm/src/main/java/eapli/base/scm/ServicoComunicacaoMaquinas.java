/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.scm;


import eapli.base.comunicacao.domain.Mensagem;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

public class ServicoComunicacaoMaquinas {
    static InetAddress serverIP;
    static SSLSocket sslSocket;
    static ServerSocket sock;
    static final String TRUSTED_SERVER=".//base.scm//certificados//server_J.jks";
    static final String SERVER_PASS="forgotten";
    static final String CLIENT_PASS="forgotten";



    /*
    Argumentos:
    arg[0] - nome do ficheiro .jks do cliente
    arg[1] - IP do server com o qual vai comunicar
    arg[2] - ID da máquina
    arg[3] - Porta do servidor (com o qual vai comunicar no UC3010 e o seu próprio no UC de recolha de mensagens)
    */

    public static void main(String[] args) throws Exception {
        
        /*
        No 4º argumento vai-se selecionar a função a rodar. Se o 4º elemento for:
        1 - Importar mensagens de máquinas para a base de dados
        2 - Recolha de mensagens
        3 - Envio de configuração
        */
        switch(Integer.parseInt(args[4])){
            
            case 1:
            ImportarMensagensMaquinasController ic = new ImportarMensagensMaquinasController();
            ic.importarMensagensDeMaquinas();
            case 2:
            SSL_recolhaMensagens(Integer.parseInt(args[3]));
            case 3:
            envioConfiguracao(args);       

        }        
    }

    public static void envioConfiguracao(String[] args){
        if(args.length!=5){
            
			System.out.println("Client name is required as first argument (a matching keystore must exist)" );
			System.out.println("Server IPv4/IPv6 address/DNS name is required as second argument");
			System.out.println("Machine ID is required as third argument");
            System.out.println("Server port is required as fourth argument");
			System.exit(1); 
        }

        System.out.println(args[0]+" "+args[1]+" "+args[2]+" "+args[3]);
		// Trust these certificates provided by servers
		System.setProperty("javax.net.ssl.trustStore",".//base.scm//certificados//"+args[0]+".jks");
		System.setProperty("javax.net.ssl.trustStorePassword",CLIENT_PASS);

		// Use this certificate and private key for client certificate when requested by the server
		System.setProperty("javax.net.ssl.keyStore",".//base.scm//certificados//"+args[0]+".jks");
    		System.setProperty("javax.net.ssl.keyStorePassword",CLIENT_PASS);

		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();

		try { serverIP = InetAddress.getByName(args[1]); }
		catch(UnknownHostException ex) {
			System.out.println("Invalid server specified: " + args[1]);
			System.exit(1); }


		try {
            sslSocket = (SSLSocket) sf.createSocket(serverIP,Integer.parseInt(args[3]));
		}
		catch(IOException ex) {
                    ex.printStackTrace();
            		System.out.println("Failed to connect to: " + args[1] + ":" + args[3]);
            		System.out.println("Application aborted.");
            		System.exit(1);
            	}

        System.out.println("Connected to: " + args[1] + ":" + args[3]);
        
        
        System.out.println("Enviar configurações");
            new Thread(new SCMClient(sslSocket, Short.parseShort(args[2]))).start();
        
        
		
    }


    public static void recolhaMensagens(int serverPort) throws IOException {
        Socket cliSock;

        try { sock = new ServerSocket(serverPort); }
        catch(IOException ex) {
            System.out.println("Failed to open server socket");
            System.exit(1);
        }

        while(true) {
            System.out.println("Á espera de mensagens");
            cliSock=sock.accept();
            new Thread(new SCMServer(cliSock)).start();
        }
    }

    public static void SSL_recolhaMensagens(int serverPort) throws IOException {
        SSLServerSocket sock = null;
        Socket cliSock;



        // Trust these certificates provided by authorized clients
        System.setProperty("javax.net.ssl.trustStore", TRUSTED_SERVER);
        System.setProperty("javax.net.ssl.trustStorePassword",SERVER_PASS);


        // Use this certificate and private key as server certificate
        System.setProperty("javax.net.ssl.keyStore",TRUSTED_SERVER);
        System.setProperty("javax.net.ssl.keyStorePassword",SERVER_PASS);

        SSLServerSocketFactory sslF = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try {
            sock = (SSLServerSocket) sslF.createServerSocket(serverPort);
            sock.setNeedClientAuth(true);
        }
        catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Server failed to open local port " + serverPort);
            System.exit(1);
        }


        while(true) {
            System.out.println("Á espera de mensagens");
            cliSock = sock.accept();
            new Thread(new SCMServer(cliSock)).start();
        }
    }
}
