package eapli.base.stock.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class UnidadeMedida implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final String tipoUnidade;

    private final double quantidade;

    public UnidadeMedida(String tipoUnidade) {
        Preconditions.nonNull(tipoUnidade, "Tipo de unidade não pode ser nulo.");
        Preconditions.nonEmpty(tipoUnidade, "Tipo de unidade não pode estar vazio");

        this.tipoUnidade = tipoUnidade;
        this.quantidade = 0;
    }

    public UnidadeMedida(double quantidade, String tipoUnidade) {
        Preconditions.nonNull(tipoUnidade, "Tipo de unidade não pode ser nulo.");
        Preconditions.nonEmpty(tipoUnidade, "Tipo de unidade não pode estar vazio");

        this.tipoUnidade = tipoUnidade;
        this.quantidade = quantidade;
    }

    protected UnidadeMedida() {
        //for ORM
        tipoUnidade = "";
        quantidade = 0;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public double getQuantidade() {
        return quantidade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.tipoUnidade);
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
        final UnidadeMedida other = (UnidadeMedida) obj;
        return this.tipoUnidade.equals(((UnidadeMedida) obj).getTipoUnidade());
    }

    @Override
    public String toString() {
        return String.format("Tipo de Unidade: %s.", tipoUnidade);
    }

}
