/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FichaProducao")
@Entity
public class FichaProducao implements AggregateRoot<String> {

    @Id
    @Column(unique = true)
    private String idFichaDeProducao;

    @ElementCollection
    @MapKeyColumn(name = "Produto")
    @Column(name = "Quantidade")
    private Map<Produto, Integer> listaProdutos;

    @ElementCollection
    @MapKeyColumn(name = "MateriaPrima")
    @Column(name = "Quantidade")
    private Map<MateriaPrima, Integer> listaMateriasPrimas;

    public FichaProducao() {
    }

    public FichaProducao(String idFichaDeProducao, Map<Produto, Integer> listaProdutos, Map<MateriaPrima, Integer> listaMateriasPrimas) {

        boolean validateProdutos, validateMateriasPrimas;

        Preconditions.nonNull(idFichaDeProducao, "Id da linha de produção não pode ser nula");
        Preconditions.nonNull(listaProdutos, "Lista de produtos não pode ser nula");
        Preconditions.nonNull(listaMateriasPrimas, "Lista de matérias-primas não pode ser nula");
        Preconditions.nonEmpty(idFichaDeProducao, "Id da linha de produção não pode estar vazio.");

        validateProdutos = verifyListaProdutos(listaProdutos);
        validateMateriasPrimas = verifyListaMateriasPrimas(listaMateriasPrimas);
        
        if(!validateProdutos || !validateMateriasPrimas) {
            throw new IllegalArgumentException();
        }

        this.idFichaDeProducao = idFichaDeProducao;
        this.listaProdutos = listaProdutos;
        this.listaMateriasPrimas = listaMateriasPrimas;
    }

    @XmlElement
    public String getIdFichaDeProducao() {
        return idFichaDeProducao;
    }
    
    public Map<Produto, Integer> getListaProdutos() {
        return listaProdutos;
    }
    
    public Map<MateriaPrima, Integer> getListaMateriasPrimas() {
        return listaMateriasPrimas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idFichaDeProducao);
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
        final FichaProducao other = (FichaProducao) obj;
        if (!Objects.equals(this.idFichaDeProducao, other.idFichaDeProducao)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FichaDeProducao{" + "idFichaDeProducao=" + idFichaDeProducao + '}';
    }

    @Override
    public boolean sameAs(Object other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String identity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean verifyListaProdutos(Map<Produto, Integer> listaProdutos) {
        if (listaProdutos.isEmpty()) {
            return false;
        }
        return listaProdutos.keySet().stream().noneMatch((p) -> (listaProdutos.get(p) <= 0));
    }

    private boolean verifyListaMateriasPrimas(Map<MateriaPrima, Integer> listaMateriasPrimas) {
        if (listaMateriasPrimas.isEmpty()) {
            return false;
        }
        return listaMateriasPrimas.keySet().stream().noneMatch((p) -> (listaMateriasPrimas.get(p) <= 0));
    }

}
