package eapli.base.producao.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.Produto;

public class ListProdutoService {

    private final eapli.base.stock.repositories.ProdutoRepository ProdutoRepository = PersistenceContext.repositories().produtos();

    public Iterable<Produto> todosProdutos() {
        return this.ProdutoRepository.findAll();
    }

}
