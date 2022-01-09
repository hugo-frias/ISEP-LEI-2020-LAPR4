/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.domain;

import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.FichaProducao;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class FichaProducaoTest {

    @Test(expected = IllegalArgumentException.class)
    public void ensureIdFichaProducaoIsNotNull() throws Exception {
        Map<Produto, Integer> map1 = new HashMap<>();
        Produto prod = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        MateriaPrima materiaP = new MateriaPrima("MP1", "Vidros", "ficha", new UnidadeMedida("KG"), new Categoria("cat03", "cortiça"));
        map1.put(prod, 10);
        Map<MateriaPrima, Integer> map2 = new HashMap<>();
        map2.put(materiaP, 10);
        FichaProducao instance = new FichaProducao(null, map1, map2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureListaProdutosIsNotNull() throws Exception {
        MateriaPrima materiaP = new MateriaPrima("MP1", "Vidros", "ficha", new UnidadeMedida("KG"), new Categoria("cat03", "cortiça"));
        Map<MateriaPrima, Integer> map2 = new HashMap<>();
        map2.put(materiaP, 10);
        FichaProducao instance = new FichaProducao("FP001", null, map2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureListaMateriasPrimasIsNotNull() throws Exception {
        Map<Produto, Integer> map1 = new HashMap<>();
        Produto prod = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        map1.put(prod, 10);
        FichaProducao instance = new FichaProducao("FP001", map1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureIdFichaProducaoIsNotEmpty() throws Exception {
        Map<Produto, Integer> map1 = new HashMap<>();
        Produto prod = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        MateriaPrima materiaP = new MateriaPrima("MP1", "Vidros", "ficha", new UnidadeMedida("KG"), new Categoria("cat03", "cortiça"));
        map1.put(prod, 10);
        Map<MateriaPrima, Integer> map2 = new HashMap<>();
        map2.put(materiaP, 10);
        FichaProducao instance = new FichaProducao("", map1, map2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureListaProdutosIsNotEmpty() throws Exception {
        Map<Produto, Integer> map1 = new HashMap<>();
        MateriaPrima materiaP = new MateriaPrima("MP1", "Vidros", "ficha", new UnidadeMedida("KG"), new Categoria("cat03", "cortiça"));
        Map<MateriaPrima, Integer> map2 = new HashMap<>();
        map2.put(materiaP, 10);
        FichaProducao instance = new FichaProducao("FP001", map1, map2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureListaMateriasPrimasIsNotEmpty() throws Exception {
        Map<Produto, Integer> map1 = new HashMap<>();
        Produto prod = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        map1.put(prod, 10);
        Map<MateriaPrima, Integer> map2 = new HashMap<>();
        FichaProducao instance = new FichaProducao("FP001", map1, map2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureListaProdutosQuantidadeIsNotNegative() throws Exception {
        MateriaPrima materiaP = new MateriaPrima("MP1", "Vidros", "ficha", new UnidadeMedida("KG"), new Categoria("cat03", "cortiça"));
        Map<MateriaPrima, Integer> map2 = new HashMap<>();
        map2.put(materiaP, 10);
        Produto instance = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        Map<Produto, Integer> listaProdutos = new HashMap<>();
        listaProdutos.put(instance, -1);
        FichaProducao instance1 = new FichaProducao("FP001", listaProdutos, map2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureListaMateriasPrimasQuantidadeIsNotNegative() throws Exception {
        MateriaPrima instance = new MateriaPrima("MP1", "Vidros", "ficha", new UnidadeMedida("KG"), new Categoria("cat03", "cortiça"));
        Map<MateriaPrima, Integer> listaMateriasPrimas = new HashMap<>();
        listaMateriasPrimas.put(instance, -1);
        Produto instance2 = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        Map<Produto, Integer> listaProdutos = new HashMap<>();
        listaProdutos.put(instance2, -1);
        FichaProducao instance1 = new FichaProducao("FP001", listaProdutos, listaMateriasPrimas);
    }

}
