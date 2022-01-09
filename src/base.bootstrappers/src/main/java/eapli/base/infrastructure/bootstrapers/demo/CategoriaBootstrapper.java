package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.application.RegistarCategoriaController;
import eapli.base.stock.repositories.CategoriaRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

public class CategoriaBootstrapper implements Action {

    private final RegistarCategoriaController controller = new RegistarCategoriaController();

    private final String codigoUm = "C01";
    private final String codigoDois = "C02";
    private final String codigoTres = "C03";

    private final String descricaoUm = "Gerais";
    private final String descricaoDois = "Cortiças";
    private final String descricaoTres = "Madeiras";

    @Override
    public boolean execute() {
        try {
            return register(codigoUm,descricaoUm) && register(codigoDois,descricaoDois) && register(codigoTres,descricaoTres);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean register(final String codigo, final String descricao) throws Exception {
        try {

            controller.register(codigo, descricao);

            return true;
        } catch (final IntegrityViolationException | ConcurrencyException
                | TransactionSystemException e) {
            System.out.printf("Erro a inicializar categoria: %s \t %s \n", codigo, descricao);
            return false;
        }
    }


}
