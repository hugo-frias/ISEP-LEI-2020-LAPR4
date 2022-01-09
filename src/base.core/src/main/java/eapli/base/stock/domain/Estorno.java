package eapli.base.stock.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

@Entity
public class Estorno implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idEstorno;
    @Column(nullable = false)
    private String idMateriaPrima;
    @Column(nullable = false)
    private int quantidade;


    public Estorno(String idMateriaPrima, int quantidade) {
        Preconditions.nonNull(idMateriaPrima);
        Preconditions.ensure(quantidade > 0);

        this.idMateriaPrima = idMateriaPrima;
        this.quantidade = quantidade;
    }

    protected Estorno() {
        // for ORM
    }

    public Integer getIdEstorno() {
        return idEstorno;
    }

    public String getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estorno that = (Estorno) o;
        return this.idEstorno.equals(that.getIdEstorno());
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public Integer identity() {
        return idEstorno;
    }
}
