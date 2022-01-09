package eapli.base.smm;

import eapli.base.smm.base.Application;

public class SistemaMonitorizacaoMaquinas {

    protected static final String SEPARATOR_HR = "===========================================";
    protected static final String GOODBYE = "O sistema está a encerrar..";
    protected static final String HELLO = "O sistema está a iniciar..";
    private static final String RESET_ARGUMENT = Application.settings().getSMMResetArgument();

    public static void main(String[] args) {
        printHeader();
        monitorizarMaquinas(args);
        printFooter();
    }

    private static void monitorizarMaquinas(String[] args) {
        try {
            if(args[0].equals(RESET_ARGUMENT)) {
                PedidoReinicializacaoMaquina reinicializacao = new PedidoReinicializacaoMaquina();
                reinicializacao.reiniciarMaquina(args);
            } else {
                MonitorizacaoMaquinas monitorizacao = new MonitorizacaoMaquinas();
                monitorizacao.monitorizarMaquinas(args);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("A porta foi mal inserida!");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("Falta indicar uma porta e pelo menos 1 endereço de rede!");
        }
    }

    protected static void printHeader() {
        System.out.println(SEPARATOR_HR);
        System.out.println(Application.APP_TITLE);
        System.out.println(SEPARATOR_HR);
        System.out.println();
        System.out.println(HELLO);
    }

    protected static void printFooter() {
        System.out.println(GOODBYE);
        System.out.println();
        System.out.println(SEPARATOR_HR);
        System.out.println(Application.APP_TITLE);
        System.out.println(SEPARATOR_HR);
    }
}
