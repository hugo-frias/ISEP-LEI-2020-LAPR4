/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.spm;

import eapli.base.producao.domain.LinhaProducao;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static Timestamp obterDataHoraInicio(String[] args) {
        String inicio = args[0];
        int anoInicio = Integer.parseInt(inicio.substring(0, 4))-1900;
        int mesInicio = Integer.parseInt(inicio.substring(4, 6));
        int diaInicio = Integer.parseInt(inicio.substring(6, 8));
        int horaInicio = Integer.parseInt(inicio.substring(8, 10));
        int minutoInicio = Integer.parseInt(inicio.substring(10, 12));
        int segundoInicio = Integer.parseInt(inicio.substring(12, 14));

        Timestamp inicioTime = new Timestamp(anoInicio, mesInicio-1, diaInicio, horaInicio, minutoInicio, segundoInicio, 0);
        
        return inicioTime;
    }
    
    public static Timestamp obterDataHoraFim(String[] args) {
        String fim = args[1];
        int anoFim = Integer.parseInt(fim.substring(0, 4))-1900;
        int mesFim = Integer.parseInt(fim.substring(4, 6));
        int diaFim = Integer.parseInt(fim.substring(6, 8));
        int horaFim = Integer.parseInt(fim.substring(8, 10));
        int minutoFim = Integer.parseInt(fim.substring(10, 12));
        int segundoFim = Integer.parseInt(fim.substring(12, 14));

        Timestamp fimTime = new Timestamp(anoFim, mesFim-1, diaFim, horaFim, minutoFim, segundoFim, 0);
        
        return fimTime;
    }

    public static Set<LinhaProducao> obterLinhasProducao(String[] args, ArrayList<LinhaProducao> lista) {
        boolean check = false;
        Set<LinhaProducao> listaLinhas = new HashSet<>();
        Set<String> listaAux = new HashSet<>();
        int listaSize = args.length;
        for (int i = 2; i < listaSize; i++) {
            listaAux.add(args[i]);
        }
        if (listaAux.isEmpty()) {
            for (LinhaProducao linha : lista) {
                listaLinhas.add(linha);
            }
        } else {
            for(String id : listaAux) {
                for(LinhaProducao linha : lista) {
                    if(linha.getIdLinhaProducao().equals(id)) {
                        listaLinhas.add(linha);
                        check = true;
                    }
                }
                if(!check) {
                    throw new IllegalArgumentException("Pelo menos uma das linhas que foi inserida não existe no sistema!");
                }
                check = false;
            }
            
        }
        return listaLinhas;
    }

}
