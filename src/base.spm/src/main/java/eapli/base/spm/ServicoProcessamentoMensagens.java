/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.spm;

import eapli.framework.util.Console;
import java.util.Scanner;

public class ServicoProcessamentoMensagens {

    protected static final String SEPARATOR_HR = "===========================================";
    protected static final String GOODBYE = "O serviço está a encerrar..";
    protected static final String HELLO = "O serviço está a iniciar..";
    protected static final String APP_TITLE = "Serviço de Processamento de Mensagens (SPM)";

    static ProcessamentoMensagens p = new ProcessamentoMensagens();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Boolean b = false, go = true;
        String exit = "exit", as;

        printHeader();
        String answer;
        answer = Console.readLine("Pretende executar processamento recorrente (responda S/N)?");
        if (answer.equals("N")) {
            processarMensagens(args);
            System.exit(0);
        }

        do {
            System.out.println("\nPretende Ativar o processamento de menssagens disponíveis? ( 1 Ativar|0 Desativar| z Sair )");
            as = sc.next();
            if (go == true) {
                if (as.equalsIgnoreCase("1")) {

                    processarMensagensRecorrente(args);
                    b = true;
                    go = false;

                    System.out.println("Serviço ativado");
                }
            } else if (go == false && as.equalsIgnoreCase("1")) {
                System.out.println("\nO serviço já se encontra em processo");
            }

            if (as.equalsIgnoreCase("0")) {
                p.desativarSPMrecorrente();
                b = true;
                go = true;
                System.out.println("Servico desativado");
            } else if (as.equalsIgnoreCase("z")) {
                b = true;
                exit = "end";
                System.out.println("Serviço terminado");
            } else if (as.equalsIgnoreCase("1") && go == false) {

            } else {
                System.out.println("\nPorfavor intruduça uma das seguintes chaves entre(...)");
            }

        } while (b == false || exit.equalsIgnoreCase("exit"));

        printFooter();
    }

    private static void processarMensagens(String[] args) {
        try {
            ProcessamentoMensagens processamento = new ProcessamentoMensagens();
            processamento.processarMensagens(args);
        } catch (NumberFormatException nfe) {
            System.out.println("O intervalo de tempo não é numérico!");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("Falta indicar um intervalo de tempo!");
        } catch (StringIndexOutOfBoundsException sioobe) {
            System.out.println("O intervalo de tempo é inválido!");
        }
    }

    private static void processarMensagensRecorrente(String[] args) {
        try {
            ProcessamentoMensagens processamento = new ProcessamentoMensagens();
            processamento.processarMensagensRecorrente(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void printHeader() {
        System.out.println(SEPARATOR_HR);
        System.out.println(APP_TITLE);
        System.out.println(SEPARATOR_HR);
        System.out.println();
        System.out.println(HELLO);
    }

    protected static void printFooter() {
        System.out.println(GOODBYE);
        System.out.println();
        System.out.println(SEPARATOR_HR);
        System.out.println(APP_TITLE);
        System.out.println(SEPARATOR_HR);
    }

}
