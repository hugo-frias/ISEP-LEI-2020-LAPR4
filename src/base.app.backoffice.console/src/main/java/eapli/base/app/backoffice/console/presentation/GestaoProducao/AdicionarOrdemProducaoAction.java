package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.framework.actions.Action;

public class AdicionarOrdemProducaoAction implements Action {

    @Override
    public boolean execute() {
        return new RegistarOrdemProducaoUI().doShow();
    }
}
