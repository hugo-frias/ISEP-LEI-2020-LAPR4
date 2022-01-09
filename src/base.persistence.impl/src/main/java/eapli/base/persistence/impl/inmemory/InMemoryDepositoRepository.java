/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.stock.domain.Deposito;
import eapli.base.stock.repositories.DepositoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryDepositoRepository extends InMemoryDomainRepository<String, Deposito> implements DepositoRepository {

    static {
        InMemoryInitializer.init();
    }

}
