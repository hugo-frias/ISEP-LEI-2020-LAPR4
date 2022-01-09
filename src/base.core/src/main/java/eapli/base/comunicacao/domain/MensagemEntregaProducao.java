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
public class MensagemEntregaProducao extends Mensagem {
    @Column
    private String idProduto;
    @Column
    private int quantidade;
    @Column
    private String idDeposito;
    @Column
    private String idLote;

    public MensagemEntregaProducao() {
    }

    public MensagemEntregaProducao(String idMaquina, Timestamp dataHora, String idProduto, int quantidade, String idDeposito, String idLote) {
        super(idMaquina, dataHora);
        Preconditions.nonEmpty(idProduto);
        Preconditions.nonEmpty(idDeposito);
        Preconditions.nonNull(quantidade);
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.idDeposito = idDeposito;
        this.idLote = idLote;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getIdDeposito() {
        return idDeposito;
    }

    public String getIdLote() {
        return idLote;
    }

    @Override
    public String toString() {
        return super.toString() + ", idProduto=" + idProduto + ", quantidade=" + quantidade + ", idDeposito=" + idDeposito + ", idLote=" + idLote + '}';
    }

}
