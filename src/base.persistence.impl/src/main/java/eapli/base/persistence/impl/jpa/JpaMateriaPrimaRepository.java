package eapli.base.persistence.impl.jpa;

import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.repositories.MateriaPrimaRepository;

public class JpaMateriaPrimaRepository extends BaseJpaRepositoryBase<MateriaPrima, String, String> implements MateriaPrimaRepository {

    public JpaMateriaPrimaRepository() {
        super("codigoInterno");
    }
}
