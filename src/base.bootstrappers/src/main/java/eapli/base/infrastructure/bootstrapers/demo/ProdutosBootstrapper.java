package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.ExecucaoOrdemProducao;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import eapli.base.stock.application.RegistarProdutoController;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.base.stock.repositories.CategoriaRepository;
import eapli.framework.actions.Action;

import java.time.LocalDate;

public class ProdutosBootstrapper implements Action {
    private final CategoriaRepository catRepo = PersistenceContext.repositories().categorias();
    private final OrdemProducaoRepository ord = PersistenceContext.repositories().ordensProducao();
    private final RegistarProdutoController rp = new RegistarProdutoController();
    @Override
    public boolean execute() {
        try {
            //final Categoria cat1 = catRepo.getCategoriaById("C01");
            //final Categoria cat2 = catRepo.getCategoriaById("C02");
            //final Categoria cat3 = catRepo.getCategoriaById("C03");

            Categoria cat1 = new Categoria("C04","Bricolage - pequenos");
            Categoria cat2 = new Categoria("C05","Decoraçao");
            Categoria cat3 = new Categoria("C06","Carros");

            catRepo.save(cat1);
            catRepo.save(cat2);
            catRepo.save(cat3);

            rp.registarProduto("PF01", "PC01", "Parafusos"
                    ,"Parafusos sextavados feitos de ferro",
                    cat2, "unidades");
            rp.registarProduto("PF02", "PC02", "tela estore"
                    ,"Tela para estore de rolo blackout",
                    cat3, "metros");
            rp.registarProduto("PF03", "PC03", "Pneu"
                    ,"Pneu grande para veiculos pesados",
                    cat1, "kgs");


            return true;



        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

