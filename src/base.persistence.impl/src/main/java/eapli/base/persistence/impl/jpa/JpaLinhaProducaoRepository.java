package eapli.base.persistence.impl.jpa;

import eapli.base.producao.domain.LinhaProducao;
import eapli.base.producao.repositories.LinhaProducaoRepository;

public class JpaLinhaProducaoRepository extends BaseJpaRepositoryBase<LinhaProducao, String, String> implements LinhaProducaoRepository {

    public JpaLinhaProducaoRepository() {
        super("idLinhaProducao");
    }
}
