/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.stock.application.RegistarDepositoController;
import eapli.framework.actions.Action;


public class DepositoBootstrapper implements Action {
    private final RegistarDepositoController rd = new RegistarDepositoController();

    @Override
    public boolean execute() {
        try {
        rd.registarDeposito("D001", "Deposito de produtos finalizados");
        rd.registarDeposito("D002", "Deposito de materias primas");
        rd.registarDeposito("D003", "Deposito de produtos semi finalizados");
        return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
}
