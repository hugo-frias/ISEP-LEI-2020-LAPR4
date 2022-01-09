package eapli.base.producao.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.LinhaProducao;
import eapli.base.producao.repositories.LinhaProducaoRepository;

public class RegistarLinhaProducaoController {

    private final LinhaProducaoRepository linhaProducaoRepository = PersistenceContext.repositories().linhasProducao();


    public LinhaProducao registarLinhaProducao (String idLinhaProducao) {

        try {
            final LinhaProducao linhaProducao = new LinhaProducao(idLinhaProducao);
            return this.linhaProducaoRepository.save(linhaProducao);
        } catch(Exception e) {
            System.out.println("Você tentou introduzir uma linha de produção que já se encontra no sistema e/ou dados inválidos.");
            return null;
        }

    }

}
