package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.Encomenda;
import eapli.base.producao.domain.ExecucaoOrdemProducao;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.repositories.EncomendaRepository;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.framework.actions.Action;

import java.time.LocalDate;

public class EncomendaBootstrapper implements Action {
    private final EncomendaRepository encomendaRepository = PersistenceContext.repositories().encomenda();

    public boolean execute() {
        try {
            //final Categoria cat1 = catRepo.getCategoriaById("C01");
            //final Categoria cat2 = catRepo.getCategoriaById("C02");
            //final Categoria cat3 = catRepo.getCategoriaById("C03");

            Categoria cat1 = new Categoria("C04","Bricolage - pequenos");
            Categoria cat2 = new Categoria("C05","Decoraçao");
            Categoria cat3 = new Categoria("C06","Carros");


            Produto prod1 = new Produto("PF01", "PC01", "Parafusos"
                    ,"Parafusos sextavados feitos de ferro",
                    cat2, new UnidadeMedida("unidades"));
            Produto prod2 = new Produto("PF02", "PC02", "tela estore"
                    ,"Tela para estore de rolo blackout",
                    cat3, new UnidadeMedida("metros"));
            Produto prod3 = new Produto("PF03", "PC03", "Pneu"
                    ,"Pneu grande para veiculos pesados",
                    cat1, new UnidadeMedida("kgs"));


            OrdemProducao ord = new OrdemProducao("OP40", LocalDate.of(2020, 2, 19), LocalDate.of(2020, 2, 22), prod1, new UnidadeMedida(("unidades")), new ExecucaoOrdemProducao(100));
            OrdemProducao ord2 = (new OrdemProducao("OP41", LocalDate.of(2021, 2, 19), LocalDate.of(2021, 2, 22), prod2, new UnidadeMedida(("metros")), new ExecucaoOrdemProducao(50)));
            Encomenda encomenda1 = new Encomenda("ENC20");
            Encomenda encomenda2 = new Encomenda("ENC21");
            encomenda1.addOrdemProducao(ord);
            encomenda2.addOrdemProducao(ord2);
            encomendaRepository.save(encomenda1);
            encomendaRepository.save(encomenda2);
            return true;



        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}
