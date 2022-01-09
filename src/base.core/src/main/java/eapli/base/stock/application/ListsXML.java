package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.producao.domain.Encomenda;
import eapli.base.producao.domain.LinhaProducao;
import eapli.base.producao.domain.Maquina;
import eapli.base.producao.domain.OrdemProducao;
import eapli.base.producao.repositories.EncomendaRepository;
import eapli.base.producao.repositories.LinhaProducaoRepository;
import eapli.base.producao.repositories.MaquinaRepository;
import eapli.base.producao.repositories.OrdemProducaoRepository;
import eapli.base.stock.domain.*;
import eapli.base.stock.repositories.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement (name = "Instancias")
public class ListsXML {
    private final ProdutoRepository produtoRepository = PersistenceContext.repositories().produtos();
    private final CategoriaRepository categoriaRepository = PersistenceContext.repositories().categorias();
    private final DepositoRepository depositoRepository = PersistenceContext.repositories().deposito();
    private final LinhaProducaoRepository linhaProducaoRepository = PersistenceContext.repositories().linhasProducao();
    private final MaquinaRepository maquinaRepository = PersistenceContext.repositories().maquinas();
    private final MateriaPrimaRepository materiaPrimaRepository = PersistenceContext.repositories().materiasPrimas();
    private final OrdemProducaoRepository ordemProducaoRepository = PersistenceContext.repositories().ordensProducao();
    private final EncomendaRepository encomendaRepository = PersistenceContext.repositories().encomenda();





    private List<Produto> allProdutos;
    private List<Categoria> allCategorias;
    private List<Deposito> allDepositos;
    private List<LinhaProducao> allLinhasProducao;
    private List<Maquina> allMaquinas;
    private List<MateriaPrima> allMateriasPrimas;
    private List<OrdemProducao> allOrdensProducao;
    private List<Encomenda> allEncomendas;

    public ListsXML() {

        this.allProdutos = new ArrayList<>();
        this.allCategorias = new ArrayList<>();
        this.allDepositos = new ArrayList<>();
        this.allLinhasProducao = new ArrayList<>();
        this.allMaquinas = new ArrayList<>();
        this.allMateriasPrimas= new ArrayList<>();
        this.allOrdensProducao = new ArrayList<>();
        this.allEncomendas = new ArrayList<>();/*
        this.allProdutos = (List) produtoRepository.findAll();
        this.allCategorias  = (List) categoriaRepository.findAll();
        this.allDepositos  = (List) depositoRepository.findAll();
        this.allLinhasProducao = (List) linhaProducaoRepository.findAll();
        this.allMaquinas = (List) maquinaRepository.findAll();
        this.allMateriasPrimas= (List) materiaPrimaRepository.findAll();
        this.allOrdensProducao = (List) ordemProducaoRepository.findAll();
        this.allEncomendas = (List) encomendaRepository.findAll();*/
    }

    public void generateProdutos(){
        allProdutos = (List) produtoRepository.findAll();
    }

    public void generateMateriasPrimas() {
        allMateriasPrimas = (List) materiaPrimaRepository.findAll();
    }

    public void generateDepositos() {
        allDepositos = (List) depositoRepository.findAll();
    }

    public void generateCategorias() {
        allCategorias = (List) categoriaRepository.findAll();
    }

    public void generateOrdensProducao(String dataIn, String dataFim) {
        List<OrdemProducao> listaAux = (List) ordemProducaoRepository.findAll();

        String[] dataInAux = dataIn.split("-");
        String[] dataFimAux = dataFim.split("-");
        Date dataInicio = new Date(Integer.parseInt(dataInAux[0])-1900,Integer.parseInt(dataInAux[1])-1,Integer.parseInt(dataInAux[2]));
        Date dataFinal = new Date(Integer.parseInt(dataFimAux[0])-1900,Integer.parseInt(dataFimAux[1])-1,Integer.parseInt(dataFimAux[2]));
        for(OrdemProducao o : listaAux){
            if(o.getDataEmissao().isAfter(dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) && o.getDataEmissao().isBefore(dataFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
                allOrdensProducao.add(o);
            }
        }

    }

    public void generateMaquinas() {
        allMaquinas = (List) maquinaRepository.findAll();
    }

    public void generateLinhasProducao() {
        allLinhasProducao = (List) linhaProducaoRepository.findAll();
    }

    public void generateEncomendas() {
        allEncomendas = (List) encomendaRepository.findAll();
    }


    public void criarInstanciaListXML(String encomenda, String linha, String maquina, String ordemProduc,
                                           String dataIn, String dataFim, String categoria, String deposito,  String materiaPrima, String produto) {

        if(encomenda.equalsIgnoreCase("s")){
            generateEncomendas();
        }
        if(linha.equalsIgnoreCase("s")){
            generateLinhasProducao();
        }
        if(maquina.equalsIgnoreCase("s")){
            generateMaquinas();
        }
        if(ordemProduc.equalsIgnoreCase("s")){
            generateOrdensProducao(dataIn, dataFim);
        }
        if(categoria.equalsIgnoreCase("s")){
            generateCategorias();
        }
        if(deposito.equalsIgnoreCase("s")){
            generateDepositos();
        }
        if(materiaPrima.equalsIgnoreCase("s")){
            generateMateriasPrimas();
        }
        if(produto.equalsIgnoreCase("s")){
            generateProdutos();
        }



    }


    @XmlElement (name = "Produto")
    public List<Produto> getAllProdutos(){
        return allProdutos;
    }

    @XmlElement (name = "Categoria")
    public List<Categoria> getAllCategorias() {
        return allCategorias;
    }

    @XmlElement (name = "Deposito")
    public List<Deposito> getAllDepositos() {
        return allDepositos;
    }

    @XmlElement (name = "LinhaProdução")
    public List<LinhaProducao> getAllLinhasProducao() {
        return allLinhasProducao;
    }

    @XmlElement (name = "Maquina")
    public List<Maquina> getAllMaquinas() {
        return allMaquinas;
    }

    @XmlElement (name = "MateriaPrima")
    public List<MateriaPrima> getAllMateriasPrimas() {
        return allMateriasPrimas;
    }
    @XmlElement (name = "OrdemProducao")
    public List<OrdemProducao> getAllOrdensProducao() {
        return allOrdensProducao;
    }
    @XmlElement (name = "Encomenda")
    public List<Encomenda> getAllEncomendas() {
        return allEncomendas;
    }
}
