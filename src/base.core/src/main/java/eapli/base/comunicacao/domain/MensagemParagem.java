/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.comunicacao.domain;

import eapli.framework.validations.Preconditions;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MensagemParagem extends Mensagem {

    @Column
    private String codParagem;

    public MensagemParagem() {
    }

    public MensagemParagem(String idMaquina, Timestamp dataHora, String codParagem) {
        super(idMaquina, dataHora);
        Preconditions.nonEmpty(codParagem);
        this.codParagem = codParagem;
    }

    public String getCodParagem() {
        return codParagem;
    }

    @Override
    public String toString() {
        return super.toString() + ", codParagem=" + codParagem + '}';
    }

}
