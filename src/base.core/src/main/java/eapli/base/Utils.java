package eapli.base;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    public static int lengthOfANum(double num) {
        int length = 0;
        return lengthOfANum(num, length);
    }

    public static int lengthOfANum(double num, int length) {
        if (num == 0) {
            return 1;
        }
        if (num > 1) {
            num = num / 10;
            length++;
            return lengthOfANum(num, length);
        }
        return length;
    }

    //método para obter um número com X casas decimais
    public static double round(double valor, int casasDecimais) {
        BigDecimal aux = BigDecimal.valueOf(valor);
        aux = aux.setScale(casasDecimais, RoundingMode.HALF_UP);
        return aux.doubleValue();
    }

    //método para verificar se uma string contém um número
    public static boolean containsNum(String s) {

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;

                }
            }
        }

        return false;
    }

    //método para verificar se uma string contém uma letra
    public static boolean containsLetter(String s) {

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isLetter(c)) {
                    return true;

                }
            }
        }

        return false;
    }

}
