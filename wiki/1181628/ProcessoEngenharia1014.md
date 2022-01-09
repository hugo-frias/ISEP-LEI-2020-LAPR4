# 1014 - Receção de ficheiros de configuração por parte do Simulador de Máquina
==============================================================================

# 1. Requisitos

**Descrição**: Como **Gestor de Projeto**, eu pretendo que o simulador de máquina suporte a recepção de ficheiros de configuração.

**Fluxo Principal**
* Este UC arranca em simultâneo com o [cliente] do simulador de máquina. E irá permanecer em execução paralela com o mesmo.
* O Servidor do Simulador recebe o pedido **HELLO** e verifica se o ID corresponde ao ID do Simulador.
* O Servidor do Simulador recebe o pedido **CONFIG** e importa/persiste num ficheiro localmente, bem como comunica ao Cliente do Simulador.
* O mesmo irá permanecer em execução até que o **Gestor de Projeto** utilize a combinação **Ctrl + C**.

A interpretação feita deste requisito foi no sentido de respeitar as seguintes condições:

* Permitir que o simulador de máquina esteja recetivo a pedidos de receção de ficheiros de configuração.
* Cada **Máquina** só pode receber 1 configuração, e a configuração irá ser um código alfanumérico de 16 letras/números (**DECISÃO DO GRUPO**).

**Regras de negócio**

* Uma **Maquina** tem um identificador.
* Uma **Máquina** apenas pode possuir um único ficheiro de configuração num instante.

# 2. Análise

*As questões e respostas aqui transcritas são as que estão presentes no fórum de esclarecimento de requesitos de LAPR4*

**Questões em aberto**
**P**: Tendo em conta que o simulador é executado para simular o funcionamento de uma máquina, nós devemos enviar um "pedido" ao simulador em funcionamento para que este receba um ficheiro de configuração? Ou seja, se eu definir que a cadência de um simulador é de 5 minutos e tenho 10 mensagens para importar, posso fazer o pedido de receção do ficheiro de configuração a meio do processamento das mensagens e este ser recebido no fim do processamento ou devo cessar o processamento em andamento para configurar a máquina?

**R**: É suposto conseguir-se enviar um ficheiro de configuração para a máquina (simulador) a qualquer momento, desde que a máquina esteja "ligada". Nota que os processos em causa (i.e. envio de mensagens e recepção de ficheiro de configuração) podem/devem ocorrer em modo paralelo/concorrente.

**P**:

1. O ficheiro pode possuir tamanho superior ao tamanho máximo de conteudo de mensagem definido no protocolo?
2. O simulador pode receber multiplos ficheiros de configuração? Se sim, esta receção pode ocorrer simultaneamente? Como são diferenciadas mensagens de dois ficheiros atendendo ao protocolo?

**R**:
1. Sim, pode. Isso é, aliás, o cenário normal.
2. Não faz sentido enviar ao mesmo tempo mais do que um ficheiro de configuração para a máquina/simulador. Quando se envia um ficheiro de configuração para a máquina é com o objetivo da máquina assumir essa configuração em detrimento da configuração atual. A cada momento, a máquina assume apenas 1 e só 1 configuração.

**P**: Visto que o simulador da máquina tem de assumir configurações recebidas pelo ficheiro de configuração, seria possível especificar com mais detalhe quais seriam essas configurações (com um ficheiro de exemplo) para que não existam problemas de implementação provenientes de interpretação incorreta?

**R**:
O simulador NÃO tem que **assumir** (no sentido de interpretar) as configurações recebidas. De acordo com a US 1014, o simulador tem apenas que receber um ficheiro de configuração que lhe seja enviado. Para demonstrar que recebeu corretamente esse ficheiro pode, por exemplo, persisti-lo localmente numa pasta. Repito o que já disse vezes sem conta: o conteúdo desses ficheiro é texto e a seu estrutura interna é irrelevante para o sistema em desenvolvimento.

* Qual a frequência deste UC?

**Notas**:
- O limite para o TCP está implícito pelo facto de o tamanho do campo **DATA RAW**, em bytes, estar especificado através do campo **DATA LENGTH** de dois bytes, como tal pode pode ter um valor entre **0** e **65535** (tal como o ID da máquina).

# 3. Design

O **UC1014** irá rodar em paralelo com o **UC1015**. O mesmo irá aguardar pedidos de aceitação de ficheiros de configuração do SCM (**UC3001**) para posteriormente preservar os conteúdos do próximo Socket.
O código para identificar um pedido de aceitação de ficheiros de configuração é **2**, como mostrado na tabela do protocolo:

