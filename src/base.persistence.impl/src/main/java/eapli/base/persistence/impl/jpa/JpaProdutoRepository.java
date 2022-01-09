package eapli.base.persistence.impl.jpa;

import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.FichaProducao;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.repositories.ProdutoRepository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class JpaProdutoRepository extends BaseJpaRepositoryBase<Produto, String, String> implements ProdutoRepository {

    public JpaProdutoRepository() {
        super("codigoFabrico");
    }

    @Override
    public void adicionarFichaProducao(Produto produto, FichaProducao ficha) {
        Produto prod = entityManager().find(Produto.class, produto.getCodigoFabrico());
        entityManager().getTransaction().begin();
        prod.adicionarFichaProducao(ficha);
        entityManager().getTransaction().commit();
    }

    @Override
    public Iterable<Produto> findProdutosSemFicha() {
        entityManager().getTransaction().begin();
        final TypedQuery<Produto> query = entityManager().createQuery("SELECT p FROM Produto p WHERE p.fichaProducao IS NULL", Produto.class);
        Iterable<Produto> results = query.getResultList();
        entityManager().close();
        return results;
    }

    @Override
    public Produto findProdutoByCodFabrico(String codFabrico) {
        entityManager().getTransaction().begin();
        try {
            Query query = entityManager().createQuery("select prod from Produto prod where codigoFabrico = :m");
            query.setParameter("m", codFabrico);
            Produto prod = (Produto) query.getSingleResult();
            entityManager().close();
            return prod;
        } catch (Exception e) {
            entityManager().close();
            return null;
        }
    }


    @Override
    public boolean verifyProdutoExiste(String ordemAux) {
        entityManager().getTransaction().begin();
        try {
            Query query = entityManager().createQuery("select prod from Produto prod where codigoFabrico = :m");
            query.setParameter("m", ordemAux);
            entityManager().close();
            return true;
        } catch (Exception e) {
            entityManager().close();
            return false;
        }
    }
}
