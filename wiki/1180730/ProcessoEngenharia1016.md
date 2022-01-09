# 1016 - Reinicialização do Simulador de Máquina
=======================================

# 1. Requisitos

**Descrição**: Como **Gestor de Projeto**, eu pretendo que o simulador de máquina suporte pedidos de reinicialização (reset) do seu estado.


**Fluxo Principal**

* Este UC arranca em simultâneo com o [cliente] do simulador de máquina. E irá permanecer em execução paralela com o mesmo.
* O Sistema de Monitorização das Máquinas (SMM) deve se encontrar no sistema geral.
* O Sistema de Monitorização das Máquinas (SMM) manda o pedido de reinicialização.
* O servidor UDP recebe o pedido de HELLO. Manda mensagem ao SMM a dizer que esta pronto a receber pedidos.
* O servidor UDP recebe o pedido de RESET.
* O simulador de máquina para o que estiver a fazer e reinicia. Quando reiniciar retoma a atividade.

A interpretação feita deste requisito foi no sentido de respeitar as seguintes condições:

* A máquina pode ou não aceitar o pedido HELLO. Caso esse pedido seja recusado, o sistema volta a fazê-lo após o sleeptime definido.
* A máquina pode ou não aceitar o pedido RESET. Caso esse pedido seja recusado, o sistema não volta a fazer mais pedidos. Só fará um novo se um utilizador o voltar a solicitar.
* As mensagens trocadas entre o sistema e os endereços de rede seguem o formato geral explícito no Protocolo de Comunicação disponibilizado.
* O pedido RESET é executado com a introdução de um "argumento de reset" (definido por configuração). Este comando permite que o sistema saiba se irá ou enviar um pedido de RESET a uma máquina ou se irá monitorizar um conjunto delas.


# 2. Análise

*As questões e respostas aqui transcritas são as que estão presentes no fórum de esclarecimento de requisitos de LAPR4*


**Q:** Quando é solicitado o pedido de reinicialização da máquina, e este é aceite, a máquina é reinicializada. O que é que se faz quando se reinicializa uma máquina? É mudado o estado da máquina? Sentimos que essa parte não está muito bem explícita.

**R:** Num cenário típico, quando uma máquina recebe um pedido de reinicialização, esta começa por interromper (em "segurança" / adequadamente) as atividades/processos em curso (e.g. envio de mensagens) e reinicia-se (i.e. desliga-se e volta a ligar-se). Após reiniciar, esta retoma as atividades/processos que tem pendentes (e.g. os que foram interrompidos). Notem que entre desligar-se e voltar a ligar-se passa algum tempo (e.g. 20 segundos).
Ora, no simulador o que se pretende é de alguma forma simular este comportamento.
Para tal, por exemplo, podem interromper todas operações e durante algum tempo não responder a nada.
Após esse intervalo, recomeçam como se o simulador estive a "arrancar" (i.e. ser executado) novamente.

=======================================

**Q:** Continuamos com uma dúvida relativa a este tópico. Em nenhuma parte dos requisitos fornecidos (tanto no penúltimo parágrafo deste documento https://moodle.isep.ipp.pt/pluginfile.php/323719/mod_resource/content/1/LEI-2019-20-Sem4-_Projeto_ProtocoloComunicacao.pdf como no ficheiro excel fornecido para este sprint) é pedida a interropção dos outros processos na máquina. No parágrafo referido anteriormente é apenas dito que "O sistema de monitorização pode enviar a uma máquina industrial um pedido RESET. Neste caso, a máquina industrial deve enviar um pedido HELLO para o sistema central e a resposta correspondente deve ser encaminhada como resposta ao pedido de RESET."
Gostávamos de esclarecer se o Cliente, com o que disse na resposta anterior, está a apresentar novos requisitos(interromper a execução dos outros processos)  a serem cumpridos neste Sprint.

**R:** Mais do que apresentar novos requisitos (embora aceite que possa ser entendido como tal), descrevi o que acontece num máquina real e, por conseguinte, gostava que o simulador, de facto, simulasse o melhor possível esse comportamento de uma máquina real.
Notem que:
1. Entre a recepção do pedido de RESET e a resposta a esse pedido a máquina/simulador pode fazer coisas (e.g. interromper processos).
2. A mensagem HELLO é enviada pela máquina/simulador ao SCM quando esta (re)inicia, conforme consta no documento supramencionado e que passo a transcrever "Quando uma máquina industrial é iniciada, ela envia um pedido HELLO baseada em TCP para o sistema central."
Portanto, não há, de facto, nenhuma contradição.
Se quiserem há é um esclarecimento do que acontece (ou deve acontecer) entre o momento de recepção da mensagem RESET e o envio (novamente) da mensagem HELLO.
Encarem a inclusão destes aspetos como um fator de valorização da qualidade da solução.

=======================================

**Q:** A resposta ao pedido HELLO enviado pela máquina para o SCM influência se deve ocorrer ou não a reinicialização?
Ou seja, caso a resposta a esse pedido seja ACK então ocorre reinicialização, caso a resposta seja NACK então essa reinicialização não deve acontecer.
Não me parece que faça muito sentido a resposta ser ACK ou NACK e gerar o mesmo comportamento (Efetuar RESET).

**R:** Se lerem com atenção o exposto acima, percebem que quando simulador recebe um pedido de RESET, este executa o reset e pronto.
Ao reiniciar-se (simulação), o simulador, vai tentar conetar-se ao SCM através de um HELLO.
A resposta a esse HELLO sendo ACK, significa que o simulador reiniciou-se e está tudo ok, portanto responde ao pedido de reset com ACK.
Caso a resposta a esse HELLO seja NACK, então significa que o simulador reiniciou-se e não conseguiu ligação ao SCM corretamente, respondendo NACK.

=======================================

# 3. Design

Este UC arranca em simultâneo com o cliente do simulador de máquina. E irá permanecer em execução paralela com o mesmo. O Sistema de Monitorização das Máquinas (SMM) deve se encontrar no sistema geral. Quando o SMM manda o pedido de reinicialização, o controller udp.c verifica se a mensagem tem o código HELLO. Se tiver, verifica se o ultimo estado da máquina foi ACK e se sim, manda uma mensagem ao SMM a avisar que está apto a receber pedidos. O SMM manda outra mensagem com o código RESET. O controller udp.c verifica se o código é RESET e se for começa a reinicialização (um sleep de 30 segundos). Após isso, o cliente do simulador retoma as atividades, mandando primeiro uma mensagem HELLO ao SCM. Mediante essa resposta, o ucp.c responde ao SMM, ACK ou NACK de acordo com a ligação ao SCM.

## 3.3. Padrões Aplicados

| **Questão: Que classe...**       | **Resposta**                       | **Justificação**                                         |
|----------------------------------|------------------------------------|----------------------------------------------------------|
| ...interage com o utilizador?    | main.c              | Pure Fabrication                                         |
| ...coordena o UC?                | udp.c    (método controller)      | Controller                                               |


## 3.4. Testes

**Nota:** O grupo reconhece a falta de testes unitários para este UC. Todos os testes foram realizados de forma manual.

# 4. Implementação

Este Use Case foi inteiramente implementado em C.

# 5. Integração/Demonstração

![LinhaDeComandos](reset.png)

# 6. Observações

*Nesta secção sugere-se que a equipa apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.*
