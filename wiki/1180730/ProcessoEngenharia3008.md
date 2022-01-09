# 3008 - Conseguir ver o estado atual das máquinas de cada linha de produção
=======================================

# 1. Requisitos

**Descrição**: Como **Gestor de Chão de Fábrica**, eu pretendo conhecer o estado atual das máquinas de cada linha de produção.


**Fluxo Principal**
* O Gestor de Chão de Fábrica deve estar logado no sistema.
* O Gestor de Chão de Fábrica pretende ver o estado atual das máquinas de cada linha de produção.
* O sistema mostra os dados de cada máquina de cada linha de produção.


A interpretação feita deste requisito foi no sentido de respeitar as seguintes condições:

* Conseguir ver os estados das máquinas todos ao mesmo tempo.

**Regras de negócio**

* Uma **Linha de Produção** tem um identificador e possui um conjunto de máquinas.
* Uma **Maquina** tem um identificador.


# 2. Análise

* A partir da análise do modelo de domínio atual, conclui-se que o mesmo satisfaz as condições exigidas pelo UC.

![Modelo_de_Dominio.svg](../Modelo_de_Dominio.svg)

**Questões em aberto**
* Qual a frequência deste UC?

# 3. Design

## 3.1. Realização da Funcionalidade

![SD_3008.svg](SD_3008.svg)

## 3.2. Diagrama de Classes

![CD_3008.svg](CD_3008.svg)

## 3.3. Padrões Aplicados


| **Questão: Que classe...**       | **Resposta**                       | **Justificação**                                         |
|----------------------------------|------------------------------------|----------------------------------------------------------|



## 3.4. Testes
*Nesta secção deve sistematizar como os testes foram concebidos para permitir uma correta aferição da satisfação dos requisitos.*

**Teste 1:** Verificar que não é possível criar uma instância da classe Produto com código de fabrico nulo.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {
      Instance instance = new   Instance(null, "XXXX", "XXXX", "XXXX", "XXXX", "XXXX","XXXX);
   }`


# 4. Implementação

Este Use Case foi inteiramente implementado em C.

# 5. Integração/Demonstração

*Nesta secção a equipa deve descrever os esforços realizados no sentido de integrar a funcionalidade desenvolvida com as restantes funcionalidades do sistema.*

# 6. Observações

*Nesta secção sugere-se que a equipa apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.*
