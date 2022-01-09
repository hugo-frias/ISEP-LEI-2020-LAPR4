/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.comunicacao.domain;

import eapli.framework.domain.model.AggregateRoot;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class NotificacaoErroProcessamento implements AggregateRoot<Integer> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotificacao;
    
    @Column(nullable = false)
    private String descricaoErro;
    
    @OneToOne
    private Mensagem mensagem;
    
    private NotificacaoStatus status;

    public NotificacaoErroProcessamento() {
    }

    public NotificacaoErroProcessamento(String descricaoErro, Mensagem mensagem) {
        this.descricaoErro = descricaoErro;
        this.mensagem = mensagem;
        this.status = NotificacaoStatus.ATIVA;
    }

    @Override
    public String toString() {
        return "NotificacaoErroProcessamento{" + "descricaoErro=" + descricaoErro + '}';
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
