/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.comunicacao.repositories;

import eapli.base.comunicacao.domain.Mensagem;
import eapli.framework.domain.repositories.DomainRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

public interface MensagemRepository extends DomainRepository<Integer, Mensagem> {
    
    ArrayList<Mensagem> findByProcessadasPorMaquinasETempo(Timestamp inicio, Timestamp fim, LinkedList<String> listaMaquinas);
    
    Mensagem findPrimeiraMensagemAntesDeTimestamp(Timestamp tempo);
    
}
