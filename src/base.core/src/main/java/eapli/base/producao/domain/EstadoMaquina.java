/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.domain;

import eapli.framework.domain.model.AggregateRoot;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EstadoMaquina implements AggregateRoot<Integer> {
    
    @Id
    private Integer numeroIdUnico;
    
    @Column
    private String status;

    public EstadoMaquina(int numeroIdUnico, String status) {
        this.numeroIdUnico = numeroIdUnico;
        this.status = status;
    }

    @Override
    public boolean sameAs(Object other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer identity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


       
}

