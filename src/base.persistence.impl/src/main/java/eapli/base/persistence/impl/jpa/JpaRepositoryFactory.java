package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.comunicacao.repositories.MensagemRepository;
import eapli.base.comunicacao.repositories.NotificacaoErroRepository;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.producao.repositories.EncomendaRepository;
import eapli.base.producao.repositories.EstadoMaquinaRepository;
import eapli.base.producao.repositories.LinhaProducaoRepository;
import eapli.base.producao.repositories.MaquinaRepository;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import eapli.base.stock.repositories.CategoriaRepository;
import eapli.base.stock.repositories.DepositoRepository;
import eapli.base.stock.repositories.EstornoRepository;
import eapli.base.stock.repositories.LoteRepository;
import eapli.base.stock.repositories.MateriaPrimaRepository;
import eapli.base.stock.repositories.MovimentoStockRepository;
import eapli.base.stock.repositories.ProdutoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository users(final TransactionalContext autoTx) {
        return new JpaAutoTxUserRepository(autoTx);
    }

    @Override
    public UserRepository users() {
        return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public CategoriaRepository categorias() {
        return new JpaCategoriaRepository();
    }

    @Override
    public ProdutoRepository produtos() {
        return new JpaProdutoRepository();

    }

    @Override
    public MateriaPrimaRepository materiasPrimas() {
        return new JpaMateriaPrimaRepository();
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public MaquinaRepository maquinas() {
        return new JpaMaquinaRepository();
    }

    @Override
    public LinhaProducaoRepository linhasProducao() {
        return new JpaLinhaProducaoRepository();
    }

    @Override
    public OrdemProducaoRepository ordensProducao() {
        return new JpaOrdemProducaoRepository();
    }

    @Override
    public DepositoRepository deposito() {
        return new JpaDepositoRepository();
    }

    @Override
    public MensagemRepository mensagens() {
        return new JpaMensagemRepository();
    }

    public EncomendaRepository encomenda() {
        return new JpaEncomendaRepository();
    }

    @Override
    public EstadoMaquinaRepository estadosMaquina() {
        return new JpaEstadoMaquinaRepository();
    }

    @Override
    public NotificacaoErroRepository notificacoesErro() {
        return new JpaNotificacaoErroRepository();
    }

    @Override
    public LoteRepository lotes() {
        return new JpaLoteRepository();
    }

    @Override
    public MovimentoStockRepository movimentosStock() {
        return new JpaMovimentoStockRepository();
    }

    @Override
    public EstornoRepository estornos() {
        return new JpaEstornoRepository();
    }
}
