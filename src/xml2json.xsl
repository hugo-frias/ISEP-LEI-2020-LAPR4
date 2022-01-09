<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
	<map xmlns="http://www.w3.org/2005/xpath-functions"/>
	
    <xsl:template match="/">
	
"Depositos":{
	"Deposito":[
		<xsl:apply-templates select="Instancias/Deposito"/>
	]}
	
"Categorias":{
	"Categoria":[
		<xsl:apply-templates select="Instancias/Categoria"/>
	]}
	
"Matérias-Primas":{
	"Matéria-Prima":[
		<xsl:apply-templates select="Instancias/MateriaPrima"/>
	]}
	
"Produtos":{
	"Produto":[
		<xsl:apply-templates select="Instancias/Produto"/>
	]}

"Ordens de Produção":{
	"Ordem de Produção":[
		<xsl:apply-templates select="Instancias/OrdemProducao"/>
	]}

"Encomendas":{
	"Encomenda":[
		<xsl:apply-templates select="Instancias/Encomenda"/>
	]}
	
"Linhas de Produção":{
	"Linha de Produção":[
		<xsl:apply-templates select="Instancias/LinhaProdução"/>
	]}
	
"Máquinas":{
	"Máquina":[
		<xsl:apply-templates select="Instancias/Maquina"/>
	]}



</xsl:template>

<xsl:template match="Deposito">
	<xsl:value-of select="concat('{','&#xa;','&#x9;','&#x9;','(','&quot;Código Depósito&quot;',':',codigoDeposito,',','&#xa;','&#x9;','&#x9;','&quot;Descrição Depósito&quot;',':',descricaoDeposito,')','&#xa;')"/>
	<xsl:if test="position()!=last()">
		<xsl:text>&#x9;&#x9;},</xsl:text>
	</xsl:if>
	<xsl:if test="position()=last()">
		<xsl:text>&#x9;&#x9;}</xsl:text>
	</xsl:if>
		<xsl:text>&#xa;</xsl:text>
	<!--<xsl:call-template name="putLast"/>-->
</xsl:template>
	
<xsl:template match="Categoria">
	<xsl:value-of select="concat('{','&#xa;','&#x9;','&#x9;','(','&quot;Código Interno&quot;',':',codigo,',','&#xa;','&#x9;','&#x9;','&quot;Descrição&quot;',':',descricao,')','&#xa;')"/>
	<xsl:if test="position()!=last()">
		<xsl:text>&#x9;&#x9;},</xsl:text>
	</xsl:if>
	<xsl:if test="position()=last()">
		<xsl:text>&#x9;&#x9;}</xsl:text>
	</xsl:if>
		<xsl:text>&#xa;</xsl:text>
	<!--<xsl:call-template name="putLast"/>-->
</xsl:template>

<xsl:template match="LinhaProdução">
	<xsl:value-of select="concat('{','&#xa;','&#x9;','&#x9;','(','&quot;Identificador da Linha de Produção&quot;',':',idLinhaProducao,')','&#xa;')"/>
	<xsl:if test="position()!=last()">
		<xsl:text>&#x9;&#x9;},</xsl:text>
	</xsl:if>
	<xsl:if test="position()=last()">
		<xsl:text>&#x9;&#x9;}</xsl:text>
	</xsl:if>
		<xsl:text>&#xa;</xsl:text>
</xsl:template>

<xsl:template match="Maquina">
	<xsl:value-of select="concat('{','&#xa;','&#x9;','&#x9;','(','&quot;Código Interno&quot;',':',codigoInterno,',','&#xa;','&#x9;','&#x9;','&quot;Descrição&quot;',':',descricao,',','&#xa;','&#x9;','&#x9;','&quot;Marca&quot;',':',marca,',','&#xa;','&#x9;','&#x9;','&quot;Modelo&quot;',':',modelo,',','&#xa;','&#x9;','&#x9;','&quot;Número de Série&quot;',':',numSerie,')','&#xa;')"/>
	<xsl:if test="position()!=last()">
		<xsl:text>&#x9;&#x9;},</xsl:text>
	</xsl:if>
	<xsl:if test="position()=last()">
		<xsl:text>&#x9;&#x9;}</xsl:text>
	</xsl:if>
		<xsl:text>&#xa;</xsl:text>
	<!--<xsl:call-template name="putLast"/>-->
</xsl:template>

<xsl:template match="MateriaPrima">
	<xsl:value-of select="concat('{','&#xa;','&#x9;','&#x9;','(','&quot;Código Interno&quot;',':',codigoInterno,',','&#xa;','&#x9;','&#x9;','&quot;Descrição&quot;',':',descricao,',','&#xa;','&#x9;','&#x9;','&quot;Nome Ficha Técnica&quot;',':',nomeFichaTecnica,')','&#xa;')"/>
	<xsl:if test="position()!=last()">
		<xsl:text>&#x9;&#x9;},</xsl:text>
	</xsl:if>
	<xsl:if test="position()=last()">
		<xsl:text>&#x9;&#x9;}</xsl:text>
	</xsl:if>
		<xsl:text>&#xa;</xsl:text>
	<!--<xsl:call-template name="putLast"/>-->
