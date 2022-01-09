# [2003] - Consultar produtos sem Ficha de Produção
=======================================

# 1. Requisitos

**Descrição:** Como **Gestor de produção**, eu pretendo consultar os produtos sem ficha de produção.

**Fluxo Principal**
* O Gestor de produção inicia o processo de consulta aos produtos sem ficha de produção.
* O sistema procura e apresenta os produtos sem ficha de produção.

**Pré-Requisitos**
* O Gestor de produção deve estar logado no sistema

**Regras de Negócio**
*  Um **Produto** tem um identificador unico.

# 2. Análise

* A partir da análise do modelo de domínio atual, conclui-se que o mesmo satisfaz as condições exigidas pelo UC.

![Modelo_de_Dominio.svg](../Modelo_de_Dominio.svg)

# 3. Design

## 3.1. Realização da Funcionalidade

![2003SSD.png](2003SSD.png)

## 3.2. Diagrama de Classes

*Nesta secção deve apresentar e descrever as principais classes envolvidas na realização da funcionalidade.*

## 3.3. Padrões Aplicados

| **Questão: Que classe...**       | **Resposta**                       | **Justificação**                                         |
|----------------------------------|------------------------------------|----------------------------------------------------------|
| ...interage com o utilizador?    | ConsultarOredemDeProducaoUI                  | Pure Fabrication                               |
| ...coordena o UC?                | ConsultarOrdemProducaoEncomendaController          | Controller                               |
|...persiste maquinas?			   |	EncomendaRepository				|			Repository									   |


# 4. Implementação

## Realização dos Requerimentos
**Classes alteradas**
* ConsultarProdutosSemFichaProducaoAction.java
* ConsultarProdutosSemFichaProducaoUI.java 
* ConsultarProdutosSemFichaProducaoController.java

# 5. Observações

Tendo tido problemas com a configuração do Maven, não tive oportunidade para testar devidamente o código.
