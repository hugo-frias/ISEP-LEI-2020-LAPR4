/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.producao.application.AdicionarMaquinaController;
import eapli.base.producao.application.ListLinhasProducaoService;
import eapli.base.producao.domain.LinhaProducao;
import eapli.framework.actions.Action;
import java.time.LocalDate;

public class MaquinaBootstrapper implements Action {
    
private final AdicionarMaquinaController amc = new AdicionarMaquinaController();
private final ListLinhasProducaoService lpc = new ListLinhasProducaoService();
private final ListLinhasProducaoService lpc1 = new ListLinhasProducaoService();
private final ListLinhasProducaoService lpc2 = new ListLinhasProducaoService();
    
    @Override
    public boolean execute() {
         try{
            Iterable<LinhaProducao> ilp1 = lpc.obterListaLinhasProducao();
            LinhaProducao p10 = ilp1.iterator().next();
            amc.registarMaquina(p10,"T3", "20201234", "Maquina GHI", LocalDate.MIN, "Woven", "Woven-1",2);
 
            
            Iterable<LinhaProducao> ilp2 = lpc1.obterListaLinhasProducao();
            LinhaProducao p11 = ilp2.iterator().next();
            amc.registarMaquina(p11,"T6", "20202345", "Maquina DEF", LocalDate.MIN, "Cherios", "golden",1);
            
            Iterable<LinhaProducao> ilp3 = lpc2.obterListaLinhasProducao();
            LinhaProducao p12 = ilp3.iterator().next();
            amc.registarMaquina(p12,"M003", "20203456", "Maquina ABC", LocalDate.MIN, "Woven", "Woven-Meister",3);
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
        
}
  