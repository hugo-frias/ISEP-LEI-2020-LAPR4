package eapli.base.persistence.impl.jpa;

import eapli.base.producao.domain.Maquina;
import eapli.base.producao.repositories.MaquinaRepository;

public class JpaMaquinaRepository extends BaseJpaRepositoryBase<Maquina, String, String> implements MaquinaRepository {

    public JpaMaquinaRepository() {
        super("codigoInterno");
    }
}
