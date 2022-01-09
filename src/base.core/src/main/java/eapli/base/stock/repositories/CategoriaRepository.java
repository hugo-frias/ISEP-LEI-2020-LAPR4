/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.repositories;

import eapli.base.stock.domain.Categoria;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

/**
 *
 * @author Hugo
 */
public interface CategoriaRepository extends DomainRepository<String, Categoria> {

    public Categoria getCategoriaById(String codigo);

}
