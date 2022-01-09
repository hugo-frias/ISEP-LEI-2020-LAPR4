/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.producao.domain.EstadoMaquina;
import eapli.base.producao.repositories.EstadoMaquinaRepository;

public class JpaEstadoMaquinaRepository extends BaseJpaRepositoryBase<EstadoMaquina, Integer, Integer> implements EstadoMaquinaRepository {

    public JpaEstadoMaquinaRepository() {
        super("numeroIdUnico");
    }

}

