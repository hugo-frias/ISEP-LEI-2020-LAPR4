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
public class MensagemProducao extends Mensagem {
    @Column
    private String idProduto;
    @Column
    private int quantidade;
    @Column
    private String idLote;

    public MensagemProducao() {
    }

    public MensagemProducao(String idMaquina, Timestamp dataHora, String idProduto, int quantidade, String idLote) {
        super(idMaquina, dataHora);
        Preconditions.nonEmpty(idProduto);
        Preconditions.nonNull(quantidade);
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.idLote = idLote;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getIdLote() {
        return idLote;
    }

    @Override
    public String toString() {
        return super.toString() + ", idProduto=" + idProduto + ", quantidade=" + quantidade + ", idLote=" + idLote + '}';
    }
    
    

}
