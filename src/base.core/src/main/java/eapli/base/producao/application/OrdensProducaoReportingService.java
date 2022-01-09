/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.application;

import eapli.base.producao.domain.ExecucaoOrdemProducao;
import eapli.base.producao.domain.Maquina;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.domain.TempoExecucao;
import eapli.base.stock.domain.Deposito;
import eapli.base.stock.domain.Estorno;
import eapli.base.stock.domain.Lote;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.MovimentoStock;
import eapli.base.stock.domain.Produto;
import eapli.framework.util.Console;
import java.util.ArrayList;
import java.util.Map;

public class OrdensProducaoReportingService {

    public void listarOrdemProducao(OrdemProducao ordem) {
        printHeader(ordem.getIdOrdemProducao());

        System.out.println("Data prevista de execução: " + ordem.getDataPrevistaExecucao());
        System.out.println("Data real de execução: " + ordem.getDatadeEmissao());
        System.out.println("Id do produto: " + ordem.getProduto().getCodigoFabrico());
        if(ordem.getEncomenda() != null) {
            System.out.println("Id da encomenda: " + ordem.getEncomenda().getIdEncomenda());
        }

        if (!ordem.getExecucaoOrdemProducao().getStatus().toString().equals("Pendente")) {

            ExecucaoOrdemProducao execucao = ordem.getExecucaoOrdemProducao();

            System.out.println("Quantidade de produto pretendida: " + execucao.getQuantidadePretendida());
            System.out.println("Quantidade de produto produzida: " + execucao.getQuantidadeProduzida());
            listarTempoExecucaoOrdem(execucao);
            listarTempoExecucaoPorMaquina(execucao);
            listarConsumosTotaisPorMateriaPrima(execucao);
            listarConsumosTotaisPorProduto(execucao);
            listarDesviosTotaisPorMateriaPrima(execucao);
            listarDesviosTotaisPorProduto(execucao);
            listarQuantidadesProdutoPorLote(execucao);
            listarQuantidadesProdutoPorDeposito(execucao);
            listarEstornosPorDeposito(execucao);
            listarMovimentosStockPorDeposito(execucao);
        }

        String answer;
        while (true) {
            answer = Console.readLine("\nDigite next para continuar");
            if(answer.equals("next")) {
                break;
            }
        }

    }

    protected void printHeader(String id) {
        System.out.println("\n-> Ordem de Producao com id " + id);
        System.out.println();
    }

    private void listarTempoExecucaoOrdem(ExecucaoOrdemProducao execucao) {
        System.out.println("Data e hora iniciais da ordem: " + execucao.getTempoExecucao().getInicio());
        System.out.println("Data e hora finais da ordem: " + execucao.getTempoExecucao().getFim());
        System.out.println("Tempo bruto total da ordem: " + execucao.getTempoExecucao().getTempoTotalBruto());
        System.out.println("Tempo efetivo total da ordem: " + execucao.getTempoExecucao().getTempoTotalEfetivo());
    }

    private void listarTempoExecucaoPorMaquina(ExecucaoOrdemProducao execucao) {
        System.out.println("\n-> Detalhes de tempos relativos a cada máquina\n");
        Map<Maquina, TempoExecucao> lista = execucao.getListaMaquinasTempo();
        for (Maquina m : lista.keySet()) {
            System.out.println("Id da máquina: " + m.getCodigoInterno());
            System.out.println("Data e hora iniciais da máquina: " + lista.get(m).getInicio());
            System.out.println("Data e hora finais da máquina: " + lista.get(m).getFim());
            System.out.println("Tempo bruto total da máquina: " + lista.get(m).getTempoTotalBruto());
            System.out.println("Tempo efetivo total da máquina: " + lista.get(m).getTempoTotalEfetivo());
            System.out.println();
        }
    }

    private void listarConsumosTotaisPorMateriaPrima(ExecucaoOrdemProducao execucao) {
        System.out.println("\n-> Consumos totais por matéria-prima\n");
        Map<MateriaPrima, Integer> lista = execucao.getListaConsumosMateriaPrima();
        for (MateriaPrima m : lista.keySet()) {
            System.out.println("Matéria prima com id  " + m.getCodigoInterno() + ": " + lista.get(m));
        }
    }

