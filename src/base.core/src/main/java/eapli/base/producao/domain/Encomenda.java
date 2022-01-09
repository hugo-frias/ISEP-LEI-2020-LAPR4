package eapli.base.producao.domain;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement (name = "Encomenda")
@Entity
public class Encomenda implements AggregateRoot<String> {

    @Id
    @Column(unique = true, nullable = false)
    private String idEncomenda;

    @OneToMany
    @Column
    List<OrdemProducao> listaOrdens;

    public Encomenda(String idEncomenda) {
        this.idEncomenda = idEncomenda;
        listaOrdens = new ArrayList<>();
    }

    protected Encomenda() {
        // for ORM
    }
    @XmlElement
    public String getIdEncomenda() {
        return idEncomenda;
    }

    @XmlElement
    public List<OrdemProducao> getOrdemProducao(){
        return listaOrdens;
    }

    public void addOrdemProducao(OrdemProducao ord){
        listaOrdens.add(ord);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encomenda that = (Encomenda) o;
        return this.getIdEncomenda().equals(that.getIdEncomenda());
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public String identity() {
        return idEncomenda;
    }

}
