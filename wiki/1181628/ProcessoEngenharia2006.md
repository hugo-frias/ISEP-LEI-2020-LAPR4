
# 2006 - Adicionar um produto ao catálogo
=======================================

# 1. Requisitos

**Descrição**: Como **Gestor de Produção**, eu pretendo adicionar um novo produto ao catálogo de produtos.

**Fluxo Principal**
* O Gestor de Produção deve estar logado no sistema.
* O Gestor de Produção deve introduzir os dados necessários de um produto.
* O sistema irá posteriormente validar os dados introduzidos e solicitar confirmação.
* No final, o Gestor de Produção confirma os dados anteriormente introduzidos.

A interpretação feita deste requisito foi no sentido de respeitar as seguintes condições:

* Um produto é caracterizado por: um código de fabrico, um código comercial, uma descrição breve e uma descrição completa, uma unidade de medida e um identificador de categoria.
* A lista de todos os produtos presentes no sistema representa o catálogo da unidade fabril.

**Regras de negócio**

* O código de fabrico de um produto é único.
* O código comercial de um produto é único.
* A descrição breve deve conter o nome do Produto.

# 2. Análise

* A partir da análise do modelo de domínio atual, conclui-se que o mesmo satisfaz as condições exigidas pelo UC.

![ModeloDominio.png](../ModeloDominio.png)

**Questões em aberto**
* Qual a frequência deste UC?

# 3. Design

## 3.1. Realização da Funcionalidade

