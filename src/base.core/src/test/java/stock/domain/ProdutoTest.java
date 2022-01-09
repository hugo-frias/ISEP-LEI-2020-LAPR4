package stock.domain;

import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.FichaProducao;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ProdutoTest {

    @Test
    public void ensureProdutoComAtributos() throws Exception {
        new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComCodigoFabrico() throws Exception {
        new Produto(null, "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComCodigoComercial() throws Exception {
        new Produto("503", null, "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComDescBreve() throws Exception {
        new Produto("503", "602", null, "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComDescCompleta() throws Exception {
        new Produto("503", "602", "Rolhas", null, new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComCategoria() {
        new Produto("503", "602", "Rolhas", "Rolhas Premiadas", null, new UnidadeMedida("unidades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComUnidadeMedida() throws Exception {
        new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComCodigoFabricoNaoVazio() throws Exception {
        new Produto("", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComCodigoComercialNaoVazia() throws Exception {
        new Produto("503", "", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComDescBreveNaoVazia() throws Exception {
        new Produto("503", "602", "", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoComDescCompletaNaoVazia() throws Exception {
        new Produto("503", "602", "Rolhas", "", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoNotPresentInListaProdutos() throws Exception {
        Produto instance = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        Map<Produto, Integer> listaProdutos = new HashMap<>();
        listaProdutos.put(instance, 20);
        MateriaPrima materiaP = new MateriaPrima("MP1", "Vidros", "ficha", new UnidadeMedida("KG"), new Categoria("cat03", "cortiça"));
        Map<MateriaPrima, Integer> listaMateriasPrimas = new HashMap<>();
        listaMateriasPrimas.put(materiaP, 10);
        FichaProducao instance1 = new FichaProducao("FP001", listaProdutos, listaMateriasPrimas);
        instance.adicionarFichaProducao(instance1);
    }
}
