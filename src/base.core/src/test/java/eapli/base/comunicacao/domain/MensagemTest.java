package eapli.base.comunicacao.domain;


import org.junit.Test;

import java.sql.Timestamp;

public class MensagemTest {


    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed2() {
        Mensagem instance = new MensagemConsumo("M01", new Timestamp(1), "MP01", 2, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed3() {
        Mensagem instance = new MensagemConsumo("M01", new Timestamp(1), null, 2, "DEP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed4() {
        Mensagem instance = new MensagemConsumo("M01", null, "MP01", 2, "DEP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed5() {
        Mensagem instance = new MensagemConsumo(null, new Timestamp(1), "MP01", 2, "DEP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed7() {
        Mensagem instance = new MensagemEntregaProducao("M01", new Timestamp(1), "P01", 2, null, "L01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed8() {
        Mensagem instance = new MensagemEntregaProducao("M01", new Timestamp(1), null, 2, "DEP01", "L01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed9() {
        Mensagem instance = new MensagemEntregaProducao("M01", null, "P01", 2, "DEP01", "L01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed10() {
        Mensagem instance = new MensagemEntregaProducao(null, new Timestamp(1), "P01", 2, "DEP01", "L01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed11() {
        Mensagem instance = new MensagemEstorno("M01", new Timestamp(1), "P01", 2, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed12() {
        Mensagem instance = new MensagemEstorno("M01", new Timestamp(1), null, 2, "DEP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed13() {
        Mensagem instance = new MensagemEstorno("M01", null, "P01", 2, "DEP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed14() {
        Mensagem instance = new MensagemEstorno(null, new Timestamp(1), "P01", 2, "DEP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed15() {
        Mensagem instance = new MensagemFimAtividade("M01", new Timestamp(1), null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed16() {
        Mensagem instance = new MensagemFimAtividade("M01", null, "OP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed17() {
        Mensagem instance = new MensagemFimAtividade(null, new Timestamp(1), "OP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed18() {
        Mensagem instance = new MensagemInicioAtividade("M01", new Timestamp(1), null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed19() {
        Mensagem instance = new MensagemInicioAtividade("M01", null, "OP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed20() {
        Mensagem instance = new MensagemInicioAtividade(null, new Timestamp(1), "OP01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed21() {
        Mensagem instance = new MensagemParagem("M01", new Timestamp(1), null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed22() {
        Mensagem instance = new MensagemParagem("M01", null, "COD01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed23() {
        Mensagem instance = new MensagemParagem(null, new Timestamp(1), "COD01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed25() {
        Mensagem instance = new MensagemProducao("M01", new Timestamp(1), null, 2, "LOT01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed26() {
        Mensagem instance = new MensagemProducao("M01", null, "P01", 2, "LOT01");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed27() {
        Mensagem instance = new MensagemProducao(null, new Timestamp(1), "P01", 2, "LOT01");
    }





}