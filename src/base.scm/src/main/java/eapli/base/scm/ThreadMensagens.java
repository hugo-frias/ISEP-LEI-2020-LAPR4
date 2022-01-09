package eapli.base.scm;

import eapli.base.comunicacao.domain.*;
import eapli.base.comunicacao.repositories.MensagemRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import org.apache.commons.lang3.time.DateUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class ThreadMensagens extends Thread{

    private String PATH_FICHEIROS_DIRETORIO_INICIAL = ".//base.scm//inicial";
    private String nameFile;
    private final String PATH_FICHEIROS_DIRETORIO_FINAL =  ".//base.scm//final";
    private String PATH_FICHEIROS_LOG_ERRO;
    private File LogErro;
    private static BufferedWriter w;
    private final MensagemRepository mensagemRepository = PersistenceContext.repositories().mensagens();


    public ThreadMensagens(String name) {
        super(name);
        nameFile = name;
        PATH_FICHEIROS_LOG_ERRO = ".//base.scm//final"+"//"+"logDeErros-"+nameFile+".txt";
        PATH_FICHEIROS_DIRETORIO_INICIAL += "//"+name;
        try {
            LogErro = new File(PATH_FICHEIROS_LOG_ERRO);
            w = new BufferedWriter(new FileWriter(LogErro));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            System.out.println(PATH_FICHEIROS_DIRETORIO_INICIAL);
            File ficheiro = new File(PATH_FICHEIROS_DIRETORIO_INICIAL);
            importMensagens(ficheiro);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void importMensagens(File ficheiro) throws Exception {
        Scanner scan;
        scan = new Scanner(ficheiro);
        String nomeAux = "";
        while (scan.hasNextLine()) {
            String mensagem = scan.nextLine();
            Mensagem mensag = (Mensagem) getTipoMensagem(mensagem);
            if (mensag != null) {
                System.out.println(mensag.toString());
                mensagemRepository.save(mensag);
            }
        }
        System.out.println("Acabei");
        Files.copy(Paths.get(PATH_FICHEIROS_DIRETORIO_INICIAL), Paths.get(PATH_FICHEIROS_DIRETORIO_FINAL + "//Processada-" + nameFile), StandardCopyOption.REPLACE_EXISTING);
    }


    public static Mensagem getTipoMensagem(String mensagem) {
        try {
            if(!mensagem.contains(";")){
                w.write(mensagem+" - Formatação incorreta");
                return null;
            }
            String[] mensagemAux = mensagem.split(";");
            if (mensagemAux[1].equalsIgnoreCase("C0")) {
                return new MensagemConsumo(mensagemAux[0], getTimestampFromString(mensagemAux[2],mensagem),
                        mensagemAux[3], Integer.parseInt(mensagemAux[4]), mensagemAux[5]);
            }
            if (mensagemAux[1].equalsIgnoreCase("C9")) {
                return new MensagemEntregaProducao(mensagemAux[0], getTimestampFromString(mensagemAux[2],mensagem),
                        mensagemAux[3], Integer.parseInt(mensagemAux[4]), mensagemAux[5], mensagemAux[6]);
            }
            if (mensagemAux[1].equalsIgnoreCase("P1")) {

                return new MensagemProducao(mensagemAux[0], getTimestampFromString(mensagemAux[2],mensagem),
                        mensagemAux[3], Integer.parseInt(mensagemAux[4]), mensagemAux[5]);
            }
            if (mensagemAux[1].equalsIgnoreCase("P2")) {
                return new MensagemEstorno(mensagemAux[0], getTimestampFromString(mensagemAux[2],mensagem),
                        mensagemAux[3], Integer.parseInt(mensagemAux[4]), mensagemAux[5]);
            }
            if (mensagemAux[1].equalsIgnoreCase("S0")) {
                return new MensagemInicioAtividade(mensagemAux[0], getTimestampFromString(mensagemAux[2],mensagem),
                        mensagemAux[3]);
            }
            if (mensagemAux[1].equalsIgnoreCase("S1")) {
                return new MensagemRetomaAtividade(mensagemAux[0], getTimestampFromString(mensagemAux[2],mensagem));
            }
            if (mensagemAux[1].equalsIgnoreCase("S8")) {
                return new MensagemParagem(mensagemAux[0], getTimestampFromString(mensagemAux[2],mensagem),
                        mensagemAux[3]);
            }
            if (mensagemAux[1].equalsIgnoreCase("S9")) {
                return new MensagemFimAtividade(mensagemAux[0], getTimestampFromString(mensagemAux[2],mensagem),
                        mensagemAux[3]);
            }
        } catch(Exception e){
            try {
                w.write(mensagem+" - Formatação incorreta");
                e.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;



    }
    public static Timestamp getTimestampFromString(String time, String mensagemParaErro){
        String[] timeStampAux = time.split("T");
        String[] dateAux = timeStampAux[0].split("/");
        Date hoje = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        String[] timeAux = timeStampAux[1].split(":");
        Date data = new Date(Integer.parseInt(dateAux[0])-1900,Integer.parseInt(dateAux[1])-1,Integer.parseInt(dateAux[2]),
                Integer.parseInt(timeAux[0]),Integer.parseInt(timeAux[1]),Integer.parseInt(timeAux[2]));
        if(hoje.before(data)){
            try {
                w.write(mensagemParaErro + "Data inválida");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Timestamp timestamp = new Timestamp(data.getTime());
        return timestamp;
    }
}
