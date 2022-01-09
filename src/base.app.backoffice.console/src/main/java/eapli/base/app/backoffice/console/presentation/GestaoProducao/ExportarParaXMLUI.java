package eapli.base.app.backoffice.console.presentation.GestaoProducao;

import eapli.base.stock.application.ExportarParaXMLController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ExportarParaXMLUI extends AbstractUI {
    private final ExportarParaXMLController exc = new ExportarParaXMLController();
    public ExportarParaXMLUI(){
    }

    @Override
    protected boolean doShow() {
        System.out.println("Deseja exportar:");
        final String encomenda = Console.readLine("Encomenda?");
        final String linha = Console.readLine("Linha de Produção?");
        final String maquina = Console.readLine("Maquina?");
        final String ordemProduc = Console.readLine("Ordem de Produção?");
        final String dataIn = Console.readLine("Digite uma data de inicio");
        final String dataFim = Console.readLine("Digite uma data de fim");

        final String categoria = Console.readLine("Categoria?");
        final String deposito = Console.readLine("Depósito?");
        final String materiaPrima = Console.readLine("Matéria Prima?");
        final String produto = Console.readLine("Produto?");



        try {
            this.exc.exportarClasseParaXML(encomenda, linha, maquina, ordemProduc, dataIn, dataFim, categoria,
                    deposito, materiaPrima, produto);
            System.out.println("Exportação concluida com sucesso!");
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Erro ao exportar");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String headline() {
        return null;
    }

}
