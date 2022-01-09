package eapli.base.scm;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImportarMensagensMaquinasController {

    private final String PATH_FICHEIROS_DIRETORIO_INICIAL =  ".//base.scm//inicial";



    public ImportarMensagensMaquinasController() {

    }

    public void importarMensagensDeMaquinas() {
        List<String> filePaths = new ArrayList<>();
        try {
            filePaths = getFilePaths();
            if (filePaths.size() != 0) {
                List<ThreadMensagens> threadList = createThreads(filePaths);
                for (Thread t : threadList) {
                    t.start();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Não foi encontrada a pasta");
        } catch (IOException e) {
            System.out.println("Não foi encontrada a pasta");
        }

    }


    private List<String> getFilePaths() throws FileNotFoundException {
        Scanner scan;
        List<String> filePaths = new ArrayList<>();
        File pasta = new File(PATH_FICHEIROS_DIRETORIO_INICIAL);
        for (File fileIt : pasta.listFiles()) {
            filePaths.add(fileIt.getName());

        }
        return filePaths;
    }

    private List<ThreadMensagens> createThreads(List<String> filePaths) {
        List<ThreadMensagens> threadList = new ArrayList<>();
        for (int i = 0; i < filePaths.size(); i++) {
            ThreadMensagens thread = new ThreadMensagens(filePaths.get(i));
            threadList.add(thread);
        }
        return threadList;
    }


}
