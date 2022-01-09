/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.domain.Produto;
import eapli.framework.visitor.Visitor;

public class ProdutosPrinter implements Visitor<Produto> {

    @Override
    public void visit(Produto visitee) {
        System.out.printf("%-30s", visitee.toString());
    }
    
}
