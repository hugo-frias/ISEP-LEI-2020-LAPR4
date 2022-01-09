/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.domain.OrdemProducaoStatus;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import eapli.framework.application.UseCaseController;
import eapli.framework.util.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UseCaseController
public class ConsultarOrdensProducaoEstadoController {

    private final OrdemProducaoRepository ordemProducaoRepository = PersistenceContext.repositories().ordensProducao();
    private final OrdensProducaoReportingService service = new OrdensProducaoReportingService();

    public void listarOrdensProducaoEstado(OrdemProducaoStatus estado) {
        Iterable<OrdemProducao> listaOrdens = ordemProducaoRepository.findOrdensProducaoByEstado(estado);
        if (!listaOrdens.iterator().hasNext()) {
            System.out.println("Não existem ordens de produção no sistema com o estado escolhido!");
        } else {
            for (OrdemProducao ordem : listaOrdens) {
                service.listarOrdemProducao(ordem);
            }
            String answer;
            while (true) {
                answer = Console.readLine("\nDigite exit para sair");
                if (answer.equals("exit")) {
                    break;
                }
            }
        }
    }

    public List<OrdemProducaoStatus> mostrarEstadosOrdemProducao() {
        ArrayList<OrdemProducaoStatus> estados = new ArrayList<>();
        OrdemProducaoStatus[] aux = OrdemProducaoStatus.values();
        estados.addAll(Arrays.asList(aux));
        return estados;
    }
}
