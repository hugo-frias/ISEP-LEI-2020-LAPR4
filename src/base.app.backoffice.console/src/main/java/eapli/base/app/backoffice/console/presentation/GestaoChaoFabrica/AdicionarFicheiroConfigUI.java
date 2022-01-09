/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica;

import eapli.base.producao.application.AdicionarFicheiroConfigController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class AdicionarFicheiroConfigUI extends AbstractUI {

    private final AdicionarFicheiroConfigController theController = new AdicionarFicheiroConfigController();

    @Override
    protected boolean doShow() {
        final String nomeFich = Console.readLine("Nome do Ficheiro");
        final String descricao = Console.readLine("Descrição");
        final String idMaq = Console.readLine("ID da Máquina");
        try {
                theController.adicionarFicheiroConfig(nomeFich,descricao,idMaq);
            } catch (@SuppressWarnings("unused") final IllegalArgumentException e) {
                System.out.println("\nVoçê introduziu dados errados.");
            }
        
        return true;
    }
        

    @Override
    public String headline() {
        return "Adicionar Ficheiro de Configuração a uma maquina";
    }

}
