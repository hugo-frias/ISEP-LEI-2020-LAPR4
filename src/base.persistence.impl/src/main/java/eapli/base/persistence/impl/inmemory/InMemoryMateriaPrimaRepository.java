/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.repositories.MateriaPrimaRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryMateriaPrimaRepository  extends InMemoryDomainRepository<String, MateriaPrima> implements MateriaPrimaRepository {

    static {
        InMemoryInitializer.init();
    }
}