    private void listarConsumosTotaisPorProduto(ExecucaoOrdemProducao execucao) {
        System.out.println("\n-> Consumos totais por produto\n");
        Map<Produto, Integer> lista = execucao.getListaConsumosProduto();
        for (Produto m : lista.keySet()) {
            System.out.println("Produto com id  " + m.getCodigoFabrico() + ": " + lista.get(m));
        }
    }

    private void listarDesviosTotaisPorMateriaPrima(ExecucaoOrdemProducao execucao) {
        System.out.println("\n-> Desvios totais por matéria-prima\n");
        Map<MateriaPrima, Integer> lista = execucao.getListaDesviosMateriaPrima();
        for (MateriaPrima m : lista.keySet()) {
            System.out.println("Matéria prima com id  " + m.getCodigoInterno() + ": " + lista.get(m));
        }
    }

    private void listarDesviosTotaisPorProduto(ExecucaoOrdemProducao execucao) {
        System.out.println("\n-> Desvios totais por produto\n");
        Map<Produto, Integer> lista = execucao.getListaDesviosProdutos();
        for (Produto m : lista.keySet()) {
            System.out.println("Produto com id  " + m.getCodigoFabrico() + ": " + lista.get(m));
        }
    }

    private void listarQuantidadesProdutoPorLote(ExecucaoOrdemProducao execucao) {
        System.out.println("\n-> Quantidades totais do produto por lote\n");
        ArrayList<Lote> lista = new ArrayList<>(execucao.getListaLotes());
        for (Lote l : lista) {
            System.out.println("Lote com id  " + l.getIdLote() + ": " + l.getQuantidade());
        }
    }

    private void listarQuantidadesProdutoPorDeposito(ExecucaoOrdemProducao execucao) {
        System.out.println("\n-> Quantidades totais de matéria prima inseridas por depósito\n");
        ArrayList<Deposito> lista = new ArrayList<>(execucao.getListaDepositos());
        for (Deposito l : lista) {
            for (Produto m : l.getListaProdutos().keySet()) {
                System.out.println("Depósito com id  " + l.getCodigoDeposito() + " e matéria-prima com id " + m.getCodigoFabrico() + ": " + l.getListaProdutos().get(m));
            }
        }
    }

    private void listarEstornosPorDeposito(ExecucaoOrdemProducao execucao) {
        System.out.println("\n-> Estornos por depósito\n");
        ArrayList<Deposito> lista = new ArrayList<>(execucao.getListaDepositos());
        for (Deposito l : lista) {
            for (Estorno m : l.getListaEstornos()) {
                System.out.println("Depósito com id  " + l.getCodigoDeposito() + " e estorno de materia-prima com id " + m.getIdMateriaPrima() + ": " + m.getQuantidade());
            }
        }
    }
    
    private void listarMovimentosStockPorDeposito(ExecucaoOrdemProducao execucao) {
        System.out.println("\n-> Movimentos de stock por depósito\n");
        ArrayList<Deposito> listaD = new ArrayList<>(execucao.getListaDepositos());
        ArrayList<MovimentoStock> listaMS = new ArrayList<>(execucao.getListaMovimentosStock());
        for (Deposito d : listaD) {
            for(MovimentoStock m : listaMS) {
                if(m.getCodigoDepositoOrigem().equals(d.getCodigoDeposito())) {
                    if(m.getEntrada() == 0) {
                        System.out.println("Saída da matéria-prima com id " + m.getIdMateriaPrima() + " do depósito com id " + d.getCodigoDeposito() + " na data e hora " + m.getDataHora() + ": " + m.getSaida());
                    } else {
                        System.out.println("Entrada da matéria-prima com id " + m.getIdMateriaPrima() + " do depósito com id " + d.getCodigoDeposito() + " na data e hora " + m.getDataHora() + ": " + m.getEntrada());
                    }
                }
            }
        }
    }    

}
