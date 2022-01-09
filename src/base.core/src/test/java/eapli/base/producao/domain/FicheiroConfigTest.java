/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.producao.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vera Pinto
 */
public class FicheiroConfigTest {
    
    public FicheiroConfigTest() {
    }
    
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

    @Test(expected = IllegalArgumentException.class)
    public void ensureNullDescricaoIsNotAllowed() {
        FicheiroConfig instance = new FicheiroConfig("XXXX",null);
    }
     @Test(expected = IllegalArgumentException.class)
    public void ensureNullNomeFichIsNotAllowed() {
        FicheiroConfig instance = new FicheiroConfig(null,"XXXX");
    }

    
}
