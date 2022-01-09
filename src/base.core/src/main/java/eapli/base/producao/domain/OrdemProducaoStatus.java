package eapli.base.producao.domain;

public enum OrdemProducaoStatus {
    PENDENTE("Pendente"), EM_EXECUCAO("Em execução"), EXECUCAO_PARADA_TEMPORARIAMENTE("Execução parada temporariamente"), SUSPENSA("Suspensa"), CONCLUIDA("Concluída");

    private final String nome;

    OrdemProducaoStatus(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
