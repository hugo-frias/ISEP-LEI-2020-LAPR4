package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.base.stock.repositories.ProdutoRepository;
import eapli.framework.application.UseCaseController;

@UseCaseController
public class RegistarProdutoController {

    private final ListCategoriasService svcCategorias = new ListCategoriasService();
    private final ProdutoRepository produtoRepository = PersistenceContext.repositories().produtos();

    public Produto registarProduto(final String codFabrico, final String codComercial, final String descBreve, final String descCompleta, final Categoria categoria, final String tipoUnidade) {

        try {
            final Produto novoProduto = new Produto(codFabrico, codComercial, descBreve, descCompleta, categoria, new UnidadeMedida(tipoUnidade));
            return this.produtoRepository.save(novoProduto);
        } catch (Exception e) {
            System.out.println("Você tentou introduzir um produto que já se encontra no sistema e/ou dados inválidos.");
            return null;
        }
    }

    public Iterable<Categoria> categorias() {
        return this.svcCategorias.todasCategorias();
    }


}
