/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.repositories.ProdutoRepository;

/**
 *
 * @author maria
 */
public class ConsultarProdutosSemFichaProducaoController {
   
    private final ProdutoRepository produtoRepository = PersistenceContext.repositories().produtos();
    
    public Iterable<Produto> consultarProdutosSemFicha() {
        return this.produtoRepository.findProdutosSemFicha();
        
    }    
    
}
