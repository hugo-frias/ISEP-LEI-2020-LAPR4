package eapli.base.spm;

import org.junit.Test;

public class ProcessamentoMensagensTest {
    
@Test(expected = IllegalArgumentException.class)
    public void ensureDataHoraInicioIsNotSuperiorThenDataHoraFim() {
    String[] argumentos = new String[2];
    argumentos[0] = "20190506104501";
    argumentos[1] = "20190506104500";
    ProcessamentoMensagens processamento = new ProcessamentoMensagens();
    processamento.processarMensagens(argumentos);
}

@Test(expected = ArrayIndexOutOfBoundsException.class)
    public void ensureOneTimestampIsPresent() {
    String[] argumentos = new String[1];
    argumentos[1] = "20190506104500";
     ProcessamentoMensagens processamento = new ProcessamentoMensagens();
    processamento.processarMensagens(argumentos);
}

@Test(expected = ArrayIndexOutOfBoundsException.class)
    public void ensureAtLeastOneTimestampIsPresent() {
    String[] argumentos = new String[0];
     ProcessamentoMensagens processamento = new ProcessamentoMensagens();
    processamento.processarMensagens(argumentos);
}

@Test(expected = StringIndexOutOfBoundsException.class)
    public void ensureTimestampIsNumber() {
    String[] argumentos = new String[1];
    argumentos[0] = "fff";
    ProcessamentoMensagens processamento = new ProcessamentoMensagens();
    processamento.processarMensagens(argumentos);
}

@Test(expected = StringIndexOutOfBoundsException.class)
    public void ensureTimestampIsValid() {
    String[] argumentos = new String[1];
    argumentos[0] = "201906";
    ProcessamentoMensagens processamento = new ProcessamentoMensagens();
    processamento.processarMensagens(argumentos);
}
}