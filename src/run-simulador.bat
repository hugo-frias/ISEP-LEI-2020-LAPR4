REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.simulador.maquina\target\base.simulador.maquina-1.3.0-SNAPSHOT.jar;base.simulador.maquina\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% eapli.base.simulador.maquina.SimuladorMaquina %*