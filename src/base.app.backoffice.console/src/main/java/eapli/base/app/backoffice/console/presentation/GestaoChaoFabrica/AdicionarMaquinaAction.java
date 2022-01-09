package eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica;

import eapli.framework.actions.Action;

public class AdicionarMaquinaAction implements Action {

    @Override
    public boolean execute() {
        return new AdicionarMaquinaUI().doShow();
    }
    
}
