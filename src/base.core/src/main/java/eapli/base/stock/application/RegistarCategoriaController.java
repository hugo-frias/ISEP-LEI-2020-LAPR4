package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.repositories.CategoriaRepository;

public class RegistarCategoriaController {

    private final CategoriaRepository catRepo = PersistenceContext.repositories().categorias();


    public RegistarCategoriaController() {
    }

    public void register(String codigo, String descricao) throws Exception {
        Categoria cat = new Categoria(codigo, descricao);
        Categoria catFinal = catRepo.save(cat);
    }

}
