/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.comunicacao.domain;

import java.sql.Timestamp;
import javax.persistence.Entity;

@Entity
public class MensagemRetomaAtividade extends Mensagem {

    public MensagemRetomaAtividade() {
    }

    public MensagemRetomaAtividade(String idMaquina, Timestamp dataHora) {
        super(idMaquina, dataHora);
    }
    
}
