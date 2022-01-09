package eapli.base.producao.domain;

import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class OrdemProducaoTest {


    @Test(expected = IllegalArgumentException.class)
    public void ensureDataPrevistaIsBiggerThanDataEmissao() throws Exception {
        Produto instanceProduto = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        ExecucaoOrdemProducao instanceExecucao = new ExecucaoOrdemProducao(100);
        new OrdemProducao("XXXX", LocalDate.of(2020, 2, 22), LocalDate.of(2020, 2, 19), instanceProduto, new UnidadeMedida(("unidades")), instanceExecucao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProdutoNullIsNotAllowed() {
        ExecucaoOrdemProducao instanceExecucao = new ExecucaoOrdemProducao(100);
        new OrdemProducao("XXXX", LocalDate.of(2020, 2, 19), LocalDate.of(2020, 2, 22), null, new UnidadeMedida(("unidades")), instanceExecucao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureExecucaoOrdemProducaoNullIsNotAllowed() throws Exception {
        Produto instanceProduto = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        new OrdemProducao("XXXX", LocalDate.of(2020, 2, 19), LocalDate.of(2020, 2, 22), instanceProduto, new UnidadeMedida(("unidades")), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureUnidadeMedidaNullIsNotAllowed() throws Exception {
        Produto instanceProduto = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        ExecucaoOrdemProducao instanceExecucao = new ExecucaoOrdemProducao(100);
        new OrdemProducao("XXXX", LocalDate.of(2020, 2, 19), LocalDate.of(2020, 2, 22), instanceProduto, null, instanceExecucao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDataEmissaoNullIsNotAllowed() throws Exception {
        Produto instanceProduto = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        ExecucaoOrdemProducao instanceExecucao = new ExecucaoOrdemProducao(100);
        new OrdemProducao("XXXX", null, LocalDate.of(2020, 2, 22), instanceProduto, new UnidadeMedida(("unidades")), instanceExecucao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDataPrevistaNullIsNotAllowed() throws Exception {
        Produto instanceProduto = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        ExecucaoOrdemProducao instanceExecucao = new ExecucaoOrdemProducao(100);
        new OrdemProducao("XXXX", LocalDate.of(2020, 2, 19), null, instanceProduto, new UnidadeMedida(("unidades")), instanceExecucao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureIdOrdemProducaoEmptyIsNotAllowed() throws Exception {
        Produto instanceProduto = new Produto("503", "602", "Rolhas", "Rolhas Premiadas", new Categoria("cat03", "cortiça"), new UnidadeMedida("unidades"));
        ExecucaoOrdemProducao instanceExecucao = new ExecucaoOrdemProducao(100);
        new OrdemProducao("", LocalDate.of(2020, 2, 19), LocalDate.of(2020, 2, 22), instanceProduto, new UnidadeMedida(("unidades")), instanceExecucao);
    }


}