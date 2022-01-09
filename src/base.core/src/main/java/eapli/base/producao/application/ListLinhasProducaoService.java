package eapli.base.producao.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.LinhaProducao;
import eapli.base.producao.repositories.LinhaProducaoRepository;

public class ListLinhasProducaoService {
    
    private final LinhaProducaoRepository linhaProducaoRepository = PersistenceContext.repositories().linhasProducao();
    
    public Iterable<LinhaProducao> obterListaLinhasProducao() {

        return this.linhaProducaoRepository.findAll();
    }
}
