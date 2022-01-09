/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MovimentoStock implements AggregateRoot<String> {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private String idMovimentoStock;

    @Column
    private String codigoDepositoOrigem;

    @Column
    private String idMateriaPrima;

    @Column
    private int entrada;

    @Column
    private int saida;
    
    @Column
    private Timestamp dataHora;

    public MovimentoStock() {
    }

    public MovimentoStock(String codigoDepositoOrigem, String idMateriaPrima, int entrada, int saida, Timestamp dataHora) {
        Preconditions.nonNull(codigoDepositoOrigem, "Código do depósito de origem não pode ser nulo.");

        Preconditions.nonEmpty(codigoDepositoOrigem, "Código do depósito de origem não pode estar vazio.");

        this.codigoDepositoOrigem = codigoDepositoOrigem;
        this.idMateriaPrima = idMateriaPrima;
        this.entrada = entrada;
        this.saida = saida;
        this.dataHora = dataHora;
    }

    public String getCodigoDepositoOrigem() {
        return codigoDepositoOrigem;
    }

    public String getIdMovimentoStock() {
        return idMovimentoStock;
    }

    public String getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public int getEntrada() {
        return entrada;
    }

    public int getSaida() {
        return saida;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.codigoDepositoOrigem);
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
        final MovimentoStock other = (MovimentoStock) obj;
        if (!Objects.equals(this.codigoDepositoOrigem, other.codigoDepositoOrigem)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MovimentoDeStock{" + "codigoDepositoOrigem=" + codigoDepositoOrigem + '}';
    }

    @Override
    public boolean sameAs(Object other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String identity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
