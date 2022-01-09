/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.application.ConsultarProdutosSemFichaProducaoController;
import eapli.base.stock.domain.Produto;
import eapli.framework.presentation.console.AbstractUI;

public class ConsultarProdutosSemFichaProducaoUI extends AbstractUI {
    private ConsultarProdutosSemFichaProducaoController cpf = new ConsultarProdutosSemFichaProducaoController();
    
    @Override
    public boolean doShow(){
        try {
            Iterable<Produto> lp = cpf.consultarProdutosSemFicha();
            
            for(Produto p : lp){
                 System.out.println(p.toString() + "\n");
            }
        
        } catch (Exception e) {
            e.printStackTrace();
           
        }
        return true;      
    }
    
    @Override
    public String headline() {
        return "Consultar os produtos sem ficha de produção";
    }
}
