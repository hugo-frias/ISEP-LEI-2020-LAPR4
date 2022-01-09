package eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import eapli.framework.actions.Action;


public class AdicionarDepositoAction implements Action {

    @Override
    public boolean execute() {
        return new RegistarDepositoUI().doShow();
    }
    
}