![2006 - Adicionar um produto ao catálogo.svg](http://www.plantuml.com/plantuml/svg/jL91JiCm4Bpd5JucKY9L73X48X94LEe1gGBoWCLUfPRSRBHs0Zo7j_0n79AIqj2eEN2oFRDZpuxNQMTGNArMro8LjqfBJHMmQFQYPbAD8m4a9BZF3qCb1S4Zbjewu6tF5fiaEu5ogXoJCSYfP0I7opGbkjoBIc8DTg1j60Ld5KcsHRQUmqfY1-ERInbhijhvIx0JJoiw2qF9-nfajunsJuWa-t4qScUQE_RrbyfRw2g0dqd1V5-Jb8fDEetcWdIzJYXwsj7hV8v5fHKeV43brrGq_L7SYBxLS01YfsiQQgDbdyk_cL2IqYMjuOMrfBW_vRH7bXfChD3AEyOMltSUhmqwY0ysv26BUol9x3muFoKzb5RKqh0VM_i78vCX6XvTD49JZOKMmwCUek0B)

## 3.2. Diagrama de Classes

![2006 - Adicionar um produto ao catálogo_CD](http://www.plantuml.com/plantuml/svg/hLJ1Rjim3BthAuYSMjW1qXvw27hfWWN8OK2nDX_0IOGZm3ODIMbN5FsO7VST_R59YHn7JXJqi9CDae_lyP6Gqwuck9tDxc0A3qfBpIqQs3RG6LPRnu0C4jtddveh5aBMQ2qynjoxA5Qj0ybALxp4NqPB5iMJCxgjOdJ13HcfiJx45LdvpT0B9H5VrzNaK1GW3Q6ZgwHhEUfM7hgKn-Xossg5YaelcIes6hzRoVN61w_5Hoz_qIV308dQH9BdHHvGwtth7wJyzmWPAOV2bQFcAAzL20pfHp9MMqUjf0MtZdwxK0o6EhRQULbahux4_gJALwE9dWU8uRec4r59zl-RLa15xc2OxNdo0jUhdJ35Jnj-JUlYW6zBbE6pgulBFhUT0P1M5k2z2pQWUMQ5YLjHH1vgyILXIxOwWfsZAIwBp2lBxZ5dJswYl4sNHygjAtkMgG0XyfSukPd0_NJRJNT_9mnpa1jiK9ov_8l8WQ7lS7f3QODJXWFtoM6CgKUTFT2RAyuj-cl8t6Lz3Tq_iPC6sxTanF4WS-ZYr7dG9MC3s-x96bcvBvt3vzzqb55TWCuXl733ivgh4Fy0)

## 3.3. Padrões Aplicados


| **Questão: Que classe...**       | **Resposta**                       | **Justificação**                                         |
|----------------------------------|------------------------------------|----------------------------------------------------------|
| ...interage com o utilizador?    | RegistarProdutoUI                  | Pure Fabrication                                         |
| ...coordena o UC?                | RegistarProdutoController          | Controller                                               |
| ...cria/instancia Produto?       | RegistarProdutoController          | Creator                                                  |
| ...persiste Produto?             | ProdutoRepository                  | Repository                                               |
| ...persiste Categoria?           | CategoriaRepository                | Repository                                               |
| ...persiste UnidadeMedida?       | ProdutoRepository                  | Repository                                               |
| ...cria CategoriaRepository?     | RepositoryFactory                  | Factory                                                  |
| ...cria ProdutoRepository?       | RepositoryFactory                  | Factory                                                  |


## 3.4. Testes
*Nesta secção deve sistematizar como os testes foram concebidos para permitir uma correta aferição da satisfação dos requisitos.*

**Teste 1:** Verificar que não é possível criar uma instância da classe Produto com código de fabrico nulo.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {  
      Produto instance = new Produto(null, "XXXX", "XXXX", "XXXX", "XXXX", "XXXX","XXXX);
   }`

**Teste 2:** Verificar que não é possível criar uma instância da classe Produto com código comercial nulo.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {  
      Produto instance = new Produto("XXXX", null, "XXXX", "XXXX", "XXXX", "XXXX", "XXXX");
   }`

**Teste 3:** Verificar que não é possível criar uma instância da classe Produto com descrição breve nula.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {  
      Produto instance = new Produto("XXXX", "XXXX", null, "XXXX", "XXXX", "XXXX", "XXXX");
   }`

**Teste 4:** Verificar que não é possível criar uma instância da classe Produto com descrição breve nula.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {  
      Produto instance = new Produto("XXXX", "XXXX", null, "XXXX", "XXXX", "XXXX", "XXXX");
   }`

**Teste 5:** Verificar que não é possível criar uma instância da classe Produto com descrição completa nula.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {  
      Produto instance = new Produto("XXXX", "XXXX", "XXXX", null, "XXXX", "XXXX", "XXXX");
   }`

**Teste 6:** Verificar que não é possível criar uma instância da classe Produto com um identificador de categoria nulo.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {  
      Produto instance = new Produto("XXXX", "XXXX", "XXXX", "XXXX",  null, "XXXX", "XXXX");
   }`

**Teste 7:** Verificar que não é possível criar uma instância da classe Produto com unidade nula.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {  
      Produto instance = new Produto("XXXX", "XXXX", "XXXX", "XXXX",  "XXXX", null, "XXXX");
   }`

**Teste 8:** Verificar que não é possível criar uma instância da classe Produto com um tipo de unidade nulo.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {
      Produto instance = new Produto("XXXX", "XXXX", "XXXX", "XXXX",  "XXXX", "XXXX", null);
   }`

**Teste 9:** Verificar que não é possível criar uma instância da classe Produto com todos os valores representativos de um Produto nulos.

`   @Test(expected = IllegalArgumentException.class)
      public void ensureNullIsNotAllowed() {  
      Produto instance = new Produto(null, null, null, null, null, null, null);
   }`

**Nota**: Considere-se que os mesmos testes foram prevenidos para Strings nulas.

# 4. Implementação

Implementámos a classe **ListCategoriasService**, de forma a evitar duplicação de código, uma vez que os métodos implementados na mesma podem ser usados por outras classes.

# 5. Integração/Demonstração

*Nesta secção a equipa deve descrever os esforços realizados no sentido de integrar a funcionalidade desenvolvida com as restantes funcionalidades do sistema.*

# 6. Observações

*Nesta secção sugere-se que a equipa apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.*