/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.comunicacao.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Mensagem implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (unique = true)
    private Integer IdMensagem;
    @Column
    private String idMaquina;
    @Column
    private Timestamp dataHora;
    @Column
    private String idLinhaProducao;
    @Column
    private String idOrdemProducao;
    @Column
    private boolean processada;

    public Mensagem() {
    }

    public Mensagem(String idMaquina, Timestamp dataHora) {
        Preconditions.nonEmpty(idMaquina);
        Preconditions.nonNull(dataHora);
        this.idMaquina = idMaquina;
        this.dataHora = dataHora;
        this.processada = false;
    }

    public Integer getIdMensagem() {
        return IdMensagem;
    }

    public String getIdMaquina() {
        return idMaquina;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public String getIdLinhaProducao() {
        return idLinhaProducao;
    }

    public String getIdOrdemProducao() {
        return idOrdemProducao;
    }

    public void setIdOrdemProducao(String idOrdemProducao) {
        this.idOrdemProducao = idOrdemProducao;
    }
    
    public void setIdLinhaProducao(String idLinhaProducao) {
        this.idLinhaProducao = idLinhaProducao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.IdMensagem;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mensagem other = (Mensagem) obj;
        if (this.IdMensagem != other.IdMensagem) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mensagem{" + "IdMensagem=" + IdMensagem + ", idMaquina=" + idMaquina + ", dataHora=" + dataHora;
    }

    @Override
    public boolean sameAs(Object other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer identity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void processar() {
        processada = true;
    }

}
