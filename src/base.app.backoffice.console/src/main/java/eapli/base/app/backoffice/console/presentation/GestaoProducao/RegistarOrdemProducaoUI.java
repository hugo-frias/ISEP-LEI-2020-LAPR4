package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.producao.application.RegistarOrdemProducaoController;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.time.LocalDate;

public class RegistarOrdemProducaoUI extends AbstractUI {

    private final RegistarOrdemProducaoController theController = new RegistarOrdemProducaoController();

    @Override
    protected boolean doShow() {

        final Iterable<Produto> todosProdutos = theController.produtos();

        String answer = "N";

        final SelectWidget<Produto> selector = new SelectWidget<>("Produtos:", todosProdutos, new ProdutosPrinter());
        selector.show();

        final Produto aqueleProduto = selector.selectedElement();

        final String identificador = Console.readLine("Identificador da Ordem. (Deixe vazio para retornar)");

        if (identificador.isEmpty()) {
            return false;
        }

        final double quantidadePretendida = Console.readDouble("Quantidade pretendida. (Escreva 0 para retornar)");

        if (quantidadePretendida == 0) {
            return false;
        }

        String dataPrevistaString = ""; LocalDate today = LocalDate.now(); LocalDate dataPrevista;

            do {
                try {
                    dataPrevistaString = Console.readLine("Introduza a data prevista para execução. Formato correto: YYYY/MM/DD. A data deve ser superior e/ou igual à data de hoje. (Deixe vazio para retornar)");
                    String[] auxData = dataPrevistaString.split("/");
                    dataPrevista = LocalDate.of(Integer.parseInt(auxData[0]), Integer.parseInt(auxData[1]), Integer.parseInt(auxData[2]));
                    if (dataPrevistaString.isEmpty()) {
                        return false;
                    }
                } catch(Exception e) {
                    System.out.println("Erro na introdução da data: " + e.getMessage() + "\n");
                    dataPrevista = LocalDate.of(2000, 1, 1);
                }
            } while(!dataPrevistaString.isEmpty() && today.compareTo(dataPrevista) > 0);



        final String tipoUnidade = Console.readLine("Introduza o tipo de unidade da Ordem de produção. (Deixe vazio para retornar)");

        if (tipoUnidade.isEmpty()) {
            return false;
        }

        while (!answer.equalsIgnoreCase("S")) {
            answer = Console.readLine("Confirma os dados (Responda S/N)?");
            if (answer.equals("N")) {
                System.out.println("\nOs dados não foram guardados!");
                return false;
            }
        }

        UnidadeMedida unidadeMedida = new UnidadeMedida(tipoUnidade);

        try {
            OrdemProducao aux = theController.registarOrdemProducao(identificador, aqueleProduto, dataPrevista, unidadeMedida, quantidadePretendida);
            System.out.println(aux.toString() + ".\n");
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Você tentou registar uma Ordem de Produção já existente no sistem e/ou inválida. \n");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Adicionar Produto.";
    }
}
