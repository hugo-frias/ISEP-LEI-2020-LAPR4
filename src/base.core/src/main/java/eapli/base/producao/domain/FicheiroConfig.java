/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class FicheiroConfig implements ValueObject {
    private final String nomeFich;
    private final String descricaoFicheiro;

    protected FicheiroConfig() {
        //for ORM
        nomeFich="";
        descricaoFicheiro="";
    }

    public FicheiroConfig(String nomeFich, String descricao) {
        Preconditions.nonNull(nomeFich, "Tipo de unidade não pode ser nulo.");
        Preconditions.nonEmpty(nomeFich, "Tipo de unidade não pode estar vazio");
        Preconditions.nonNull(descricao, "Tipo de unidade não pode ser nulo.");
        Preconditions.nonEmpty(descricao, "Tipo de unidade não pode estar vazio");
        this.nomeFich = nomeFich;
        this.descricaoFicheiro = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.nomeFich);
        hash = 97 * hash + Objects.hashCode(this.descricaoFicheiro);
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
        final FicheiroConfig other = (FicheiroConfig) obj;
        if (!Objects.equals(this.nomeFich, other.nomeFich)) {
            return false;
        }
        if (!Objects.equals(this.descricaoFicheiro, other.descricaoFicheiro)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FicheiroConfig{" + "nomeFich=" + nomeFich + ", descricao=" + descricaoFicheiro + '}';
    }
    
    
    
}
