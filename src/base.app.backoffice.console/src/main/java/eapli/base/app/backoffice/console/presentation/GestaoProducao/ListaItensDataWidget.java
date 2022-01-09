/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.Produto;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import java.util.HashMap;
import java.util.Map;

public class ListaItensDataWidget {

    public Map<Produto, Integer> chooseProdutos(Iterable<Produto> listaProdutos, Produto prod) {
        int aux = 0;
        String answer = "N";
        Map<Produto, Integer> mapa = new HashMap<>();
        Produto produto;
        while (listaProdutos.iterator().hasNext() && answer.equals("N")) {
            SelectWidget<Produto> selector = new SelectWidget<>("Produtos:", listaProdutos, new ProdutosPrinter());
            selector.show();
            produto = selector.selectedElement();
            if (prod.equals(produto) || produto == null) {
                mapa.clear();
                return mapa;
            }
            Integer quantidade = Console.readInteger("Quantidade");
            mapa.put(produto, quantidade);
            answer = Console.readLine("Deseja sair?");
            if (answer.equals("S")) {
                break;
            }
        }
        for (Produto p : mapa.keySet()) {
            for (Produto p1 : mapa.keySet()) {
                if (p1.equals(p)) {
                    if (aux == 0) {
                        aux++;
                    } else {
                        mapa.clear();
                        return mapa;
                    }
                }
            }
        }
        return mapa;
    }

    public Map<MateriaPrima, Integer> chooseMateriasPrimas(Iterable<MateriaPrima> listaMateriasPrimas) {
        int aux = 0;
        String answer = "N";
        Map<MateriaPrima, Integer> mapa = new HashMap<>();
        MateriaPrima matP;
        while (listaMateriasPrimas.iterator().hasNext() && answer.equals("N")) {
            SelectWidget<MateriaPrima> selector = new SelectWidget<>("Matérias Primas:", listaMateriasPrimas, new MateriasPrimasPrinter());
            selector.show();
            matP = selector.selectedElement();
            if (matP == null) {
                mapa.clear();
                return mapa;
            }
            Integer quantidade = Console.readInteger("Quantidade");
            mapa.put(matP, quantidade);
            answer = Console.readLine("Deseja sair?");
            if (answer.equals("S")) {
                break;
            }
        }
        for (MateriaPrima p : mapa.keySet()) {
            for (MateriaPrima p1 : mapa.keySet()) {
                if (p1.equals(p)) {
                    if (aux == 0) {
                        aux++;
                    } else {
                        mapa.clear();
                        return mapa;
                    }
                }
            }
        }
        return mapa;
    }
}
