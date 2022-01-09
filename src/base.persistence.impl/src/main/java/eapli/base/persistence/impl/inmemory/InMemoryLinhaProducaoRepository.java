package eapli.base.persistence.impl.inmemory;

import eapli.base.producao.domain.LinhaProducao;
import eapli.base.producao.repositories.LinhaProducaoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryLinhaProducaoRepository extends InMemoryDomainRepository<String, LinhaProducao> implements LinhaProducaoRepository {
        static{
        InMemoryInitializer.init();
    }
    
}
