package eapli.base.persistence.impl.jpa;

import eapli.base.stock.domain.Lote;
import eapli.base.stock.repositories.LoteRepository;

public class JpaLoteRepository extends BaseJpaRepositoryBase<Lote, String, String> implements LoteRepository {
    
    public JpaLoteRepository() {
        super("idLote");
    }
}