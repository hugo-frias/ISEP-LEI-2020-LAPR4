/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.smm;

import org.junit.Test;

public class PedidoReinicializacaoMaquinaTest {

    @Test(expected = NumberFormatException.class)
    public void ensurePortaIsNumber() {
        String[] argumentos = new String[2];
        argumentos[0] = "string";
        argumentos[1] = "10.8.0.80";
        MonitorizacaoMaquinas instance = new MonitorizacaoMaquinas();
        instance.monitorizarMaquinas(argumentos);
    }

    @Test(expected = NumberFormatException.class)
    public void ensurePortaIsPresentWithNetworkAddress() {
        String[] argumentos = new String[1];
        argumentos[0] = "10.8.0.80";
        MonitorizacaoMaquinas instance = new MonitorizacaoMaquinas();
        instance.monitorizarMaquinas(argumentos);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAtLeastOneNetworkIsPresent() {
        String[] argumentos = new String[1];
        argumentos[0] = "9000";
        MonitorizacaoMaquinas instance = new MonitorizacaoMaquinas();
        instance.monitorizarMaquinas(argumentos);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void ensurePortaAndAtLeastOneNetworkArePresent() {
        String[] argumentos = new String[0];
        MonitorizacaoMaquinas instance = new MonitorizacaoMaquinas();
        instance.monitorizarMaquinas(argumentos);
    }

}
