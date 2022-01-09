package eapli.base.persistence.impl.inmemory;

import eapli.base.stock.domain.Categoria;
import eapli.base.stock.repositories.CategoriaRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryCategoriaRepository extends InMemoryDomainRepository<String, Categoria> implements CategoriaRepository{

    static{
        InMemoryInitializer.init();
    }

    @Override
    public Categoria getCategoriaById(String codigo) {
        return null;
    }
}
