package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.application.RegistarProdutoController;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.Produto;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class RegistarProdutoUI extends AbstractUI {

    private final RegistarProdutoController theController = new RegistarProdutoController();

    @Override
    protected boolean doShow() {
        final Iterable<Categoria> todasCategorias = this.theController.categorias();

        String answer = "N";

        final SelectWidget<Categoria> selector = new SelectWidget<>("Categorias:", todasCategorias,
                new CategoriasPrinter());
        selector.show();

        final Categoria aquelaCategoria = selector.selectedElement();

        if(aquelaCategoria == null) {
            return false;
        }

        final String tipoUnidade = Console.readLine("Tipo de Unidade (Deixe vazio para retornar)");

        if (tipoUnidade.isEmpty()) {
            return false;
        }

        final String codigoFabrico = Console.readLine("Código Fabrico (Deixe vazio para retornar)");

        if (codigoFabrico.isEmpty()) {
            return false;
        }

        final String codigoComercial = Console.readLine("Código Comercial (Deixe vazio para retornar)");

        if (codigoComercial.isEmpty()) {
            return false;
        }

        final String descBreve = Console.readLine("Descrição Breve (Deixe vazio para retornar)");

        if (descBreve.isEmpty()) {
            return false;
        }

        final String descCompleta = Console.readLine("Descrição Completa (Deixe vazio para retornar)");

        if(descCompleta.isEmpty()) {
            return false;
        }

        while (!answer.equalsIgnoreCase("S")) {
            answer = Console.readLine("Confirma os dados (Responda S/N)?");
            if (answer.equals("N")) {
                System.out.println("\nOs dados não foram guardados!");
                return false;
            }
        }

        try {
            Produto aux = this.theController.registarProduto(codigoFabrico, codigoComercial, descBreve, descCompleta, aquelaCategoria, tipoUnidade);
            System.out.printf("Produto introduzido: %s. \n", aux.toString());
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Você tentou introduzir um produto que já se encontra no sistema.");
        }

        return false;

    }

    @Override
    public String headline() {
        return "Adicionar Produto.";
    }
}