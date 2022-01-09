package eapli.base.persistence.impl.inmemory;

import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.domain.OrdemProducaoStatus;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryOrdemProducaoRepository extends InMemoryDomainRepository<String, OrdemProducao> implements OrdemProducaoRepository {

    static{
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<OrdemProducao> findOrdensProducaoByEstado(OrdemProducaoStatus estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
