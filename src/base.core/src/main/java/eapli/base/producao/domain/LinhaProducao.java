/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "LinhaProducao")
@Entity
public class LinhaProducao implements AggregateRoot<String> {



    @Id
    @Column(unique = true, nullable = false)
    private String idLinhaProducao;
    @Column (nullable = false)
    private boolean ativa;

    @OneToMany
    private List<Maquina> listaMaquinas;

    public LinhaProducao(String idLinhaProducao) {
        Preconditions.nonEmpty(idLinhaProducao);
        Preconditions.nonNull(idLinhaProducao);
        this.idLinhaProducao = idLinhaProducao;
        this.ativa = false;
        this.listaMaquinas = new LinkedList<>();
    }

    public LinhaProducao(String idLinhaProducao, boolean ativa) {
        Preconditions.nonEmpty(idLinhaProducao);
        Preconditions.nonNull(idLinhaProducao);
        this.idLinhaProducao = idLinhaProducao;
        this.ativa = ativa;
        this.listaMaquinas = new LinkedList<>();
    }

    protected LinhaProducao() {
        //for ORM
    }

    @XmlElement
    public String getIdLinhaProducao() {
        return idLinhaProducao;
    }

    @XmlElement
    public List<Maquina> getListaMaquinas() {
        return listaMaquinas;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void ativar() {
        ativa = true;
    }

    public void desativar() {
        ativa = false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idLinhaProducao);
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
        final LinhaProducao other = (LinhaProducao) obj;
        return Objects.equals(this.idLinhaProducao, other.idLinhaProducao);
    }


    @Override
    public String toString() {
        return String.format("Identificador da linha: %s", idLinhaProducao);
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public String identity() {
        return this.idLinhaProducao;
    }

    public void adicionarMaquina(Maquina maquina, int posicao) {
        if (listaMaquinas.size() < posicao) {
            listaMaquinas.add(maquina);
        } else {
            listaMaquinas.add(posicao-1, maquina);
        }
    }

}
