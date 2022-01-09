/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.producao.application.ConsultarOrdensProducaoEstadoController;
import eapli.base.producao.domain.OrdemProducaoStatus;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import java.util.ArrayList;

public class ConsultarOrdensProducaoEstadoUI extends AbstractUI {

    private final ConsultarOrdensProducaoEstadoController theController = new ConsultarOrdensProducaoEstadoController();

    @Override
    protected boolean doShow() {
        String answer = "N";
        ArrayList<OrdemProducaoStatus> estados = (ArrayList<OrdemProducaoStatus>) theController.mostrarEstadosOrdemProducao();

        final SelectWidget<OrdemProducaoStatus> selector = new SelectWidget<>("Estados de ordens de produção:", estados, new OrdemProducaoStatusPrinter());
        selector.show();
        final OrdemProducaoStatus estado = selector.selectedElement();

        while (!answer.equalsIgnoreCase("S")) {
            answer = Console.readLine("Confirma o estado escolhido (Responda S/N)?");
            if (answer.equals("N")) {
                System.out.println("\nOperação cancelada!");
                return false;
            }
        }

        theController.listarOrdensProducaoEstado(estado);

        return true;
    }

    @Override
    public String headline() {
        return "Consultar ordens de produção num determinado estado";
    }

}
