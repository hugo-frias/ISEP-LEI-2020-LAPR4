# 1012 - Pedidos de monitorização do estado das máquinas
=======================================

# 1. Requisitos

**Descrição**: Como **Gestor de Projeto**, eu pretendo que o simulador de máquinas suporte pedidos de monitorização do seu estado.

**Fluxo Principal**
* O Gestor de Projeto deve estar logado no sistema.
* O Gestor de Projeto pretende ver o estado de uma máquina.
* O sistema mostra as máquinas e pede para selecionar qual o Gestor pretende ver o estado.
* O Gestor de Projeto seleciona a máquina.
* O sistema mostra o estado da máquina selecionada.

A interpretação feita deste requisito foi no sentido de respeitar as seguintes condições:

* Conseguir ver se uma máquina esta ligada e com conexão ao servidor.

**Regras de negócio**

* Uma **Linha de Produção** tem um identificador e possui um conjunto de máquinas.
* Uma **Maquina** tem um identificador.
* Uma **Mensagem** tem um identificador, um timestamp e é emitida por uma máquina.

# 2. Análise

*As questões e respostas aqui transcritas são as que estão presentes no fórum de esclarecimento de requesitos de LAPR4*

**Q:** Relativamente ao UC 1012, na área reservada à especificação diz o seguinte "Deve ser concorrente (em C) com o resto da simulação da máquina e o estado deve ser partilhado entre threads."
1 - O estado referido indica se a máquina está ativa ou não?
2 - Não consegui entender qual a necessidade de partilhar o estado entre diferentes threads... Seria possível especificar quais as operações a efetuar que tornem mais explicita esta necessidade?


**R:** Essa indicação nos critérios de aceitação da User Story é genérica e não foi feita pensando em situações concretas.
Portanto, cabe à equipa identificar e analisar a efetiva necessidade (ou não) dessa essa partilha de estado/informação.
Para tal, por exemplo, podem/devem analisar tecnicamente o protocolo de comunicação.
Faz parte do vosso trabalho saber analisar o problema, tomar decisões e justificar/fundamentar corretamente essas decisões tanto ao cliente como tecnicamente (i.e. aos peritos das outras UC).
Ainda a este respeito convém acrescentar algo que talvez possa ajudar:
1. Quando se coloca o simulador de máquina em execução isso significa que a máquina simulada está "ligada".
2. O facto de estar "ligada" não significa que esteja em comunicação com a aplicação servidora de recepção de mensagens (SCM). A comunicação é estabelecida de acordo com as regras estabelecidas no protocolo de comunicação.
3. Notem que o simulador deve enviar uma mensagem de cada vez para o SCM (e.g. uma mensagem a cada 5 segundos). O tempo de cadência (e.g. 5 segundos) serve para "simular" que a máquina está em operação, logo, a gerar mensagens. Trata-se de um "simulador" e não de uma máquina real. As máquinas reais produzem mensagens com base nos sensores que possam ter. O simulador não tem esses sensores, daí ter-se sugerido ler as mensagens a enviar de um ficheiro. Assim, controla-se as mensagens que são geradas/simuladas para o sistema.
4. Uma mensagem só deve ser considerada enviada quando a SCM sinaliza que recebeu corretamente a mensagem.
5. A meio do processo de envio de mensagens a comunicação pode "cair" ou desligar-se. É algo muito comum.
6. De acordo com as regras estabelecidas no protocolo de comunicação, deve-se tentar estabelecer novamente a comunicação entre ambos (simulador e SCM). Isto deve acontecer sem reiniciar o simulador, pois isso é o equivalente a desligar e ligar a máquina. Ou seja, a máquina deixaria de trabalhar só porque não estabelece comunicação com o servidor. Como é evidente, isso não faz sentido.
7. O envio de mensagens deve retomar no ponto (mensagem) em que parou.
8. O estado (ligado/desligado) de comunicação do simulador com a SCM é relevante ser conhecido. Inclusivé, para decidir a resposta a dar aos pedidos de monitorização.


**Questões em aberto**
* Qual a frequência deste UC?

# 3. Design

O PedidoEstado(controller do US1012) irá receber da classe controller do US1011(em C) a mensagem que foi enviada pela classe RecolherMensagens (US4002-Java). Depois de a receber o PedidoEstado (controller do US1012) irá mandar para o SMM(em Java, US6001) a mensagem.

## 3.3. Padrões Aplicados


| **Questão: Que classe...**       | **Resposta**                       | **Justificação**                                         |
|----------------------------------|------------------------------------|----------------------------------------------------------|
| ...interage com o ClienteSMaquina?    | Servidor                  | Pure Fabrication                                         |
| ...coordena o UC?                | PedidoEstado   (controller)       | Controller                                               |



## 3.4. Testes

O grupo reconhece a falta de testes unitários para este UC.

# 4. Implementação

Este Use Case foi inteiramente implementado em C.

# 5. Integração/Demonstração

*Nesta secção a equipa deve descrever os esforços realizados no sentido de integrar a funcionalidade desenvolvida com as restantes funcionalidades do sistema.*

# 6. Observações

*Nesta secção sugere-se que a equipa apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.*
