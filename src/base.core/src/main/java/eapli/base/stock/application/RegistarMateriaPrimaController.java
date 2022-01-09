/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.base.stock.repositories.MateriaPrimaRepository;


public class RegistarMateriaPrimaController {
    private final ListCategoriasService svcCategorias = new ListCategoriasService();
    private final MateriaPrimaRepository materiaPrimaRepository = PersistenceContext.repositories().materiasPrimas();

    public MateriaPrima registarMateriaPrima(final String codigoInterno, final String descricao, final String nomeFichaTecnica, final Categoria categoria, final UnidadeMedida unidadeMedida) {


        final MateriaPrima novaMateriaPrima = new MateriaPrima(codigoInterno, descricao, nomeFichaTecnica,  unidadeMedida,categoria);

        return this.materiaPrimaRepository.save(novaMateriaPrima);
    }


    public Iterable<Categoria> categorias() {
        return this.svcCategorias.todasCategorias();
    }


}
