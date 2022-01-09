package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.application.ImportarProdutosCSVController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ImportarProdutosCSVUI extends AbstractUI {

    private final ImportarProdutosCSVController ipc = new ImportarProdutosCSVController();
    public ImportarProdutosCSVUI(){
    }

    @Override
    protected boolean doShow() {
        final String path = Console.readLine("Introduza o path do ficheiro CSV");


        try {
            this.ipc.importar(path);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Erro ao importar");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String headline() {
        return null;
    }






}
