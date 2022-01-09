package eapli.base.producao.domain;

import eapli.framework.domain.model.AggregateRoot;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

@Entity
public class TempoExecucao implements AggregateRoot<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private String idTempo;

    private Timestamp inicio;

    private Timestamp fim;

    private Time tempoTotalBruto;

    private Time tempoTotalEfetivo;

    @ElementCollection()
    @MapKeyColumn(name = "InstanteParagem")
    @Column(name = "InstanteRetoma")
    private Map<Timestamp, Timestamp> listaParagens = new HashMap<>();

    public TempoExecucao() {
        inicio = null;
        fim = null;
    }

    public TempoExecucao(Timestamp inicio, Timestamp fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Timestamp getInicio() {
        return inicio;
    }

    public Timestamp getFim() {
        return fim;
    }

    public Time getTempoTotalBruto() {
        return tempoTotalBruto;
    }

    public Time getTempoTotalEfetivo() {
        return tempoTotalEfetivo;
    }

    public Map<Timestamp, Timestamp> getListaParagens() {
        return listaParagens;
    }

    public void setInicio(Timestamp inicio) {
        this.inicio = inicio;
    }

    public void setFim(Timestamp fim) {
        this.fim = fim;
    }

    public void setListaParagens(Map<Timestamp, Timestamp> lista) {
        this.listaParagens = lista;
    }

    public void adicionarParagem(Timestamp paragem) {
        listaParagens.put(paragem, null);
    }

    public void adicionarRetoma(Timestamp retoma) {
        for (Timestamp paragem : listaParagens.keySet()) {
            if (listaParagens.get(paragem) == null) {
                listaParagens.put(paragem, retoma);
                break;
            }
        }
    }

    public void calcularTotalBruto() {
        if (inicio != null && fim != null) {
            int milisecondsInicio = (int) inicio.getTime();
            int milisecondsFim = (int) fim.getTime();
            int milisecondsTotal = milisecondsFim - milisecondsInicio;

            tempoTotalBruto = converterMilisegundosEmHoraMinutoSegundo(milisecondsTotal);
        }
    }

    public void calcularTotalEfetivo() {
        if (inicio != null & fim != null) {
            int milisecondsParagem = 0;
            int aux;
            for (Timestamp paragem : listaParagens.keySet()) {
                aux = (int) (listaParagens.get(paragem).getTime() - paragem.getTime());
                milisecondsParagem = milisecondsParagem + aux;
            }
            int milisecondsInicio = (int) inicio.getTime();
            int milisecondsFim = (int) fim.getTime();
            int milisecondsTotal = milisecondsFim - milisecondsInicio - milisecondsParagem;

            tempoTotalEfetivo = converterMilisegundosEmHoraMinutoSegundo(milisecondsTotal);
        }
    }

    private Time converterMilisegundosEmHoraMinutoSegundo(int milisegundos) {
        int seconds = milisegundos / 1000;

        int hora = seconds / 3600;
        int minuto = (seconds % 3600) / 60;
        int segundos = (seconds % 3600) % 60;

        Time tempoTotal = new Time(hora, minuto, segundos);

        return tempoTotal;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public String identity() {
        return null;
    }
}
