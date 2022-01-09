/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import javax.persistence.Version;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Deposito")
@Entity
public class Deposito implements AggregateRoot<String> {

    private static final long serialVersionUID = 1L;

    @Version
    private long version;

    @Id
    @Column(unique = true, nullable = false)
    private String codigoDeposito;
    @Column(nullable = false)
    private String descricaoDeposito;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Estorno> listaEstornos;

    @ElementCollection
    @MapKeyColumn(name = "Produto")
    @Column(name = "Quantidade")
    private Map<Produto, Integer> listaProdutos;

    public Deposito(String codigoDeposito, String descricaoDeposito) {
        Preconditions.nonNull(codigoDeposito, "Código de Depósito não pode ser nulo.");
        Preconditions.nonNull(descricaoDeposito, "Descrição de Depósito não pode ser nula");

        Preconditions.nonEmpty(codigoDeposito, "Código de Depósito não pode estar vazio.");
        Preconditions.nonEmpty(descricaoDeposito, "Descrição de depósito não pode estar vazia.");

        this.codigoDeposito = codigoDeposito;
        this.descricaoDeposito = descricaoDeposito;
        this.listaEstornos = new ArrayList<>();
        this.listaProdutos = new HashMap<>();
    }

    public Deposito() {

    }

    @XmlElement
    public String getCodigoDeposito() {
        return codigoDeposito;
    }

    @XmlElement
    public String getDescricaoDeposito() {
        return descricaoDeposito;
    }

    public List<Estorno> getListaEstornos() {
        return listaEstornos;
    }

    public Map<Produto, Integer> getListaProdutos() {
        return listaProdutos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.codigoDeposito);
        hash = 23 * hash + Objects.hashCode(this.descricaoDeposito);
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
        final Deposito other = (Deposito) obj;
        if (!Objects.equals(this.codigoDeposito, other.codigoDeposito)) {
            return false;
        }
        return Objects.equals(this.descricaoDeposito, other.descricaoDeposito);
    }

    @Override
    public String toString() {
        return String.format("Código do Depósito: %s ;   Descrição do Depósito: %s", codigoDeposito, descricaoDeposito);
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public String identity() {
        return this.codigoDeposito;
    }

    public void adicionarProduto(Produto prod, int quantidade) {
        if (listaProdutos.containsKey(prod)) {
            quantidade = listaProdutos.get(prod) + quantidade;
        }
        listaProdutos.put(prod, quantidade);
    }

    public void adicionarEstorno(Estorno es) {
        listaEstornos.add(es);
    }
}
