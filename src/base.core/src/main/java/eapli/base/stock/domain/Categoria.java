/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.domain;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

import static eapli.base.Utils.containsLetter;
import static eapli.base.Utils.containsNum;

/**
 *
 * @author Hugo
 */
@XmlRootElement(name = "Categoria")
@Entity
@Table
public class Categoria implements AggregateRoot<String> {
    @Id
    @Column (unique = true)
    private String codigo;
    @Column
    private String descricao;

    private static final String DESCRICAO_OMISSAO = "Sem descrição";

    public Categoria(String codigo, String descricao) throws Exception {
        if(validateCategoria(codigo, descricao)) {
            this.codigo = codigo;
            this.descricao = descricao;
        }
        else{
            throw new Exception("Erro a criar a categoria!");
        }
    }


    public Categoria (String codigo) {
        this.codigo = codigo;
        this.descricao = DESCRICAO_OMISSAO;
    }

    protected Categoria() {
        // for ORM
    }
    @XmlElement
    public String getCodigo() {
        return codigo;
    }

    @XmlElement
    public String getDescricao() {
        return descricao;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.codigo);
        hash = 83 * hash + Objects.hashCode(this.descricao);
        return hash;
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
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
        final Categoria other = (Categoria) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return Objects.equals(this.descricao, other.descricao);
    }

    @Override
    public String toString() {
        return "Categoria{" + "codigo=" + codigo + ", descricao=" + descricao + '}';
    }


    @Override
    public String identity() {
        return this.codigo;
    }
    private boolean validateCategoria(String codigo, String descricao ){

        return codigo.length() <= 10;
    }
}
