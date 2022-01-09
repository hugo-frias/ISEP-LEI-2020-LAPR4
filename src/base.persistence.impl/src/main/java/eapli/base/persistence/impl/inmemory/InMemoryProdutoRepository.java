package eapli.base.persistence.impl.inmemory;

import eapli.base.stock.domain.FichaProducao;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.repositories.ProdutoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryProdutoRepository extends InMemoryDomainRepository<String, Produto>  implements ProdutoRepository {

    static{
        InMemoryInitializer.init();
    }


    @Override
    public void adicionarFichaProducao(Produto produto, FichaProducao ficha) {

    }

    @Override
    public Iterable<Produto> findProdutosSemFicha() {
        return null;
    }

    @Override
    public Produto findProdutoByCodFabrico(String codFabrico) {
        return null;
    }

    @Override
    public boolean verifyProdutoExiste(String ordemAux) {
        return false;
    }
}
