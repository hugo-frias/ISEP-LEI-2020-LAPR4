/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Objects;
import javax.persistence.Entity;

@Entity
public class Lote implements AggregateRoot<String> {

    private static final long serialVersionUID = 1L;

    @Version
    private long version;

    @Id
    @Column(unique = true, nullable = false)
    private String idLote;
    @Column(nullable = false)
    private String codigoFabrico;

    @Column
    private int quantidade;

    public Lote(String codigoFabrico, String idLote) {
        Preconditions.nonNull(codigoFabrico, "Código de fabrico não pode ser nulo.");
        Preconditions.nonNull(idLote, "Identificador do Lote não pode ser nulo.");

        Preconditions.nonEmpty(codigoFabrico, "Código de fabrico não pode estar vazio.");
        Preconditions.nonEmpty(idLote, "Identificador do lote não pode estar vazio.");

        this.codigoFabrico = codigoFabrico;
        this.idLote = idLote;
        this.quantidade = 0;
    }

    public Lote() {
        //for ORM
        codigoFabrico = idLote = "";
    }

    public String getCodigoFabrico() {
        return codigoFabrico;
    }

    public String getIdLote() {
        return idLote;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.codigoFabrico);
        hash = 43 * hash + Objects.hashCode(this.idLote);
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
        final Lote other = (Lote) obj;
        if (!Objects.equals(this.codigoFabrico, other.codigoFabrico)) {
            return false;
        }
        return Objects.equals(this.idLote, other.idLote);
    }

    @Override
    public String toString() {
        return String.format("ID do Lote: %s ;  ID do item armazenado: %s", idLote, codigoFabrico);
    }

    @Override
    public boolean sameAs(Object other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String identity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void AdicionarQuantidade(int quantidade) {
        this.quantidade = this.quantidade + quantidade;
    }

}
