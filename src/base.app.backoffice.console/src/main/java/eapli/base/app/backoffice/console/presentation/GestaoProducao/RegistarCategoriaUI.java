package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.application.RegistarCategoriaController;
import eapli.base.stock.application.RegistarProdutoController;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class RegistarCategoriaUI extends AbstractUI {
    public RegistarCategoriaUI() {
    }

    private final RegistarCategoriaController rcc = new RegistarCategoriaController();

    @Override
    protected boolean doShow() {


        final String codigo = Console.readLine("Código Categoria");

        final String descricao = Console.readLine("Descrição Categoria");

        try {
            this.rcc.register(codigo, descricao);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Você tentou introduzir uma categoria que já se encontra no sistema.");
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
