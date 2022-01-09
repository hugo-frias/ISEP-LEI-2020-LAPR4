/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.repositories;

import eapli.base.producao.domain.Encomenda;
import eapli.framework.domain.repositories.DomainRepository;

/**
 *
 * @author joaoa
 */
public interface EncomendaRepository extends DomainRepository<String, Encomenda>{
    public Encomenda getEncomendaById(String codigo);
}
