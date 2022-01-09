package eapli.base.persistence.impl.jpa;

import eapli.base.stock.domain.MovimentoStock;
import eapli.base.stock.repositories.MovimentoStockRepository;

public class JpaMovimentoStockRepository extends BaseJpaRepositoryBase<MovimentoStock, String, String>
        implements MovimentoStockRepository {
    
    public JpaMovimentoStockRepository() {
        super("codigoDepositoOrigem");
    }
}