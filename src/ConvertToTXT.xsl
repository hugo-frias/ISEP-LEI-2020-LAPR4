<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" omit-xml-declaration="yes"/>
	
	<xsl:template match="/">	
		
		<!--Categorias -->
		<xsl:value-of select="concat('- Categorias','&#xa;')"/>
		<xsl:value-of select="concat('Código Interno,Descrição','&#xa;','&#xa;')"/>
		<xsl:apply-templates select="Instancias/Categoria"/>
		
		<!--Depósitos -->
		<xsl:value-of select="concat('&#xa;','- Depósitos','&#xa;')"/>
		<xsl:value-of select="concat('Código Interno,Descrição','&#xa;','&#xa;')"/>
		<xsl:apply-templates select="Instancias/Deposito"/>
		
		<!--Linha de Produção -->
		<xsl:value-of select="concat('&#xa;','- Linhas de Produção','&#xa;')"/>
		<xsl:value-of select="concat('Identificador da Linha de Produção','&#xa;','&#xa;')"/>
		<xsl:apply-templates select="Instancias/LinhaProdução"/>
		
		<!--Máquinas -->
		<xsl:value-of select="concat('&#xa;','- Máquinas','&#xa;')"/>
		<xsl:value-of select="concat('Código Interno, Descrição, Marca, Modelo, Número de Série, Versão','&#xa;','&#xa;')"/>
		<xsl:apply-templates select="Instancias/Maquina"/>
		
		<!--Matéria Prima -->
		<xsl:value-of select="concat('&#xa;','- Matérias Primas','&#xa;')"/>
		<xsl:value-of select="concat('Código Interno, Descrição, Nome da Ficha Técnica','&#xa;','&#xa;')"/>
		<xsl:apply-templates select="Instancias/MateriaPrima"/>
		
		<!--Produto -->
		<xsl:value-of select="concat('&#xa;','- Produtos','&#xa;')"/>
		<xsl:value-of select="concat('Código da Categoria, Código Comercial, Código Fabrico, Descrição Breve, Descrição Completa, Unidade de Medida','&#xa;','&#xa;')"/>
		<xsl:apply-templates select="Instancias/Produto"/>
		
		<!--Ordem de Produção -->
		<xsl:value-of select="concat('&#xa;','- Ordens de Produção','&#xa;')"/>
		<xsl:value-of select="concat('Identificador da Ordem, Data Prevista de Execução, Data de Emissão, Quantidade Pretendida, Quantidade Produzida, Código Comercial, Código Fabrico, Código da Categoria','&#xa;','&#xa;')"/>
		<xsl:apply-templates select="Instancias/OrdemProducao"/>
		
		<!--Encomenda -->
		<xsl:value-of select="concat('&#xa;','- Encomendas','&#xa;')"/>
		<xsl:value-of select="concat('Identificador da Encomenda, Identificador da Ordem, Data Prevista de Execução, Data de Emissão, Quantidade Pretendida, Quantidade Produzida, Código Comercial, Código Fabrico, Código da Categoria','&#xa;','&#xa;')"/>
		<xsl:apply-templates select="Instancias/Encomenda"/>
	</xsl:template>
	
	<!--Categorias -->
	<xsl:template match="Categoria">
		<xsl:value-of select="concat(codigo,',',descricao,'&#xa;')"/>
	</xsl:template>
	
	<!--Depósitos -->
	<xsl:template match="Deposito">
		<xsl:value-of select="concat(codigoDeposito,',',descricaoDeposito,'&#xa;')"/>
	</xsl:template>
	
	<!--Linha de Produção -->
	<xsl:template match="LinhaProdução">
		<xsl:value-of select="concat(idLinhaProducao,'&#xa;')"/>
	</xsl:template>
	
	<!--Máquinas -->
	<xsl:template match="Maquina">
		<xsl:value-of select="concat(codigoInterno,',',descricao,',',marca,',',modelo,',',numSerie,',',version,'&#xa;')"/>
	</xsl:template>
	
	<!--Matérias Primas -->
	<xsl:template match="MateriaPrima">
		<xsl:value-of select="concat(codigoInterno,',',descricao,',',nomeFichaTecnica,'&#xa;')"/>
	</xsl:template>
	
	<!--Produto -->
	<xsl:template match="Produto">
		<xsl:value-of select="concat(categoria/codigo,',',codigoComercial,',',codigoFabrico,',',descricaoBreve,',',
		descricaoCompleta,',',unidadeMedidaId,'&#xa;')"/>
	</xsl:template>
	
	<!--Ordem de Produção -->
	<xsl:template match="OrdemProducao">
		<xsl:value-of select="concat(idOrdemProducao,',',dataPrevistaExecucao,',',datadeEmissao,',',
		execucaoOrdemProducao/quantidadePretendida,',',execucaoOrdemProducao/quantidadeProduzida,',',
		produto/codigoComercial,',',produto/codigoFabrico,',',produto/categoria/codigo,'&#xa;')"/>
	</xsl:template>
	
	<!--Encomendas -->
	<xsl:template match="Encomenda">
		<xsl:value-of select="concat(idEncomenda,',',ordemProducao/idOrdemProducao,',',ordemProducao/dataPrevistaExecucao,',',
		ordemProducao/datadeEmissao,',',ordemProducao/execucaoOrdemProducao/quantidadePretendida,',',ordemProducao/execucaoOrdemProducao/quantidadeProduzida,',',
		ordemProducao/produto/codigoComercial,',',ordemProducao/produto/codigoFabrico,',',ordemProducao/produto/categoria/codigo,'&#xa;')"/>
	</xsl:template>
</xsl:stylesheet>