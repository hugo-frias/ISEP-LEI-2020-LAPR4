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
public class MensagemConsumo extends Mensagem {

    private String idMateriaPrima;

    private int quantidade;

    private String idDeposito;

    public MensagemConsumo() {
    }

    public MensagemConsumo(String idMaquina, Timestamp dataHora, String idMateriaPrima, int quantidade, String idDeposito) {
        super(idMaquina, dataHora);
        Preconditions.nonEmpty(idMateriaPrima);
        Preconditions.nonEmpty(idDeposito);
        Preconditions.nonNull(quantidade);
        this.idMateriaPrima = idMateriaPrima;
        this.quantidade = quantidade;
        this.idDeposito = idDeposito;
    }

    public String getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getIdDeposito() {
        return idDeposito;
    }

    @Override
    public String toString() {
        return super.toString() + ", idProduto=" + idMateriaPrima + ", quantidade=" + quantidade + ", idDeposito=" + idDeposito + '}';
    }

}

