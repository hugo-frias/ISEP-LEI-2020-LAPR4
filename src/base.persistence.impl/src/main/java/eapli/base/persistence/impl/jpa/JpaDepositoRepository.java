/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.stock.domain.Deposito;
import eapli.base.stock.repositories.DepositoRepository;

public class JpaDepositoRepository extends BaseJpaRepositoryBase<Deposito, String, String> implements DepositoRepository {

    public JpaDepositoRepository() {
        super("codigoDeposito");
    }
    
}
