package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.base.stock.repositories.CategoriaRepository;
import eapli.base.stock.repositories.ProdutoRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImportarProdutosCSVController {

    private final ProdutoRepository prodRep = PersistenceContext.repositories().produtos();
    private final CategoriaRepository catRepo = PersistenceContext.repositories().categorias();

    public ImportarProdutosCSVController() {
    }

    public void importar(String path) {
        List<Produto> listaProdutos = getListaProdutosByCSV(path);
        for(Produto p : listaProdutos){
            prodRep.save(p);
        }

    }

    public List<Produto> getListaProdutosByCSV(String path){
        int count = 0;
        String line = "";
        String cvsSplitBy = ";";
        List<Produto> listaProdutos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = br.readLine()) != null) {

                if(count>0){
                    String[] produtoAux = line.split(cvsSplitBy);
                    Categoria catAux = new Categoria(produtoAux[5],"");
                    Produto produto = new Produto(produtoAux[0],produtoAux[1],produtoAux[2],produtoAux[3], catAux, new UnidadeMedida(1, produtoAux[4]));
                    listaProdutos.add(produto);
                }
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProdutos;
    }
}
