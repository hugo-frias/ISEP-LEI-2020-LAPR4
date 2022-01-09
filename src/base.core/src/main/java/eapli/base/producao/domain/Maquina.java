/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Maquina")
@Entity
@Table
public class Maquina implements AggregateRoot<String> {

    private static final long serialVersionUID = 1L;

    @Version
    private long version;

    @Id
    @Column (unique = true, nullable = false)
    private String codigoInterno;
    @Column (unique = true)
    private String numSerie;
    @Column (nullable = false)
    private String descricao;
    @Column (nullable = false)
    private LocalDate dataInstalacao;
    @Column (nullable = false)
    private String marca;
    @Column (nullable = false)
    private String modelo;
    @Column 
    private FicheiroConfig fc;

    public Maquina() {
    }

    public Maquina(String codigoInterno, String numSerie, String descricao, LocalDate dataInstalacao, String marca, String modelo) {
        Preconditions.nonNull(codigoInterno, "Código Interno não pode ser nulo.");
        Preconditions.nonNull(numSerie, "Número de Série não pode ser nulo.");
        Preconditions.nonNull(descricao, "Descrição não pode ser nula.");
        Preconditions.nonNull(dataInstalacao, "Data de instação não pode ser nula.");
        Preconditions.nonNull(marca, "Marca não pode ser nula.");
        Preconditions.nonNull(modelo, "Modelo não pode ser nulo.");

        Preconditions.nonEmpty(codigoInterno, "Código Interno não pode estar vazio.");
        Preconditions.nonEmpty(numSerie, "Número de Série não pode estar vazio.");
        Preconditions.nonEmpty(descricao, "Descrição não pode estar vazia.");
        Preconditions.nonEmpty(marca, "Marca não pode estar vazia.");
        Preconditions.nonEmpty(modelo, "Modelo não pode estar vazio.");
        
        verifyDataInstalacao(dataInstalacao);

        this.codigoInterno = codigoInterno;
        this.numSerie = numSerie;
        this.descricao = descricao;
        this.dataInstalacao = dataInstalacao;
        this.marca = marca;
        this.modelo = modelo;
        this.fc= null;
    }
    @XmlElement
    public String getCodigoInterno() {
        return codigoInterno;
    }

    @XmlElement
    public String getModelo() {
        return modelo;
    }

    @XmlElement
    public String getDescricao() {
        return descricao;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    @XmlElement
    public long getVersion() {
        return version;
    }
    @XmlElement
    public String getNumSerie() {
        return numSerie;
    }

    public LocalDate getDataInstalacao() {
        return dataInstalacao;
    }
    @XmlElement
    public String getMarca() {
        return marca;
    }
    public FicheiroConfig getFicheiroConfig(){
        return fc;
    }

    public void setFicheiroConfig(FicheiroConfig fc) {
        this.fc = fc;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.codigoInterno);
        //hash = 97 * hash + Objects.hashCode(this.fabricante);
        hash = 97 * hash + Objects.hashCode(this.modelo);
        //hash = 97 * hash + Objects.hashCode(this.anoFabrico);
        hash = 97 * hash + Objects.hashCode(this.numSerie);
        hash = 97 * hash + Objects.hashCode(this.descricao);
        hash = 97 * hash + Objects.hashCode(this.dataInstalacao);
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
        final Maquina other = (Maquina) obj;
        if (!Objects.equals(this.codigoInterno, other.codigoInterno)) {
            return false;
        }
        /*if (!Objects.equals(this.fabricante, other.fabricante)) {
            return false;
        }*/
        if (!Objects.equals(this.modelo, other.modelo)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        /*if (!Objects.equals(this.anoFabrico, other.anoFabrico)) {
            return false;
        }*/
        if (!Objects.equals(this.numSerie, other.numSerie)) {
            return false;
        }
        return Objects.equals(this.dataInstalacao, other.dataInstalacao);
    }

    @Override
    public String toString() {

        return String.format("Código interno: %s ; Número da Série: %s ;   Descrição da máquina: %s ;  Data de instalação da máquina: %s ;   Marca da máquina: %s ; Modelo da máquina: %s." ,codigoInterno, numSerie, descricao, dataInstalacao.toString(), marca, modelo);
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public String identity() {
        return this.codigoInterno;
    }
    
    private void verifyDataInstalacao(LocalDate data) {
        LocalDate dataAtual = LocalDate.now();
        if(data.isAfter(dataAtual)) {
            throw new IllegalArgumentException();
        }
    }
    
    
}
