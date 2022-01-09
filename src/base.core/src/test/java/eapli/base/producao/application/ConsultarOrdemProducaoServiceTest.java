/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.application;

import eapli.base.producao.domain.Encomenda;
import eapli.base.producao.domain.ExecucaoOrdemProducao;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.framework.domain.repositories.DomainRepository;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author joaoa
 */
public class ConsultarOrdemProducaoServiceTest{

        
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//    /**
//     * Test of apresentarLista method, of class ConsultarOrdemProducaoService.
//     */
//    @Test
//    public void testApresentarLista() throws Exception {
//        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(actualOutput));
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        UnidadeMedida um = new UnidadeMedida(20, "kg");
//        Categoria cat2 = new Categoria("md007", "Carvalho");
//        Produto mesaDeCarvalho = new Produto("PF09", "PC09", "Mesa", "Mesa de carvalho de D.Dinis", cat2, um);
//        Encomenda e = new Encomenda("idecomendateste1");
//        ExecucaoOrdemProducao eo = new ExecucaoOrdemProducao(20);
//        OrdemProducao ordemProducao1 = new OrdemProducao("Producao003", LocalDate.MIN, LocalDate.MIN, mesaDeCarvalho, um, e, eo);
//        List<OrdemProducao> listaProducao = new ArrayList<>();
//        listaProducao.add(ordemProducao1);
//         ConsultarOrdemProducaoService instance = new ConsultarOrdemProducaoService();
//         
//        instance.apresentarLista(listaProducao);
//         String expectedOutput = os.toString();
//        assertEquals(expectedOutput, actualOutput);
//    }

}
