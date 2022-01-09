/**
 *
 */
package eapli.base.infrastructure.persistence;

import eapli.base.comunicacao.repositories.MensagemRepository;
import eapli.base.comunicacao.repositories.NotificacaoErroRepository;
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

/**
 * @author Paulo Gandra Sousa
 *
 */
public interface RepositoryFactory {

	/**
	 * factory method to create a transactional context to use in the repositories
	 *
	 * @return
	 */
	TransactionalContext newTransactionalContext();

	/**
	 *
	 * @param autoTx the transactional context to enrol
	 * @return
	 */
	UserRepository users(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	UserRepository users();
	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	CategoriaRepository categorias();

    ProdutoRepository produtos();
    
    MateriaPrimaRepository materiasPrimas();
    
    MaquinaRepository maquinas();

    DepositoRepository deposito();
    
    LinhaProducaoRepository linhasProducao();

    OrdemProducaoRepository ordensProducao();
    
    MensagemRepository mensagens();
    
    EncomendaRepository encomenda();
    
    EstadoMaquinaRepository estadosMaquina();
    
    NotificacaoErroRepository notificacoesErro();

    LoteRepository lotes();

    MovimentoStockRepository movimentosStock();

    EstornoRepository estornos();
}
