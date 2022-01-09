

# 2010 - Adicionar uma nova Ordem de Produção
=======================================

# 1. Requisitos

**Descrição**: Como **Gestor de Produção**, eu pretendo introduzir manualmente uma nova ordem de produção.

**Fluxo Principal**
* O Gestor de Produção deve estar logado no sistema.
* O sistema irá apresentar uma lista de todos os Produtos catalogados no sistema.
* O Gestor de Produção deve selecionar o Produto pretendido a ser produzido e introduzir a quantidade pretendida.
* O sistema irá posteriormente validar os dados introduzidos e solicitar confirmação.
* No final, o Gestor de Produção confirma os dados anteriormente selecionados.

A interpretação feita deste requisito foi no sentido de respeitar as seguintes condições:

* Uma Ordem de Produção é referente a somente um único OrdemProducao.
* Uma Ordem de Produção é produzida em somente uma única linha de produção.
* Uma Ordem de Produção é caracterizada por: um identificador, uma data de emissão, uma data prevista de execução. Por sua vez, refere um Produto, uma Encomenda e uma Execução de Ordem de Produção.

**Regras de negócio**

* O identificador da Ordem de Produção é um código alfanumérico único.
* A data prevista de execução deve ser superior à data de emissão da Ordem de Produção.

# 2. Análise

* A partir da análise do modelo de domínio atual, conclui-se que o mesmo satisfaz as condições exigidas pelo UC.

![Modelo_de_Dominio.svg](../Modelo_de_Dominio.svg)

**Questões em aberto**
* Qual a frequência deste UC?

# 3. Design

## 3.1. Realização da Funcionalidade

