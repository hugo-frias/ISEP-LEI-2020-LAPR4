package eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica;

import eapli.base.producao.application.RegistarLinhaProducaoController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class RegistarLinhaProducaoUI extends AbstractUI {


    private final RegistarLinhaProducaoController theControler = new RegistarLinhaProducaoController();

    @Override
    protected boolean doShow() {

        String answer = "N";

        final String idLinhaProducao = Console.readLine("ID da Linha Produção (Let be empty to leave) :");

        if (idLinhaProducao.isEmpty()) return false;

        while (!answer.equalsIgnoreCase("S")) {
            answer = Console.readLine("Confirma os dados (Responda S/N)?");
            if (answer.equals("N")) {
                System.out.println("\nOs dados não foram guardados!");
                return false;
            }
        }

        try {
            this.theControler.registarLinhaProducao(idLinhaProducao);
            System.out.println("\nDados guardados com sucesso!");
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("\nVoçê tentou adicionar uma linha de produção que já existe no sistema.");
        }


        return false;
    }

    @Override
    public String headline() {
        return null;
    }
}