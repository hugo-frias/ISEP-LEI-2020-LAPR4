/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.Encomenda;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.repositories.EncomendaRepository;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author joaoa
 */
public class ConsultarOrdemProducaoService {

    private final OrdemProducaoRepository ordemProducaoRepository = PersistenceContext.repositories().ordensProducao();
    private final EncomendaRepository encomendaRepository = PersistenceContext.repositories().encomenda();

    //apresentar as encomendas disponiveis
    public List<String> apresentarEncomendas() {
        List<Encomenda> le = (List<Encomenda>) encomendaRepository.findAll();

        List<String> lf = new ArrayList<>();
        for (Encomenda encomenda : le) {
            System.out.println(encomenda.getIdEncomenda());
            lf.add(encomenda.getIdEncomenda());
        }
        return lf;
    }

    /**
     * Função que busca guardar a lista de Ordens de uma certa encomenda
     *
     * @param idEncomenda
     * @return
     */
    public List<OrdemProducao> guardarListaOredemProducao(String idEncomenda) {
        try {
            Encomenda encomenda;
            encomenda = (Encomenda) encomendaRepository.getEncomendaById(idEncomenda);

            List<OrdemProducao> odp = new ArrayList<>();
            odp = encomenda.getOrdemProducao();

            return odp;
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

    //Apresentar a listagem final das ordens de produção para cada encomenda
    public void apresentarLista(List<OrdemProducao> listaProducao) {
        try {
            if (listaProducao.size() > 0) {
                for (OrdemProducao odp : listaProducao) {
                    System.out.println("\n" + odp.toString());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
