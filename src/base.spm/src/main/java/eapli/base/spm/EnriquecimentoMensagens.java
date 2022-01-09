package eapli.base.spm;

import eapli.base.comunicacao.domain.Mensagem;
import eapli.base.producao.domain.LinhaProducao;
import java.util.ArrayList;

public class EnriquecimentoMensagens {

    public String enriquecerMensagens(ArrayList<Mensagem> listaMsg, LinhaProducao linhaProducao) {
        String idLinha = linhaProducao.getIdLinhaProducao();
        enriquecerMensagensComLinhaProducao(listaMsg, idLinha);
        return enriquecerMensagensComOrdens(listaMsg);
    }

    private void enriquecerMensagensComLinhaProducao(ArrayList<Mensagem> listaMsg, String idLinha) {
        for (Mensagem m : listaMsg) {
            if (m.getIdLinhaProducao() == null) {
                m.setIdLinhaProducao(idLinha);
            }
        }
    }

    private String enriquecerMensagensComOrdens(ArrayList<Mensagem> lista) {
        boolean found = false;
        String idOrdem = null;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdOrdemProducao() != null) {
                found = true;
                idOrdem = lista.get(i).getIdOrdemProducao();
            }
        }
        if (found) {
            for (Mensagem m : lista) {
                if (m.getIdOrdemProducao() == null) {
                    m.setIdOrdemProducao(idOrdem);
                }
            }
        } else {
            throw new IllegalArgumentException("Não foi possível atribuir ordens de produção ao bloco!");
        }
        return idOrdem;
    }

}