![2010 - Adicionar uma nova Ordem de Produção](http://www.plantuml.com/plantuml/svg/dLF1QiCm3BtdAtHC8OLkny0APNJGmrZfw0S8I-qCgRNOJjTzpmxxaFxOuZGbf4cth2VZzvwa9ydsscSCzvEx2OnXHbffCMYXs24OsIEyME8T4CFI2XN7x-ENHD6Ajzfvj1LP4GfblHXFUub7CTvAbh5DbMNqV7lybds5Ud7nAFf3FPuksRfImKPnWFdWKylluhGNgpcaw2g2YLho-OGg78tj2wAiS7BJCB-ssFBMYTneGxCi6z3ObK6j5waN4dSUqJ_dN8O3oUkRV5opC6H5je-FDR7nUgCLajW4yWBBAo5n0fWNd77jEO72LFWpKtacGEXnQNaVSiuFh4As0Pi_Irk2b6kqLWeym6bTSMDenNcXouqWuO0wxVpXnxIGfi35049Ryvrs3gM_nyvSBhsF-fvZg_WDZ_9QlCCznvtkHj4F)

## 3.2. Diagrama de Classes

![2010 - Adicionar uma nova Ordem de Produção_CD](http://www.plantuml.com/plantuml/svg/dLHBRjim4Dth50Eld5eEd2wDRb8x1hme6ZJD0IRas2OWaGf96KcBdgQBekV8nKhgOv6MRBTTIPfvx_7DS3HMs8pW_Unc1bEuvO899L53cID8jKVuh3dbm0dkjUBbsw-tdof9M8R6rAagp531zsJoOBMGMn2Sf1KRmP0hdKoMQ0auMhpBXJ6eWiYzfhqmBlD2p8ia4s2QN7OSQQGXFHrafe3F9MLKsR7kivI28wTFnDqp1Mfm2oMjLfcwIdwqPRGIlN8MEzU81uksDCwpAfzSevvBTBwySeNyvbu7SsH9yYhvLUJaHFAYyhqI-PBQGpeFIp85wQhx9nlP6JPMQNaEMEFMrjrS2FA1JgPfbKUifGMckDYg5JvfmTI1L4SNAYVD16Q7E2V3Fhexe2ZYS4L65hjxZ5JJ8xNqe9A6xBIvkvGvXQtI0kyCKzdE1ODhMxJ9eCHQFxBapWqR_HYEHS1iZWowGzgu0IF9oCyClLYF1as5CiBrNv0P1sw-qDQFeuw6g1csZ7J37S0SJ_hG45qQxg4V8lBMU7vT8VEFoarpGLUzWUZWCMu26o7vRPQ5dAYMqqo3UnhxVHBXAvrBdHlTZE33j2ocrTiW-d4z1xR379E1M_iBVdTQgzDlMIpJF-VWeDV4OmD7oexeXZWvrSZ_OCCeH_aQqaw8pw5ewcXjzwczUWBIXLClzzfR7rw_STbHRWv-ZOKRSpIxlduNuWSrQrwxFF-5isoATxY8L7qqWdFObgZTNwdEmmFV0UNG_dDbQzgGfgHQwqVZMDFdyFOxYJTSZFFKkgHkUvv2_040)

## 3.3. Padrões Aplicados


| **Questão: Que classe...**       | **Resposta**                       | **Justificação**                                         |
|----------------------------------|------------------------------------|----------------------------------------------------------|
| ...interage com o utilizador?    | RegistarOrdemProducaoUI                  | Pure Fabrication                                         |
| ...coordena o UC?                | RegistarOrdemProducaoController          | Controller                                               |
| ...cria/instancia OrdemProducao?       | RegistarOrdemProducaoController          | Creator                                                  |
| ...persiste OrdemProducao?             | ProdutoRepository                  | Repository                                               |
| ...cria ProdutoRepository?     | RepositoryFactory                  | Factory                                                  |
| ...cria LinhaProducaoRepository?     | RepositoryFactory                  | Factory                                                  |


## 3.4. Testes
*Nesta secção deve sistematizar como os testes foram concebidos para permitir uma correta aferição da satisfação dos requisitos.*

**Teste 1:** Verificar que não é possível criar uma instância da classe **OrdemProducao** com identificador nulo.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {
      OrdemProducao instance = new OrdemProducao(null, DATA1, DATA2, produto, linha, execucaoOrdemProducao);
   }`

**Teste 2:** Verificar que não é possível criar uma instância da classe **OrdemProducao** com data de emissão nula.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {
      OrdemProducao instance = new OrdemProducao("XXXX", null, DATA2, produto, linha, execucaoOrdemProducao);
   }`

**Teste 3:** Verificar que não é possível criar uma instância da classe **OrdemProducao** com data prevista de execução nula.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {
      OrdemProducao instance = new OrdemProducao("XXXX", DATA1, null, produto, linha, execucaoOrdemProducao);
   }`

**Teste 4:** Verificar que não é possível criar uma instância da classe **OrdemProducao** com um **Produto** nulo.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {
      OrdemProducao instance = new OrdemProducao("XXXX", DATA1, DATA2, null, linha, execucaoOrdemProducao);
   }`

**Teste 5:** Verificar que não é possível criar uma instância da classe **OrdemProducao** com uma **Linha de Produção** nula.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {
      OrdemProducao instance = new OrdemProducao("XXXX", DATA1, DATA2, produto, null, execucaoOrdemProducao);
   }`

**Teste 6:** Verificar que não é possível criar uma instância da classe **OrdemProducao** com uma **Execução de Ordem de Produção** nula.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {
      OrdemProducao instance = new OrdemProducao("XXXX", DATA1, DATA2, produto, linha, null);
   }`

**Teste 7:** Verificar que não é possível criar uma instância da classe **OrdemProducao** com identificador vazio.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureEmptyIsNotAllowed() {
      OrdemProducao instance = new OrdemProducao("", DATA1, DATA2, produto, linha, execucaoOrdemProducao);
   }`

**Teste 8:** Verificar que não é possível criar uma instância da classe **OrdemProducao** com uma data prevista inferior à data de emissão.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureInvalidDateIsNotAllowed() {
      OrdemProducao instance = new OrdemProducao("XXXX", DATA1, DATA2, produto, linha, execucaoOrdemProducao);
   }`


**Teste 9:** Verificar que não é possível criar uma instância da classe **OrdemProducao** com todos os valores representativos de uma **OrdemProducao** nulos.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {
      OrdemProducao instance = new OrdemProducao(null, null, null, null, null, null);
   }`

# 4. Implementação

Implementámos a classe **ListProdutosService**, de forma a evitar duplicação de código, uma vez que os métodos implementados na mesma podem ser usados por outras classes.

# 5. Integração/Demonstração

*Nesta secção a equipa deve descrever os esforços realizados no sentido de integrar a funcionalidade desenvolvida com as restantes funcionalidades do sistema.*

![Demonstração](https://i.ibb.co/vLk6H0C/Anota-o-2020-05-31-160035.png)

# 6. Observações

*Nesta secção sugere-se que a equipa apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.*