package eapli.base.producao.domain;

import javax.xml.bind.annotation.XmlElement;
import eapli.base.stock.domain.Produto;
import eapli.base.stock.domain.UnidadeMedida;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlRootElement(name = "OrdemProducao")
@Entity
public class OrdemProducao implements AggregateRoot<String> {

    @Id
    @Column(unique = true, nullable = false)
    private String idOrdemProducao;

    @Column(nullable = false)
    private LocalDate dataEmissao;

    @Column(nullable = false)
    private LocalDate dataPrevExecucao;

    @OneToOne
    private Produto produto;

    @Column(nullable = false)
    private UnidadeMedida unidadeMedida;

    @OneToOne(cascade = CascadeType.ALL)
    private ExecucaoOrdemProducao execucaoOrdemProducao;

    @ManyToOne
    private Encomenda encomenda;

    public OrdemProducao(String idOrdemProducao, LocalDate dataEmissao, LocalDate dataPrevExecucao, Produto produto, UnidadeMedida unidadeMedida, Encomenda encomenda, ExecucaoOrdemProducao execucaoOrdemProducao) {
        Preconditions.nonEmpty(idOrdemProducao);
        Preconditions.nonNull(produto);
        Preconditions.nonNull(encomenda);
        Preconditions.nonNull(dataEmissao);
        Preconditions.nonNull(dataPrevExecucao);
        Preconditions.nonNull(unidadeMedida);
        Preconditions.ensure(0 <= dataEmissao.compareTo(dataPrevExecucao));
        this.idOrdemProducao = idOrdemProducao;
        this.dataEmissao = dataEmissao;
        this.dataPrevExecucao = dataPrevExecucao;
        this.produto = produto;
        this.unidadeMedida = unidadeMedida;
        this.encomenda = encomenda;
        this.execucaoOrdemProducao = execucaoOrdemProducao;
    }

    public OrdemProducao(String idOrdemProducao, LocalDate dataEmissao, LocalDate dataPrevExecucao, Produto produto, UnidadeMedida unidadeMedida, ExecucaoOrdemProducao execucaoOrdemProducao) {
        Preconditions.nonEmpty(idOrdemProducao);
        Preconditions.nonNull(produto);
        Preconditions.nonNull(dataEmissao);
        Preconditions.nonNull(dataPrevExecucao);
        Preconditions.nonNull(unidadeMedida);
        Preconditions.nonNull(execucaoOrdemProducao);
        Preconditions.ensure(dataPrevExecucao.compareTo(dataEmissao) >= 0);
        this.idOrdemProducao = idOrdemProducao;
        this.dataEmissao = dataEmissao;
        this.dataPrevExecucao = dataPrevExecucao;
        this.produto = produto;
        this.unidadeMedida = unidadeMedida;
        this.execucaoOrdemProducao = execucaoOrdemProducao;
        this.encomenda = null;
    }

    public OrdemProducao(String idOrdemProducao, LocalDate dataEmissao, LocalDate dataPrevExecucao, Produto produto, UnidadeMedida unidadeMedida, Encomenda encomenda,double quantidade) {
        Preconditions.nonEmpty(idOrdemProducao);
        Preconditions.nonNull(dataEmissao);
        Preconditions.nonNull(dataPrevExecucao);
        Preconditions.nonNull(unidadeMedida);
        Preconditions.ensure(0 <= dataEmissao.compareTo(dataPrevExecucao));
        this.idOrdemProducao = idOrdemProducao;
        this.dataEmissao = dataEmissao;
        this.dataPrevExecucao = dataPrevExecucao;
        this.produto = produto;
        this.unidadeMedida = unidadeMedida;
        this.encomenda = encomenda;
        this.execucaoOrdemProducao = new ExecucaoOrdemProducao(quantidade);
    }

    protected OrdemProducao() {
        // for ORM
    }

    @XmlElement
    public String getIdOrdemProducao() {
        return idOrdemProducao;
    }


    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    @XmlElement
    public String getDatadeEmissao(){
        return dataEmissao.toString();
    }

    public LocalDate getDataPrevExecucao() {
        return dataPrevExecucao;
    }

    @XmlElement
    public String getDataPrevistaExecucao(){
        return dataPrevExecucao.toString();
    }
    @XmlElement
    public Produto getProduto() {
        return produto;
    }

    @XmlElement
    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    @XmlElement
    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    @XmlElement
    public ExecucaoOrdemProducao getExecucaoOrdemProducao() {
        return execucaoOrdemProducao;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrdemProducao that = (OrdemProducao) o;
        return Objects.equals(idOrdemProducao, that.idOrdemProducao)
                && Objects.equals(dataEmissao, that.dataEmissao)
                && Objects.equals(dataPrevExecucao, that.dataPrevExecucao)
                && Objects.equals(produto, that.produto)
                && Objects.equals(unidadeMedida, that.unidadeMedida)
                && Objects.equals(encomenda, that.encomenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrdemProducao, dataEmissao, dataPrevExecucao, produto, unidadeMedida, encomenda);
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public String identity() {
        return idOrdemProducao;
    }
    
    public String toString(){
        return String.format("Identificador da Ordem de Produção: %s ; Data de Emissão da mesma: %s ; Data Prevista de Execução da mesma: %s ; Produto Referido: %s ; Identificador da Execução da Ordem Correspondente: %d.", idOrdemProducao, dataEmissao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dataPrevExecucao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), produto.getCodigoFabrico(), execucaoOrdemProducao.getIdExecucaoOrdemProducao());
    }
}
