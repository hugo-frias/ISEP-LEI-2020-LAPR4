/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.producao.domain.OrdemProducaoStatus;
import eapli.framework.visitor.Visitor;

public class OrdemProducaoStatusPrinter implements Visitor<OrdemProducaoStatus> {

    @Override
    public void visit(OrdemProducaoStatus visitee) {
        System.out.printf("%-30s", visitee.toString());
    }
    
}
