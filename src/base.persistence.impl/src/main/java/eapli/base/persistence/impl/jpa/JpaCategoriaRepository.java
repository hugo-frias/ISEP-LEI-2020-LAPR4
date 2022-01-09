package eapli.base.persistence.impl.jpa;

import eapli.base.stock.domain.Categoria;
import eapli.base.stock.repositories.CategoriaRepository;
import javax.persistence.*;

public class JpaCategoriaRepository extends BaseJpaRepositoryBase<Categoria, String, String> implements CategoriaRepository {

    public JpaCategoriaRepository() {
        super("codigo");
    }

    @Override
    public Categoria getCategoriaById(String codigo) {
        entityManager().getTransaction().begin();
        try {
            Query query = entityManager().createQuery("select cat from Categoria cat where codigo = :m");
            query.setParameter("m", codigo);
            Categoria cat = (Categoria) query.getSingleResult();
            entityManager().close();
            return cat;
        } catch (Exception e) {
            entityManager().close();
            return null;
        }

    }

}