![Tabbela2](https://i.ibb.co/cT6hTtk/144943.png)

O grupo considerou que o ficheiro de configurações seria enviado em somente **um socket**, após esta resposta/pergunta ao professor **André Moreira**:

**Alexandre Mendes 1180810**: Professor, fiquei confuso quanto ao ponto dos 65535 bytes, no protocolo é nos dito "Devido aos limites do UDP, o tamanho máximo dos pedidos/respostas é de 512 bytes" , no entanto se o limite para udp não se aplica ás transações tcp, então não está referido nenhum limite para tcp no protocolo. O número 65535 refere apenas o id máximo da máquina, devemos tomar 65535 como um novo requesito e limite de tamanho de mensagem em tcp, ou tomamos como limite os 512 bytes que utilizamos para udp e caso for necessário o cliente ajusta numa nova versão do protocolo?

**Professor André Moreira ASC**: Boa tarde. Relativamente ao primeiro ponto, sob o ponto de vista do protocolo de comunicação, de momento todos devem assumir que nas transações TCP _**não é necessário**_ suportar conteúdos de dimensão superior ao máximo implícito no referido protocolo, ou seja 65535 bytes. Isso é válido para todos os tipos de pedidos e respostas definidos na versão atual do protocolo.

![ServerTCP](https://media.geeksforgeeks.org/wp-content/uploads/Socket_server-1.png)
Source:[GeeksForGeeks](https://www.geeksforgeeks.org/tcp-server-client-implementation-in-c/)

![SD](http://www.plantuml.com/plantuml/svg/hPBFJi904CRlVOevjWS8JVH2Wy5mbGiaNiraEosJxEx0_X5VntZmGNWnIrM2rIPQxA6RoSployxtxTm7T24QdGGEcY2Vv5SmWXKfEhmUNWHAWehLXjY9FnPARCLrTFZUteg3RQD0K0gisKIDfRZZuFBml8jiCKdMv1vPoK9CjAnGeIonRVdHJTjhY1CwzMJBT4DQoufsaNp8acz3Ft6ES137_XurTz7P3BY2zAC9JPbVvrbp_axK-FhM-ZsviPAIeAMB7hJ7c-dUSQ3qf9Jz4Qz8RN0XbfvGqdOXqfuksM1ImAKhQ94jLEBIeiWIEFlwHNgpDcGzrcGUWXE5N-NwupkV6XvbH-OIJpfImoFk6D36FMXZB5cnM7HtKdTS7d-MdMiFVr7_wIJPCfatlsZq6m00)
**Nota - 1**: O método **isValid()** representa as condições do ID da máquina ser diferente do ID da máquina que o simulador está a simular, a condição decidida pelo grupo (tamanho da mensagem igual a 16) e a mensagem ser vazia.
**Nota - 3**: Considera-se o **SD** como forma de representar somente as conexões e a ideia geral do código, não representando efetivamente todos os métodos designados.
**Nota - 3**: O UC foi planeado para suportar a recepção de várias mensagens de modo a no final totalizar um único ficheiro de configuração, contudo após as últimas declarações do professor regente de **RCOMP** sobre o tamanho dos **65535** bytes, o grupo implementou de modo a que 1 mensagem represente a totalidade do ficheiro de configuração.

## 3.3. Padrões Aplicados

| **Questão: Que classe...**       | **Resposta**                       | **Justificação**                                         |
|----------------------------------|------------------------------------|----------------------------------------------------------|
| ...interage com o utilizador?    | main.c              | Pure Fabrication                                         |
| ...coordena o UC?                | simulador.c          | Controller                                               |

## 3.4. Testes

**Nota**: O grupo reconhece a falta de testes unitários para este UC. Todos os testes foram realizados de forma manual.

# 4. Implementação

- Este Use Case foi inteiramente implementado em **C**.

# 5. Integração/Demonstração

*Nesta secção a equipa deve descrever os esforços realizados no sentido de integrar a funcionalidade desenvolvida com as restantes funcionalidades do sistema.*

![Demonstração](https://i.ibb.co/cXCY7R6/Desenvolvimento.png)

# 6. Observações

*Nesta secção sugere-se que a equipa apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.*

* Este UC, pelo facto de ser inteiramente implementado em **C**, apresentou dificuldades na realização da documentação, bem como nos testes práticos pela minha falta de experiência.
* Este UC tem as suas conexões protegidas por **SSL/TLS** através do **UC1015**.
