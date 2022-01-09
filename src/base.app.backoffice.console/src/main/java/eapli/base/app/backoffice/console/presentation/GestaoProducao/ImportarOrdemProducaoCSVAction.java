package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.framework.actions.Action;

public class ImportarOrdemProducaoCSVAction implements Action {
    @Override
    public boolean execute() {
        return new ImportarOrdemProducaoCSVUI().doShow();
    }
}
