
# 1011 - Simulação do funcionamento de uma máquina
=======================================

# 1. Requisitos

**Descrição**: Como **Gestor de Projeto**, eu pretendo que a equipa desenvolva uma aplicação que simule o funcionamento de uma máquina, nomeadamente no envio de mensagens geradas por estas.

**Fluxo Principal**
* O Gestor de Projeto deve estar logado no sistema.
* O Gestor de Projeto deve introduzir os dados necessários para a realização da simulação (argumentos).
* O sistema irá posteriormente validar os dados introduzidos e proceder à execução, caso possível.

A interpretação feita deste requisito foi no sentido de respeitar as seguintes condições:

* Uma máquina não pode emitir duas mensagens no mesmo instante de tempo.

**Regras de negócio**

Por fim, assume-se que as máquinas industriais são identificadas através de um número de identificação único (**unique identification number**), que corresponde a um número inteiro positivo entre **1** e **65535**.

Uma **Mensagem** enviada através de um socket possui a seguinte estrutura:

![Tabela1](https://i.ibb.co/ZVHwTfz/144843.png)

**Tipos de Códigos** de uma **Mensagem**.

![Tabbela2](https://i.ibb.co/cT6hTtk/144943.png)

# 2. Análise

* A partir da análise do modelo de domínio atual, conclui-se que o mesmo satisfaz as condições exigidas pelo UC.

![Modelo_de_Dominio.svg](../Modelo_de_Dominio.svg)

**Questões em aberto**
* Qual a frequência deste UC?

# 3. Design

## 3.1. Realização da Funcionalidade

![SD.svg](http://www.plantuml.com/plantuml/svg/ZL1DIyD04BtlhnXlpQ4bEVHIM55wqKD1A7YLeJkc0_iHTwS6fF_6W_WxyiTC3Y6nQk8S1cRdlNbldx1OWYBFrx21Uyy7Hijls7mqxm4CmHQ5oX0PKpVjK9hFRbW-H7QObDfQ9Y-qt-7BYJqkh_SKNzc4kAaZ2orSAk_8sf2fVZ62H48pZU3qsFePG2isOzo5-g1vwrDDCVjfxvBHM-W9MWC_m_2oEeJstrSQYdMHWTRp5zbL8SgJevImfBIeK8wPiY5KK1_Pqk-tuNo69D6Ix_QT4kIPWb7z8-dlX8d0_mBEvZbfQ2vRyaQfcxRBoTal)

## 3.2. Diagrama de Classes

![CD.svg](http://www.plantuml.com/plantuml/svg/NL0xJiKm4EnpYbKg40eYxHCT1HMYY4GRhU9zOOK_mMlp4U8mY09nZboCjGDFw7KpizxPyIHEbg2_w7leO62NBQvVwsS04s2V_SJ1eoEVL2383i6j7u-PFJRDP544hYphc8ORh3AyDMUW2u8pkJ75CE4_GU62KGrr84emphcw5o9XUjYRw_8SsP-SAeY4feBdo8agcYcDblr6s2qXfj6H5FigJJNFAEGDnKq9NizPM-dxN-w1uXER46jWbZkqV7pgzxgk-9088i_tQGUteXryfs22b98EFb9Qar2-Kve2UY6tMEqDrMY9f8dIrkrvqnosshw5ooul7RGE_MjxtETEuxliMMly0G00)

## 3.3. Padrões Aplicados

| **Questão: Que classe...**       | **Resposta**                       | **Justificação**                                         |
|----------------------------------|------------------------------------|----------------------------------------------------------|
| ...interage com o ClienteSMaquina?    | Servidor                  | Pure Fabrication                                         |
| ...coordena o UC?                | ClienteSMaquina   (controller)       | Controller                                               |


## 3.4. Testes
*Nesta secção deve sistematizar como os testes foram concebidos para permitir uma correta aferição da satisfação dos requisitos.*

**Teste 1:** Verificar que não é possível criar uma instância da classe Produto com código de fabrico nulo.

`
      @Test
      public void isValidTest() {
      boolean result = isValid("T3);
      assertTrue(result);
   }`

**Nota:** O grupo reconhece a falta de testes unitários para este UC.

# 4. Implementação

Este Use Case foi inteiramente implementado em C.

 - **Ordem dos Argumentos**:
	 - **ARGV[0]** : NOME DO FICHEIRO COMPILADO **[Sem interesse para o utilizador]**
	 - **ARGV[1]** : ID EXTERNO DA MÁQUINA
	 - **ARGV[2]** : ENDEREÇO DE IP
	 - **ARGV[3]** : PORTA
	 - **ARGV[4]** : CADÊNCIA
	 - **ARGV[5]** : ID INTERNO DA MÁQUINA

# 5. Integração/Demonstração

*Nesta secção a equipa deve descrever os esforços realizados no sentido de integrar a funcionalidade desenvolvida com as restantes funcionalidades do sistema.*

**Cliente Válido**

![ClienteV](https://i.ibb.co/QHL43KB/Cliente.png)

**Resposta do Servidor**

![ServidorV](https://i.ibb.co/TTwtV9n/Servidor-Valido.png)

**Cliente Inválido (Máquina não reconhecida pelo servidor)**

![ClienteN](https://i.ibb.co/gD2sDsc/ClienteN.png)

**Resposta do Servidor**

![ServidorV](https://i.ibb.co/SVLfD7G/Servidor-Invalido.png)

# 6. Observações

*Nesta secção sugere-se que a equipa apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.*

Considerando uma perspetiva critica da elaboração de UC, consideramos que o facto deste UC ser desenvolvido numa linguagem diferente (**C**) e ter que comunicar com outra linguagem (**Java)**, apresentou alguns obstáculos.