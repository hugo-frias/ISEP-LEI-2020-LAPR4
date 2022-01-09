package eapli.base.persistence.impl.jpa;

import eapli.base.stock.domain.Estorno;
import eapli.base.stock.repositories.EstornoRepository;

public class JpaEstornoRepository extends BaseJpaRepositoryBase<Estorno, Integer, Integer> implements EstornoRepository {

    public JpaEstornoRepository() {
        super("idEstorno");
    }
    
}