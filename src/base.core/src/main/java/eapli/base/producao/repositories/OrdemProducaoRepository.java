package eapli.base.producao.repositories;

import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.domain.OrdemProducaoStatus;
import eapli.framework.domain.repositories.DomainRepository;

public interface OrdemProducaoRepository extends DomainRepository<String, OrdemProducao> {
    
    Iterable<OrdemProducao> findOrdensProducaoByEstado(OrdemProducaoStatus estado);

}
