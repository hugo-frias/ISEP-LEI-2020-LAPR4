/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.application;

   
   

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.FicheiroConfig;
import eapli.base.producao.domain.Maquina;
import eapli.base.producao.repositories.LinhaProducaoRepository;
import eapli.base.producao.repositories.MaquinaRepository;
import java.util.Optional;

public class AdicionarFicheiroConfigController {
    private final MaquinaRepository maquinaRepository = PersistenceContext.repositories().maquinas();
    
    public void adicionarFicheiroConfig(String nomeFich, String descricao, String idMaq) {
        
            Maquina maq = maquinaRepository.ofIdentity(idMaq).get();
            FicheiroConfig fc = new FicheiroConfig(nomeFich,descricao);
            maq.setFicheiroConfig(fc);
            maquinaRepository.save(maq);
        
        
        
    }
    
}
