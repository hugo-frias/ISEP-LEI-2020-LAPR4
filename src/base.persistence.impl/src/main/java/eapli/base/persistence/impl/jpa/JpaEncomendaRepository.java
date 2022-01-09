/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.producao.domain.Encomenda;
import eapli.base.producao.repositories.EncomendaRepository;
import eapli.base.stock.domain.Categoria;
import java.util.Optional;
import javax.persistence.Query;

/**
 *
 * @author joaoa
 */
public class JpaEncomendaRepository extends BaseJpaRepositoryBase<Encomenda, String, String> implements EncomendaRepository {

    public JpaEncomendaRepository() {
          super("idEncomenda");
    }
   
    @Override
    public Encomenda getEncomendaById(String codigo) {
        entityManager().getTransaction().begin();
        try {
            Query query = entityManager().createQuery("select enc from Encomenda enc where enc.idEncomenda = :e");
            query.setParameter("e", codigo);
            Encomenda cat = (Encomenda) query.getSingleResult();
            entityManager().close();
            return cat;
        } catch (Exception e) {
            entityManager().close();
            return null;
        }

    }

    
}
