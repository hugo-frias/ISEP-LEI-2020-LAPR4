# 1015 - Comunicações entre o simulador de máquina e o SCM protegidas (SSL/TLS)
==============================================================================

# 1. Requisitos

**Descrição**: Como **Gestor de Projeto**, eu pretendo que a equipa desenvolva uma aplicação que simule o funcionamento de uma máquina, nomeadamente no envio de mensagens geradas por estas. As comunicações entre o simulador de máquina e o SCM devem estar protegidas (SSL/TLS).

**Fluxo Principal**
* O Gestor de Projeto deve estar logado no sistema.
* O Gestor de Projeto deve introduzir os dados necessários para a realização da simulação (argumentos/parâmetros).
* O sistema irá posteriormente validar os dados introduzidos e proceder à execução, caso possível.

A interpretação feita deste requisito foi no sentido de respeitar as seguintes condições:

* Uma **máquina** não pode emitir duas mensagens no mesmo instante de tempo.
* Uma **máquina** só deve começar a enviar mensagens, após receber a verificação do **Servidor**.

**Regras de negócio**

Por fim, assume-se que as máquinas industriais são identificadas através de um número de identificação único (**unique identification number**), que corresponde a um número inteiro positivo entre **1** e **65535**.

Uma **Mensagem** enviada através de um socket possui a seguinte estrutura:

![Tabela1](https://i.ibb.co/ZVHwTfz/144843.png)

**Tipos de Códigos** de uma **Mensagem**.

![Tabela2](https://i.ibb.co/cT6hTtk/144943.png)

# 2. Análise

* A partir da análise do modelo de domínio atual, conclui-se que o mesmo satisfaz as condições exigidas pelo UC.

![Modelo_de_Dominio.svg](../Modelo_de_Dominio.svg)

**Questões em aberto**
* Qual a frequência deste UC?

# 3. Design

## 3.1. Realização da Funcionalidade

**Nota**: Este UC serve como uma camada de proteção para o as conexões entre o **Simulador** e o **SCM**. Então, pela minha análise não se mostrou necessário a realização de um **SD**.

## 3.2. Diagrama de Classes

**Nota**: Este UC serve como uma camada de proteção para o as conexões entre o **Simulador** e o **SCM**. Então, pela minha análise não se mostrou necessário a realização de um **SD**.

## 3.3. Padrões Aplicados

**Nota**: Este UC serve como uma camada de proteção para o as conexões entre o **Simulador** e o **SCM**. Então, pela minha análise não foram usados nenhum tipo de padrões.

## 3.4. Testes
*Nesta secção deve sistematizar como os testes foram concebidos para permitir uma correta aferição da satisfação dos requisitos.*

**Nota:** O grupo reconhece a falta de testes unitários para este UC. Todos os testes foram realizados de forma manual.

# 4. Implementação

Este Use Case foi inteiramente implementado em C.

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

**Certificados de TODOS os clientes conhecidos**

![Authentic](https://i.ibb.co/DpGTX7k/authentic-clients.png)

**Certificado do Servidor**

![ServerPem](https://i.ibb.co/nBP1cRx/Certificado-do-Servidor.png)

**Certificado do Cliente usado para testes**

![ClientePerm](https://i.ibb.co/S05MMsR/Cliente-Certificado.png)

**Chave do Cliente usada para testes**

![ClienteKey](https://i.ibb.co/9c9KPVJ/Cliente-Chave.png)

# 6. Observações

*Nesta secção sugere-se que a equipa apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.*

Considerando uma perspetiva critica da elaboração de UC, consideramos que o facto deste UC ser desenvolvido numa linguagem diferente (**C**) e ter que comunicar com outra linguagem (**Java)**, apresentou alguns obstáculos.

**Export the .crt**:

`keytool -export -alias mydomain -file mydomain.der -keystore mycert.jks`

**Convert the Cert to PEM**:

`openssl x509 -inform der -in mydomain.der -out certificate.pem`

**Export the key**:

`keytool -importkeystore -srckeystore mycert.jks -destkeystore keystore.p12 -deststoretype PKCS12`

**Concert PKCS12 key to unencrypted PEM**:

`openssl pkcs12 -in keystore.p12  -nodes -nocerts -out mydomain.key`

**Source**: [StackOverflow](https://serverfault.com/a/715841)