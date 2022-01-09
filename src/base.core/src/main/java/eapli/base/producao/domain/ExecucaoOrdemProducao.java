package eapli.base.producao.domain;

import eapli.base.stock.domain.Deposito;
import eapli.base.stock.domain.Lote;
import eapli.base.stock.domain.MateriaPrima;
import eapli.base.stock.domain.MovimentoStock;
import eapli.base.stock.domain.Produto;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "ExecucaoOrdemProducao")
@Entity
public class ExecucaoOrdemProducao implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long idExecucaoOrdemProducao;
    @Column(nullable = false)
    private OrdemProducaoStatus status;
    @Column(nullable = false)
    private double quantidadeProduzida;
    @Column(nullable = false)
    private double quantidadePretendida;

    @OneToOne(cascade = CascadeType.ALL)
    private TempoExecucao tempoExecucao;

    @ElementCollection(targetClass = TempoExecucao.class)
    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "Maquina")
    @Column(name = "Tempo")
    private Map<Maquina, TempoExecucao> listaMaquinasTempo;

    @ElementCollection()
    @MapKeyColumn(name = "MateriaPrimaConsumida")
    @Column(name = "Quantidade")
    private Map<MateriaPrima, Integer> listaConsumosMateriaPrima;

    @ElementCollection()
    @MapKeyColumn(name = "ProdutoConsumido")
    @Column(name = "Quantidade")
    private Map<Produto, Integer> listaConsumosProduto;

    @ElementCollection
    @MapKeyColumn(name = "DesviosMateriaPrima")
    @Column(name = "Quantidade")
    private Map<MateriaPrima, Integer> listaDesviosMateriaPrima;

    @ElementCollection
    @MapKeyColumn(name = "DesviosProduto")
    @Column(name = "Quantidade")
    private Map<Produto, Integer> listaDesviosProdutos;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Lote> listaLotes;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Deposito> listaDepositos;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<MovimentoStock> listaMovimentosStock;

    @OneToOne
    private LinhaProducao linhaProducao;

    public ExecucaoOrdemProducao(double quantidadePretendida) {
        Preconditions.ensure(quantidadePretendida > 0);

        this.status = OrdemProducaoStatus.PENDENTE;
        this.quantidadeProduzida = 0;
        this.quantidadePretendida = quantidadePretendida;
        this.linhaProducao = null;
        this.tempoExecucao = null;
        //this.desperdicioList = new ArrayList<>();
        //this.estornoList = new ArrayList<>();
    }

    protected ExecucaoOrdemProducao() {
        // for ORM
    }

    public Long getIdExecucaoOrdemProducao() {
        return idExecucaoOrdemProducao;
    }

    public OrdemProducaoStatus getStatus() {
        return status;
    }

    public void addQuantidadeProduzida(int quantidadeProduzida) {
        Preconditions.ensure(quantidadeProduzida > 0);
        this.quantidadeProduzida += quantidadeProduzida;
    }

    @XmlElement
    public double getQuantidadeProduzida() {
        return quantidadeProduzida;
    }

    @XmlElement
    public double getQuantidadePretendida() {
        return quantidadePretendida;
    }

    public Map<Maquina, TempoExecucao> getListaMaquinasTempo() {
        return listaMaquinasTempo;
    }

    public Map<MateriaPrima, Integer> getListaConsumosMateriaPrima() {
        return listaConsumosMateriaPrima;
    }

    public Map<Produto, Integer> getListaConsumosProduto() {
        return listaConsumosProduto;
    }

    public Map<MateriaPrima, Integer> getListaDesviosMateriaPrima() {
        return listaDesviosMateriaPrima;
    }

    public Map<Produto, Integer> getListaDesviosProdutos() {
        return listaDesviosProdutos;
    }

    public List<Lote> getListaLotes() {
        return listaLotes;
    }

    public List<Deposito> getListaDepositos() {
        return listaDepositos;
    }

    public TempoExecucao getTempoExecucao() {
        return tempoExecucao;
    }

    public List<MovimentoStock> getListaMovimentosStock() {
        return listaMovimentosStock;
    }

    @XmlElement
    public LinhaProducao getLinhaProducao() {
        return linhaProducao;
    }

    public void setLinhaProducao(LinhaProducao linhaProducao) {
        this.linhaProducao = linhaProducao;
    }

    public void setTempoExecucao(TempoExecucao tempoExecucao) {
        this.tempoExecucao = tempoExecucao;
    }

    public void adicionarQuantidadeProduzida(double quantidadeProduzida) {
        this.quantidadeProduzida = this.quantidadeProduzida + quantidadeProduzida;
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public Long identity() {
        return idExecucaoOrdemProducao;
    }

    public void adicionarMaquinaComTempo(Maquina m, TempoExecucao t) {
        listaMaquinasTempo.put(m, t);
    }

    public void adicionarConsumoMateriaPrima(MateriaPrima i, int quantidade) {
        /*int aux = quantidade;
        if(listaConsumosMateriaPrima.containsKey(i)) {
            quantidade = listaConsumosMateriaPrima.get(i) + aux;
        }*/
        listaConsumosMateriaPrima.put(i, quantidade);
    }

    public void adicionarConsumoProduto(Produto i, int quantidade) {
        int aux = quantidade;
        if(listaConsumosProduto.containsKey(i)) {
            quantidade = listaConsumosProduto.get(i) + aux;
        }
        listaConsumosProduto.put(i, quantidade);
    }

    public void adicionarDesvioMateriaPrima(MateriaPrima i, int quantidade) {
        int aux = quantidade;
        if(listaDesviosMateriaPrima.containsKey(i)) {
            quantidade = listaDesviosMateriaPrima.get(i) + aux;
        }
        listaDesviosMateriaPrima.put(i, quantidade);
    }

    public void adicionarDesvioProduto(Produto i, int quantidade) {
        int aux = quantidade;
        if(listaDesviosProdutos.containsKey(i)) {
            quantidade = listaDesviosProdutos.get(i) + aux;
        }
        listaDesviosProdutos.put(i, quantidade);
    }

    public void adicionarLote(Lote l) {
        listaLotes.add(l);
    }

    public void adicionarDeposito(Deposito d) {
        listaDepositos.add(d);
    }

    public void adicionarMovimentoStock(MovimentoStock m) {
        listaMovimentosStock.add(m);
    }

    public void adicionarTempoExecucao(TempoExecucao tempo) {
        this.tempoExecucao = tempo;
    }

    public void concluida() {
        this.status = OrdemProducaoStatus.CONCLUIDA;
    }

    public void paradaTemporariamente() {
        this.status = OrdemProducaoStatus.EXECUCAO_PARADA_TEMPORARIAMENTE;
    }

    public void emExecucao() {
        this.status = OrdemProducaoStatus.EM_EXECUCAO;
    }

    public void suspensa() {
        this.status = OrdemProducaoStatus.SUSPENSA;
    }

    public Map<Timestamp, Timestamp> obterListaParagens() {
        int aux = 0;
        Map<Timestamp, Timestamp> listaParagensReal = null;
        Map<Timestamp, Timestamp> listaParagens = new HashMap<>();
        while (true) {
            for (TempoExecucao tempo : listaMaquinasTempo.values()) {
                for (Timestamp t : tempo.getListaParagens().keySet()) {
                    if (listaParagens.containsKey(t)) {
                        if (listaParagens.get(t).after(tempo.getListaParagens().get(t))) {
                            listaParagens.put(t, listaParagens.get(t));
                            break;
                        }
                    }
                    if (aux == 0) {
                        listaParagens.put(t, tempo.getListaParagens().get(t));
                    }
                }
            }
            aux++;
            if (aux == 2) {
                break;
            }
            Map<Timestamp, Timestamp> listaAux = new HashMap<>();
            for (Timestamp t : listaParagens.keySet()) {
                listaAux.put(listaParagens.get(t), t);
            }
            listaParagensReal = new HashMap<>(listaParagens);
            listaParagens = listaAux;
        }
        return listaParagensReal;
    }
}
