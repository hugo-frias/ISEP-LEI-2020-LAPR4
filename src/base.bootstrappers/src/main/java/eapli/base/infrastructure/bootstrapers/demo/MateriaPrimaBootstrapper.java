/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.application.RegistarMateriaPrimaController;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.base.stock.repositories.CategoriaRepository;
import eapli.framework.actions.Action;

public class MateriaPrimaBootstrapper implements Action {

    private final RegistarMateriaPrimaController controller = new RegistarMateriaPrimaController();
    private final CategoriaRepository catRepo = PersistenceContext.repositories().categorias();

    @Override
    public boolean execute() {
        try {

            Categoria cat1 = new Categoria("C01", "Gerais");
            Categoria cat2 = new Categoria("C02", "Cortiça");
            Categoria cat3 = new Categoria("C03", "Vidros");
            /*final Categoria cat1 = catRepo.getCategoriaById("C01");
            final Categoria cat2 = catRepo.getCategoriaById("C02");
            final Categoria cat3 = catRepo.getCategoriaById("C03");*/

            final UnidadeMedida med1 = new UnidadeMedida(20, "Kg");

            controller.registarMateriaPrima("MP1", "Vidros", "fichaTecnicaVidros", cat1, med1);
            controller.registarMateriaPrima("MP2", "Madeira", "fichaTecnicaMadeira", cat1, med1);
            controller.registarMateriaPrima("MP3", "Asfalto", "fichaTecnicaAsfalto", cat1, med1);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
