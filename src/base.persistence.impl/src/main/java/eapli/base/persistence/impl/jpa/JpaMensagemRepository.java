/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.comunicacao.domain.Mensagem;
import eapli.base.comunicacao.repositories.MensagemRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.persistence.TypedQuery;

public class JpaMensagemRepository extends BaseJpaRepositoryBase<Mensagem, Integer, Integer>
        implements MensagemRepository {

    public JpaMensagemRepository() {
        super("idMensagem");
    }

    @Override
    public ArrayList<Mensagem> findByProcessadasPorMaquinasETempo(Timestamp inicio, Timestamp fim, LinkedList<String> listaMaquinas) {
        try {
            TypedQuery<Mensagem> query = entityManager().createQuery("select msg from Mensagem msg where msg.idMaquina in :m and msg.dataHora between :g and :h and msg.processada = FALSE order by msg.dataHora asc", Mensagem.class);
            query.setParameter("m", listaMaquinas);
            query.setParameter("g", inicio);
            query.setParameter("h", fim);
            ArrayList<Mensagem> lista = (ArrayList<Mensagem>) query.getResultList();
            return lista;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Mensagem findPrimeiraMensagemAntesDeTimestamp(Timestamp tempo) {
        try {
            TypedQuery<Mensagem> query = entityManager().createQuery("select msg from Mensagem msg where msg.dataHora <:t and msg.processada = FALSE order by msg.dataHora desc", Mensagem.class).setMaxResults(1);
            query.setParameter("t", tempo);
            Mensagem mensagem = query.getSingleResult();
            return mensagem;
        } catch (Exception e) {
            return null;
        }
    }

}
