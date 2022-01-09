/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.comunicacao.domain;

import eapli.framework.validations.Preconditions;

import java.sql.Timestamp;
import javax.persistence.Entity;

@Entity
public class MensagemFimAtividade extends Mensagem {

    public MensagemFimAtividade() {
    }

    public MensagemFimAtividade(String idMaquina, Timestamp dataHora, String idOrdemProducao) {
        super(idMaquina, dataHora);
        Preconditions.nonEmpty(idOrdemProducao);
        super.setIdOrdemProducao(idOrdemProducao);
    }

    @Override
    public String toString() {
        return super.toString() + ", idOrdemProducao=" + super.getIdOrdemProducao() + '}';
    }
    
    

}
