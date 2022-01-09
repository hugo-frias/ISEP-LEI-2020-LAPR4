/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica;

import eapli.base.stock.application.RegistarDepositoController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;


public class RegistarDepositoUI extends AbstractUI{
    private final RegistarDepositoController theController = new RegistarDepositoController();

    @Override
    protected boolean doShow() {
        final String codigoInterno = Console.readLine("Código do Depósito");
        
        final String descricao = Console.readLine("Descrição");
        
        try {
            this.theController.registarDeposito(codigoInterno, descricao);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Você tentou introduzir um deposito que já se encontra no sistema.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Adicionar Depósito.";
    }
    
}
    