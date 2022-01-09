package eapli.base.persistence.impl.jpa;

import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.domain.OrdemProducaoStatus;
import eapli.base.producao.repositories.OrdemProducaoRepository;

import javax.persistence.TypedQuery;

public class JpaOrdemProducaoRepository extends BaseJpaRepositoryBase<OrdemProducao, String, String> implements OrdemProducaoRepository {

    public JpaOrdemProducaoRepository() {
        super("idOrdemProducao");
    }

    @Override
    public Iterable<OrdemProducao> findOrdensProducaoByEstado(OrdemProducaoStatus estado) {
        try {
            TypedQuery<OrdemProducao> query = entityManager().createQuery("select ordem from OrdemProducao ordem where ordem.execucaoOrdemProducao.status =: e", OrdemProducao.class);
            query.setParameter("e", estado);
            Iterable<OrdemProducao> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            return null;
        }
    }

}
