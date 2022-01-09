package eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica;

import eapli.base.producao.domain.LinhaProducao;
import eapli.framework.visitor.Visitor;

public class LinhasProducaoPrinter implements Visitor<LinhaProducao> {

    @Override
    public void visit(LinhaProducao visitee) {
        System.out.printf("%-30s", visitee.toString());
    }
}
