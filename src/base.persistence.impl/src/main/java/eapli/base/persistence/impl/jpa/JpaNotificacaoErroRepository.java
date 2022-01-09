/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.comunicacao.domain.NotificacaoErroProcessamento;
import eapli.base.comunicacao.repositories.NotificacaoErroRepository;

public class JpaNotificacaoErroRepository extends BaseJpaRepositoryBase<NotificacaoErroProcessamento, Integer, Integer> implements NotificacaoErroRepository {

    public JpaNotificacaoErroRepository() {
        super("idNotificacao");
    }

}
