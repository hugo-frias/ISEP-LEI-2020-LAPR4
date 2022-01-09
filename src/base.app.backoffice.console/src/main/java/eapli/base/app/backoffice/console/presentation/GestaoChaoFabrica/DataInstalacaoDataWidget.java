package eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica;

import eapli.framework.util.Console;
import java.time.LocalDate;

public class DataInstalacaoDataWidget {
    
    private Integer dia;
    private Integer mês;
    private Integer ano;

    public void show() {
        this.dia = Console.readInteger("Dia de instalação");
        this.mês = Console.readInteger("Mês de instalação");
        this.ano = Console.readInteger("Ano de instalação");
    }

    public LocalDate Data() {
        return LocalDate.of(this.ano,this.mês,this.dia);
    }
}
