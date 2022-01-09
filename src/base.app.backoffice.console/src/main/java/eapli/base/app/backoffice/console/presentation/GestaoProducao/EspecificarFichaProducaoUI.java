/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.application.EspecificarFichaProducaoController;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.Produto;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import java.util.HashMap;
import java.util.Map;

public class EspecificarFichaProducaoUI extends AbstractUI {

    private final EspecificarFichaProducaoController theController = new EspecificarFichaProducaoController();

    @Override
    protected boolean doShow() {
        Iterable<Produto> produtosSemFicha = this.theController.mostrarListaProdutosSemFicha();
        Iterable<MateriaPrima> materiasPrimas = this.theController.mostrarListaMateriasPrimas();
        String answer = "N";
        Map<Produto, Integer> listaProdutos = new HashMap<>();
        Map<MateriaPrima, Integer> listaMateriasPrimas = new HashMap<>();

        final SelectWidget<Produto> selector = new SelectWidget<>("Produtos sem ficha de produção:", produtosSemFicha, new ProdutosPrinter());
        selector.show();
        final Produto produto = selector.selectedElement();
        
        if(produto == null) {
            return false;
        }

        final String idFichaProducao = Console.readLine("Id da ficha de produção");

        final ListaItensDataWidget listaProduto = new ListaItensDataWidget();
        listaProdutos = listaProduto.chooseProdutos(produtosSemFicha, produto);

        if (listaProdutos.isEmpty()) {
            System.out.println("Não foi possível concluir a ação!");
            return false;
        }

        final ListaItensDataWidget listaMateriaPrima = new ListaItensDataWidget();
        listaMateriasPrimas = listaMateriaPrima.chooseMateriasPrimas(materiasPrimas);

        if (listaMateriasPrimas.isEmpty()) {
            System.out.println("Não foi possível concluir a ação!");
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
            this.theController.registarFichaProducao(idFichaProducao,produto,listaProdutos,listaMateriasPrimas);
            System.out.println("\nDados guardados com sucesso!");
        } catch (@SuppressWarnings("unused") final IllegalArgumentException e) {
            System.out.println("\nErro ao inserir dados.");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Especificar ficha de produção de um produto";
    }

}
