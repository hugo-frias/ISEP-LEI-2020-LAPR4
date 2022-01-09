/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.FichaProducao;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.repositories.ProdutoRepository;
import java.util.Map;

public class EspecificarFichaProducaoController {

    private final ProdutoRepository produtoRepository = PersistenceContext.repositories().produtos();
    private final ListItensService svc = new ListItensService();

    public FichaProducao registarFichaProducao(final String idFichaProducao, Produto produto, Map<Produto, Integer> listaProdutos, Map<MateriaPrima, Integer> listaMateriasPrimas) {

        final FichaProducao ficha = new FichaProducao(idFichaProducao, listaProdutos, listaMateriasPrimas);
        
        produto.adicionarFichaProducao(ficha);
        
        produtoRepository.save(produto);

        return ficha;
    }

    public Iterable<Produto> mostrarListaProdutosSemFicha() {
        return this.svc.obterListaProdutosSemFicha();
    }

    public Iterable<MateriaPrima> mostrarListaMateriasPrimas() {
        return this.svc.obterListaMateriasPrimas();
    }
}
