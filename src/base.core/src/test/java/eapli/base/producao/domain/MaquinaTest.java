/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.domain;

import org.junit.Test;

import java.time.LocalDate;

public class MaquinaTest {

    @Test(expected = IllegalArgumentException.class)
    public void ensureCodigoInternoIsNotNull() {
        Maquina instance = new Maquina(null, "20201234", "descrição", LocalDate.MIN, "Woven", "Woven-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNumeroSerieIsNotNull() {
        Maquina instance = new Maquina("M001", null, "descrição", LocalDate.MIN, "Woven", "Woven-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescricaoIsNotNull() {
        Maquina instance = new Maquina("M001", "20201234", null, LocalDate.MIN, "Woven", "Woven-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDataInstalacaoIsNotNull() {
        Maquina instance = new Maquina("M001", "20201234", "descrição", null, "Woven", "Woven-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMarcaIsNotNull() {
        Maquina instance = new Maquina("M001", "20201234", "descrição", LocalDate.MIN, null, "Woven-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureModeloIsNotNull() {
        Maquina instance = new Maquina("M001", "20201234", "descrição", LocalDate.MIN, "Woven", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCodigoInternoIsNotEmpty() {
        Maquina instance = new Maquina("", "20201234", "descrição", LocalDate.MIN, "Woven", "Woven-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNumeroSerieIsNotEmpty() {
        Maquina instance = new Maquina("M001", "", "descrição", LocalDate.MIN, "Woven", "Woven-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescricaoIsNotEmpy() {
        Maquina instance = new Maquina("M001", "20201234", "", LocalDate.MIN, "Woven", "Woven-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMarcaIsNotEmpty() {
        Maquina instance = new Maquina("M001", "20201234", "descrição", LocalDate.MIN, "", "Woven-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureModeloIsNotEmpty() {
        Maquina instance = new Maquina("M001", "20201234", "descrição", LocalDate.MIN, "Woven", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMaquinaDontHaveFutureDataInstalacao() {
        LocalDate now = LocalDate.now();
        int ano = now.getYear() + 1;
        Maquina instance = new Maquina("M001", "20201234", "descrição", LocalDate.of(ano, now.getMonthValue(), now.getDayOfMonth()), "Woven", "Woven-1");
    }

}
