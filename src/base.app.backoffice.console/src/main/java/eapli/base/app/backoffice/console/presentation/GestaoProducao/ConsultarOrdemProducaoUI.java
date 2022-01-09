/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.producao.application.ConsultarOrdemProducaoEncomendaController;
import java.util.Scanner;
import eapli.base.producao.application.ConsultarOrdemProducaoService;
import eapli.base.producao.domain.Encomenda;
import eapli.base.producao.domain.OrdemProducao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joaoa
 */
public class ConsultarOrdemProducaoUI {

    ConsultarOrdemProducaoService cps = new ConsultarOrdemProducaoService();
    ConsultarOrdemProducaoEncomendaController cpc = new ConsultarOrdemProducaoEncomendaController();
    Scanner entrada = new Scanner(System.in);

    protected boolean doShow() {
        System.out.println("Escreva a encomenda que pretende consultar");

        String encomenda;
        boolean i = false;
        System.out.println("Lista das encomendas disponiveis");
        List<String> le = new ArrayList<>();
        le = cps.apresentarEncomendas();
        System.out.println("\nIntruduza o id da encomenda");
        encomenda = entrada.nextLine();
        
        for (String idEncomenda : le) {
            if (idEncomenda.equalsIgnoreCase(encomenda)) {
                i = true;
            }
        }

        if (i == false) {
            System.out.println("falha a completar a acção");
            return false;
        }

        cpc.run(encomenda);

        return true;
    }
}
