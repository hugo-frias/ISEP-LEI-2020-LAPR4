/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.comunicacao.domain.NotificacaoErroProcessamento;
import eapli.base.comunicacao.repositories.NotificacaoErroRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryNotificacaoErroRepository extends InMemoryDomainRepository<Integer, NotificacaoErroProcessamento> implements NotificacaoErroRepository {

    static {
        InMemoryInitializer.init();
    }
}