</xsl:template>	

<xsl:template match="Produto">
	<xsl:value-of select="concat('{','&#xa;','&#x9;','&#x9;','(','&quot;Código da Categoria&quot;',':',categoria/codigo,',','&#xa;','&#x9;','&#x9;','&quot;Código Comercial&quot;',':',codigoComercial,',','&#xa;','&#x9;','&#x9;','&quot;Código Fabrico&quot;',':',codigoFabrico,',','&#xa;','&#x9;','&#x9;','&quot;Descrição Breve&quot;',':',descricaoBreve,',','&#xa;','&#x9;','&#x9;','&quot;Descrição Completa&quot;',':',descricaoCompleta,',','&#xa;','&#x9;','&#x9;','&quot;Unidade de Medida&quot;',':',unidadeMedidaId,')','&#xa;')"/>
	<xsl:if test="position()!=last()">
		<xsl:text>&#x9;&#x9;},</xsl:text>
	</xsl:if>
	<xsl:if test="position()=last()">
		<xsl:text>&#x9;&#x9;}</xsl:text>
	</xsl:if>
		<xsl:text>&#xa;</xsl:text>
	<!--<xsl:call-template name="putLast"/>-->
</xsl:template>	

<xsl:template match="OrdemProducao">
	<xsl:value-of select="concat('{','&#xa;','&#x9;','&#x9;','(','&quot;Código da Ordem de Produção&quot;',':',idOrdemProducao,',','&#xa;','&#x9;','&#x9;','&quot;Código Comercial do Produto &quot;',':',produto/codigoComercial,',','&#xa;','&#x9;','&#x9;','&quot;Código Fabrico do Produto&quot;',':',produto/codigoFabrico,',','&#xa;','&#x9;','&#x9;','&quot;Código da Categoria do Produto&quot;',':',produto/categoria/codigo,',','&#xa;','&#x9;','&#x9;','&quot;Quantidade Pretendida&quot;',':',execucaoOrdemProducao/quantidadePretendida,',','&#xa;','&#x9;','&#x9;','&quot;Quantidade Pretendida&quot;',':',execucaoOrdemProducao/quantidadeProduzida,')','&#xa;')"/>
	<xsl:if test="position()!=last()">
		<xsl:text>&#x9;&#x9;},</xsl:text>
	</xsl:if>
	<xsl:if test="position()=last()">
		<xsl:text>&#x9;&#x9;}</xsl:text>
	</xsl:if>
		<xsl:text>&#xa;</xsl:text>
	<!--<xsl:call-template name="putLast"/>-->
</xsl:template>	

<xsl:template match="Encomenda">
	<xsl:value-of select="concat('{','&#xa;','&#x9;','&#x9;','(','&quot;Identificador da Encomenda&quot;',':',idEncomenda,',','&#xa;','&#x9;','&#x9;','&quot;Código da Ordem de Produção&quot;',':',ordemProducao/idOrdemProducao,',','&#xa;','&#x9;','&#x9;','&quot;Código Comercial do Produto&quot;',':',ordemProducao/produto/codigoComercial,',','&#xa;','&#x9;','&#x9;','&quot;Código Fabrico do Produto&quot;',':',ordemProducao/produto/codigoFabrico,',','&#xa;','&#x9;','&#x9;','&quot;Código da Categoria do Produto&quot;',':',ordemProducao/produto/categoria/codigo,',','&#xa;','&#x9;','&#x9;','&quot;Quantidade Pretendida&quot;',':',ordemProducao/execucaoOrdemProducao/quantidadePretendida,',','&#xa;','&#x9;','&#x9;','&quot;Quantidade Pretendida&quot;',':',ordemProducao/execucaoOrdemProducao/quantidadeProduzida,')','&#xa;')"/>
	<xsl:if test="position()!=last()">
		<xsl:text>&#x9;&#x9;},</xsl:text>
	</xsl:if>
	<xsl:if test="position()=last()">
		<xsl:text>&#x9;&#x9;}</xsl:text>
	</xsl:if>
		<xsl:text>&#xa;</xsl:text>
	<!--<xsl:call-template name="putLast"/>-->
</xsl:template>	

<!--Não conseguimos usar o template para poupar os ifs 
<xsl:template name = "putLast">
	<xsl:if test="position()!=last()">
		<xsl:text>&#x9;&#x9;},</xsl:text>
	</xsl:if>
	<xsl:if test="position()=last()">
		<xsl:text>&#x9;&#x9;}</xsl:text>
	</xsl:if>
		<xsl:text>&#xa;</xsl:text>
</xsl:template>-->




</xsl:stylesheet>