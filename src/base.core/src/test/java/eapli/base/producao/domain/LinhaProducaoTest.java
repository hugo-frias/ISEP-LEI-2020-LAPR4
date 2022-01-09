package eapli.base.producao.domain;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class LinhaProducaoTest {

    private static final String idLinhaProducao = "DOM-03";

    @Test
    public void ensureLinhaProducaoComIDLinhaProducao() {
        new LinhaProducao(idLinhaProducao);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureIDLinhaProducaoExistente() {
        new LinhaProducao(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureIDLinhaProducaoNaoVazio() {
        new LinhaProducao("");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void ensurePosicaoIsNotEqualToZero() {
        LinhaProducao instance = new LinhaProducao("LP001");
        Maquina instance1 = new Maquina("M001", "20201234", "descrição", LocalDate.MIN, "Woven", "Woven-1");
        instance.adicionarMaquina(instance1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void ensurePosicaoIsNotInferiorToZero() {
        LinhaProducao instance = new LinhaProducao("LP001");
        Maquina instance1 = new Maquina("M001", "20201234", "descrição", LocalDate.MIN, "Woven", "Woven-1");
        instance.adicionarMaquina(instance1, -1);
    }

}
