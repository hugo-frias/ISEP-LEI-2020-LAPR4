/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.repositories;

import eapli.base.stock.domain.FichaProducao;
import eapli.base.stock.domain.Produto;
import eapli.framework.domain.repositories.DomainRepository;

public interface ProdutoRepository extends DomainRepository<String, Produto> {

    public void adicionarFichaProducao(Produto produto, FichaProducao ficha);

    public Iterable<Produto> findProdutosSemFicha();

    public Produto findProdutoByCodFabrico(String codFabrico);

    boolean verifyProdutoExiste(String ordemAux);
}
