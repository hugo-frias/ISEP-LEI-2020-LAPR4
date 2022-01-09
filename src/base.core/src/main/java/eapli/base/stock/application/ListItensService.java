/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.repositories.MateriaPrimaRepository;
import eapli.base.stock.repositories.ProdutoRepository;

public class ListItensService {

    private final ProdutoRepository produtoRepository = PersistenceContext.repositories().produtos();
    private final MateriaPrimaRepository materiaPrimaRepository = PersistenceContext.repositories().materiasPrimas();
    
    public Iterable<Produto> obterListaProdutosSemFicha() {
        return this.produtoRepository.findProdutosSemFicha();
    }

    public Iterable<MateriaPrima> obterListaMateriasPrimas() {
        return this.materiaPrimaRepository.findAll();
    }
}
