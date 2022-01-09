package eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica;

import eapli.base.producao.application.AdicionarMaquinaController;
import eapli.base.producao.domain.LinhaProducao;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class AdicionarMaquinaUI extends AbstractUI {

    private final AdicionarMaquinaController theController = new AdicionarMaquinaController();

    @Override
    protected boolean doShow() {
        final Iterable<LinhaProducao> linhasProducao = this.theController.mostrarListaLinhasProducao();
        if (!linhasProducao.iterator().hasNext()) {
            System.out.println("Não existem linhas de produção no sistema.");
        } else {
            String answer = "N";

            final SelectWidget<LinhaProducao> selector = new SelectWidget<>("Linhas de produção:", linhasProducao, new LinhasProducaoPrinter());
            selector.show();
            final LinhaProducao linha = selector.selectedElement();

            if (linha == null) {
                return false;
            }

            final String codigoInterno = Console.readLine("Código Interno");
            final String numeroSerie = Console.readLine("Número de série");
            final String descricao = Console.readLine("Descrição");
            final DataInstalacaoDataWidget dataInstalacao = new DataInstalacaoDataWidget();
            dataInstalacao.show();
            final String marca = Console.readLine("Marca");
            final String modelo = Console.readLine("Modelo");
            final int posicao = Console.readInteger("Posição na linha");

            while (!answer.equalsIgnoreCase("S")) {
                answer = Console.readLine("Confirma os dados (Responda S/N)?");
                if (answer.equals("N")) {
                    System.out.println("\nOs dados não foram guardados!");
                    return false;
                }
            }

            try {
                this.theController.registarMaquina(linha, codigoInterno, numeroSerie, descricao, dataInstalacao.Data(), marca, modelo, posicao);
                System.out.println("\nDados guardados com sucesso!");
            } catch (@SuppressWarnings("unused") final IllegalArgumentException e) {
                System.out.println("\nVoçê introduziu dados errados.");
            } catch (@SuppressWarnings("unused") final IndexOutOfBoundsException e) {
                System.out.println("\nVoçê introduziu uma posição inválida.");
            }
        }

        return true;
    }

    @Override
    public String headline() {
        return "Adicionar uma máquina";
    }
}
