package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.Encomenda;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.repositories.EncomendaRepository;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import eapli.base.stock.domain.Categoria;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.base.stock.repositories.CategoriaRepository;
import eapli.base.stock.repositories.ProdutoRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportarOrdemProducaoCSVController {

    private final OrdemProducaoRepository ordRep = PersistenceContext.repositories().ordensProducao();
    private final ProdutoRepository prodRep = PersistenceContext.repositories().produtos();
    private final EncomendaRepository encRep = PersistenceContext.repositories().encomenda();

    public ImportarOrdemProducaoCSVController() {
    }

    public void importar(String path) {
        List<OrdemProducao> listaOrdemProducao = getListaOrdemProducaoByCSV(path);
        for(OrdemProducao o : listaOrdemProducao){
            ordRep.save(o);
        }

    }

    public List<OrdemProducao> getListaOrdemProducaoByCSV(String path){
        int count = 0;
        String line;
        String cvsSplitBy = ";";
        List<OrdemProducao> listaOrdemProducao = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                if(count>0){
                    String[] ordemAux = line.split(cvsSplitBy);
                    if(prodRep.verifyProdutoExiste(ordemAux[3])) {
                        Date dataEmissao = new Date(Long.parseLong(ordemAux[1]));
                        Date dataPrev = new Date(Long.parseLong(ordemAux[2]));
                        Produto prod = prodRep.findProdutoByCodFabrico(ordemAux[3]);
                        Encomenda encomenda = new Encomenda(ordemAux[6]);
                        encRep.save(encomenda);
                        OrdemProducao ordemProducao = new OrdemProducao(ordemAux[0], dataEmissao.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), dataPrev.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                prod, new UnidadeMedida(Double.parseDouble(ordemAux[4]), ordemAux[5]), encomenda,  Double.parseDouble(ordemAux[4]));
                        listaOrdemProducao.add(ordemProducao);
                    }
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaOrdemProducao;
    }
}
