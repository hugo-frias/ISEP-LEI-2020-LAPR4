/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.domain;

import javax.xml.bind.annotation.XmlElement;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import java.util.Map;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "Produto")
@Entity
public class Produto extends Item implements AggregateRoot<String> {

    private static final long serialVersionUID = 1L;

    @Version
    private long version;

    @Id
    @Column(unique = true, nullable = false)
    private String codigoFabrico;
    @Column(unique = true, nullable = false)
    private String codigoComercial;
    @Column(nullable = false)
    private String descricaoBreve;
    @Column(nullable = false)
    private String descricaoCompleta;


    @ManyToOne
    //@Column(nullable = false)
    private Categoria categoria;
    private UnidadeMedida unidade;
    @OneToOne(cascade = CascadeType.ALL)
    private FichaProducao fichaProducao;

    public Produto(String codigoFabrico, String codigoComercial, String descricaoBreve, String descricaoCompleta, Categoria categoria, UnidadeMedida unidadeMedida) {
        super(unidadeMedida, categoria);

        Preconditions.nonNull(codigoFabrico, "Código de fabrico não pode ser nulo.");
        Preconditions.nonNull(codigoComercial, "Código comercinal não pode ser nulo.");
        Preconditions.nonNull(descricaoBreve, "Descrição Breve não pode ser nula.");
        Preconditions.nonNull(descricaoCompleta, "Descrição completa não pode ser nula");

        Preconditions.nonEmpty(codigoFabrico, "Código de fabrico não pode estar vazio.");
        Preconditions.nonEmpty(codigoComercial, "Código comercinal não pode estar vazio.");
        Preconditions.nonEmpty(descricaoBreve, "Descrição Breve não pode estar vazia.");
        Preconditions.nonEmpty(descricaoCompleta, "Descrição completa não pode estar vazia");

        this.codigoFabrico = codigoFabrico;
        this.codigoComercial = codigoComercial;
        this.descricaoBreve = descricaoBreve;
        this.descricaoCompleta = descricaoCompleta;
        this.unidade = unidadeMedida;
        this.categoria = categoria;
        this.fichaProducao = null;
    }

    public Produto() {
        //for ORM
    }

    @XmlElement
    public String getCodigoFabrico() {
        return codigoFabrico;
    }

    @XmlElement
    public String getCodigoComercial() {
        return codigoComercial;
    }

    @XmlElement
    public String getDescricaoBreve() {
        return descricaoBreve;
    }

    @XmlElement
    public String getDescricaoCompleta() {
        return descricaoCompleta;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public long getVersion() {
        return version;
    }

    public String getcategoriaId() {
        return categoria.getCodigo();
    }
    @XmlElement
    public String getUnidadeMedidaId() {
        return unidade.getQuantidade()+" "+unidade.getTipoUnidade();
    }
    @XmlElement
    public Categoria getCategoria() {
        return categoria;
    }


    public UnidadeMedida getIdUnidadeMedida() {
        return unidade;
    }

    public FichaProducao getFichaProducao() {
        return fichaProducao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.codigoFabrico);
        hash = 31 * hash + Objects.hashCode(this.codigoComercial);
        hash = 31 * hash + Objects.hashCode(this.descricaoBreve);
        hash = 31 * hash + Objects.hashCode(this.descricaoCompleta);
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
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.codigoFabrico, other.codigoFabrico)) {
            return false;
        }
        if (!Objects.equals(this.codigoComercial, other.codigoComercial)) {
            return false;
        }
        if (!Objects.equals(this.descricaoBreve, other.descricaoBreve)) {
            return false;
        }
        return Objects.equals(this.descricaoCompleta, other.descricaoCompleta);
    }

    @Override
    public String toString() {
        return String.format("Código de fabrico: %s ;  Código comercial: %s ;   Descrição breve: %s ;     Descrição completa: %s ", codigoFabrico, codigoComercial, descricaoBreve, descricaoCompleta);
    }

    @Override
    public String identity() {
        return this.getCodigoFabrico();
    }

    public void adicionarFichaProducao(FichaProducao ficha) {
        Map<Produto, Integer> lista = ficha.getListaProdutos();
        lista.keySet().stream().filter((p) -> (p.equals(this))).forEachOrdered((_item) -> {
            throw new IllegalArgumentException();
        });
        this.fichaProducao = ficha;
    }

    public boolean hasFichaProducao() {
        return this.fichaProducao != null;
    }
}
