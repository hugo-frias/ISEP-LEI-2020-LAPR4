package eapli.base.persistence.impl.inmemory;

import eapli.base.producao.domain.Maquina;
import eapli.base.producao.repositories.MaquinaRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryMaquinaRepository extends InMemoryDomainRepository<String, Maquina> implements MaquinaRepository {
    
        static{
        InMemoryInitializer.init();
    }
}
