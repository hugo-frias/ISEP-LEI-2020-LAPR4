package eapli.base.producao.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.LinhaProducao;
import eapli.base.producao.domain.Maquina;
import eapli.base.producao.repositories.LinhaProducaoRepository;
import eapli.base.producao.repositories.MaquinaRepository;
import eapli.framework.application.UseCaseController;
import java.time.LocalDate;

@UseCaseController
public class AdicionarMaquinaController {
    
    private final MaquinaRepository maquinaRepository = PersistenceContext.repositories().maquinas();
    private final LinhaProducaoRepository linhaProducaoRepository = PersistenceContext.repositories().linhasProducao();
    private final ListLinhasProducaoService svc = new ListLinhasProducaoService();
    
    public LinhaProducao registarMaquina(final LinhaProducao linha, final String codigoInterno, final String numeroSerie, final String descricao,
            LocalDate dataInstalacao, final String marca, final String modelo, int posicao) {

        final Maquina maquina = new Maquina(codigoInterno,numeroSerie,descricao,dataInstalacao,marca,modelo);
        
        maquinaRepository.save(maquina);
        
        return atualizarLinhaProducao(linha,maquina,posicao);
    }

    public Iterable<LinhaProducao> mostrarListaLinhasProducao() {
        return this.svc.obterListaLinhasProducao();
    }
    
    public LinhaProducao atualizarLinhaProducao(LinhaProducao linha, Maquina maquina, int posicao) {
        linha.adicionarMaquina(maquina, posicao);
        return this.linhaProducaoRepository.save(linha);
    }
}
