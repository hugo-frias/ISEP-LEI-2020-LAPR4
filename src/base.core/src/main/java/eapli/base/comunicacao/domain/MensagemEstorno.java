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
public class MensagemEstorno extends Mensagem {
    @Column
    private String idMateriaPrima;
    @Column
    private int quantidade;
    @Column
    private String idDeposito;

    public MensagemEstorno() {
    }

    public MensagemEstorno(String idMaquina, Timestamp dataHora, String idProduto, int quantidade, String idDeposito) {
        super(idMaquina, dataHora);
        Preconditions.nonEmpty(idProduto);
        Preconditions.nonEmpty(idDeposito);
        Preconditions.nonNull(quantidade);
        this.idMateriaPrima = idProduto;
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
