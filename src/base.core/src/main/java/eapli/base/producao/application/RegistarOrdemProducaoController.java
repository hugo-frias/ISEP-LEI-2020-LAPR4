package eapli.base.producao.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.ExecucaoOrdemProducao;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;

import java.time.LocalDate;

public class RegistarOrdemProducaoController {

    private final OrdemProducaoRepository ordemProducaoRepository = PersistenceContext.repositories().ordensProducao();
    private final ListProdutoService svgProdutos = new ListProdutoService();

    public OrdemProducao registarOrdemProducao(String identificador, Produto aqueleProduto, LocalDate dataPrevista, UnidadeMedida tipoUnidade, double quantidadePretendida) {
        ExecucaoOrdemProducao aquelaExecucao = new ExecucaoOrdemProducao(quantidadePretendida);
        try {
            final OrdemProducao novaOrdemProducao = new OrdemProducao(identificador, LocalDate.now(), dataPrevista, aqueleProduto, tipoUnidade, aquelaExecucao);
            return this.ordemProducaoRepository.save(novaOrdemProducao);
        } catch (@SuppressWarnings("unused") Exception e) {
            System.out.println("Você tentou introduzir uma Ordem de Produção que já se encontra no sistema e/ou os dados encontram-se inválidos.");
            e.printStackTrace();
            return null;
        }
    }

    public Iterable<Produto> produtos() {
        return this.svgProdutos.todosProdutos();
    }
}
