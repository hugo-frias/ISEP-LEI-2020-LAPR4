/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.producao.application.RegistarLinhaProducaoController;
import eapli.framework.actions.Action;
import java.util.ArrayList;
import java.util.List;


public class LinhaProducaoBootstrapper implements Action {
    
    
    private final RegistarLinhaProducaoController rlp = new RegistarLinhaProducaoController();
    
    
    @Override
    public boolean execute() {
        
        
        try{
            
            rlp.registarLinhaProducao("LP001");
            rlp.registarLinhaProducao("LP002");
            rlp.registarLinhaProducao("LP003");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
        
    }
    
    

