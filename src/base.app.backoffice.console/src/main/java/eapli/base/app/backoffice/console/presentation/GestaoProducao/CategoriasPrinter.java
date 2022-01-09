package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.domain.Categoria;
import eapli.framework.visitor.Visitor;

public class CategoriasPrinter implements Visitor<Categoria> {


    @Override
    public void visit(Categoria visitee) {
        System.out.printf("%-30s", visitee.toString());
    }
}
