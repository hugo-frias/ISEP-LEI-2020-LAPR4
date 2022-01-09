/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MateriaPrima")
@Entity
public class MateriaPrima extends Item implements AggregateRoot<String>{

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @Column (unique = true)
    private String codigoInterno;
    @Column (nullable = false)
    private String descricao;
    @Column (nullable = false)
    private String nomeFichaTecnica;

    public MateriaPrima(String codigoInterno, String descricao, String nomeFichaTecnica, UnidadeMedida unidadeMedida, Categoria categoria) {
        super(unidadeMedida, categoria);

        Preconditions.nonNull(codigoInterno, "Código interno não pode ser nulo.");
        Preconditions.nonNull(descricao, "Descrição não pode ser nula.");
        Preconditions.nonNull(nomeFichaTecnica, "Nome da ficha técnica não pode ser nulo.");

        Preconditions.nonEmpty(codigoInterno, "Código interno não pode estar vazio.");
        Preconditions.nonEmpty(descricao, "Descrição da matéria prima não pode estar vazia.");
        Preconditions.nonEmpty(nomeFichaTecnica, "Nome da ficha técnica da máquina não pode estar vazia.");

        this.codigoInterno = codigoInterno;
        this.descricao = descricao;
        this.nomeFichaTecnica = nomeFichaTecnica;
    }

    protected MateriaPrima() {
        //for ORM
    }
    @XmlElement
    public String getCodigoInterno() {
        return codigoInterno;
    }

    @XmlElement
    public String getDescricao() {
        return descricao;
    }

    @XmlElement
    public String getNomeFichaTecnica() {
        return nomeFichaTecnica;
    }

    @XmlElement
    public UnidadeMedida getUnidadeMedida() { return super.getUnidadeMedida(); }
    @XmlElement
    public Categoria getCategoria() { return super.getCategoria(); }

    @Override
    public String toString() {
        return String.format("Código Interno: %s ;   Descrição: %s ;   Nome da ficha técnica: %s.", codigoInterno, descricao, nomeFichaTecnica);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.codigoInterno);
        hash = 71 * hash + Objects.hashCode(this.descricao);
        hash = 71 * hash + Objects.hashCode(this.nomeFichaTecnica);
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
        final MateriaPrima other = (MateriaPrima) obj;
        if (!Objects.equals(this.codigoInterno, other.codigoInterno)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        return Objects.equals(this.nomeFichaTecnica, other.nomeFichaTecnica);
    }
    
        @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public String identity() {
        return this.codigoInterno;
    }
    
    
}
