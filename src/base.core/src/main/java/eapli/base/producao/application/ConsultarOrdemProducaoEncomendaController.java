/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.application;

import eapli.base.producao.domain.OrdemProducao;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author joaoa
 */
public class ConsultarOrdemProducaoEncomendaController {

    ConsultarOrdemProducaoService cps = new ConsultarOrdemProducaoService();

    public void run(String idEncomenda) {
        List<OrdemProducao> le = cps.guardarListaOredemProducao(idEncomenda);
        cps.apresentarLista(le);

    }
}
