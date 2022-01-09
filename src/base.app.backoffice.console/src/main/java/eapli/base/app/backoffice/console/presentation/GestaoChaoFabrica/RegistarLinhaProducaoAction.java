package eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica;

import eapli.framework.actions.Action;

/**
 *
 * @author Diogo Ribeiro
 */

public class RegistarLinhaProducaoAction implements Action {

    @Override
    public boolean execute() {
        return new RegistarLinhaProducaoUI().doShow();
    }

}