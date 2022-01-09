package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.repositories.CategoriaRepository;


public class ListCategoriasService {

 private final CategoriaRepository categoriaRepository = PersistenceContext.repositories().categorias();

    public Iterable<Categoria> todasCategorias() {
        return this.categoriaRepository.findAll();
    }


}
