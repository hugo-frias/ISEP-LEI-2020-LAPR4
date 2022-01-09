/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.application.RegistarMateriaPrimaController;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;


public class RegistarMateriaPrimaUI extends AbstractUI{
    private final RegistarMateriaPrimaController theController = new RegistarMateriaPrimaController();

    @Override
    protected boolean doShow() {
        final Iterable<Categoria> todasCategorias = this.theController.categorias();

        final SelectWidget<Categoria> selector = new SelectWidget<>("Categorias:", todasCategorias,
                new CategoriasPrinter());
        selector.show();

        final Categoria aquelaCategoria = selector.selectedElement();

        final String tipoUnidade = Console.readLine("Tipo de Unidade");

        UnidadeMedida aquelaUnidade = new UnidadeMedida(tipoUnidade);
        
        
        final String codigoInterno = Console.readLine("Código Interno");
        
        final String descricao = Console.readLine("Descrição");
        
        final String nomeFT = Console.readLine("Nome Ficha Técnica");
        
        
        try {
            this.theController.registarMateriaPrima(codigoInterno, descricao, nomeFT, aquelaCategoria, aquelaUnidade);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Você tentou introduzir uma matéria-prima que já se encontra no sistema.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Adicionar Matéria-Prima.";
    }
    
    
}
