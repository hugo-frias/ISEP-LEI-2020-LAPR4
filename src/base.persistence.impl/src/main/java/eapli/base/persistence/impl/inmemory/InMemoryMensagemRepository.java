/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.comunicacao.domain.Mensagem;
import eapli.base.comunicacao.repositories.MensagemRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

public class InMemoryMensagemRepository extends InMemoryDomainRepository<Integer, Mensagem> implements MensagemRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public ArrayList<Mensagem> findByProcessadasPorMaquinasETempo(Timestamp inicio, Timestamp fim, LinkedList<String> listaMaquinas) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mensagem findPrimeiraMensagemAntesDeTimestamp(Timestamp tempo) {
        throw new UnsupportedOperationException();
    }

}
