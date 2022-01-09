/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.domain;

import eapli.framework.validations.Preconditions;
import javax.persistence.*;
import java.util.Objects;

/**
 *
 * @author Hugo
 */
@Embeddable
public abstract class Item {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    private UnidadeMedida unidadeMedida;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Categoria categoria;

    public Item(UnidadeMedida unidadeMedida, Categoria categoria) {
        Preconditions.nonNull(unidadeMedida, "Unidade de Medida não pode ser nula.");
        Preconditions.nonNull(categoria, "Categoria não pode ser nula.");

        this.unidadeMedida = unidadeMedida;
        this.categoria = categoria;
    }

    public Item() {
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }


    public Categoria getCategoria() {
        return categoria;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.unidadeMedida);
        hash = 97 * hash + Objects.hashCode(this.categoria);
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
        final Item other = (Item) obj;
        if (!Objects.equals(this.unidadeMedida, other.unidadeMedida)) {
            return false;
        }
        return Objects.equals(this.categoria, other.categoria);
    }
    
    
    
}
