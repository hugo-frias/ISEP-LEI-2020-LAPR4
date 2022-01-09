<xsl:stylesheet xmlns:xsl ="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" omit-xml-declaration="yes" version="1.0" encoding="UTF-8" indent="no"/>

<xsl:template match ="/">
	<html>
		<style>
		<!--Codigo para por uma imagem no background-->
		body {
			background-image: url('teamokapi.png');
			background-repeat: no-repeat;
			background-attachment: fixed;
			background-size: 100% 100%;
		}
		</style>
		<link rel="stylesheet" href="style.css"></link>
		
		<body>
			<!--Titulo da Tabela-->
			<H1 style="color:white;">Categorias</H1>
			<table border="1">
				<tr bgcolor="orange">
					<!--Titulo das colunas da Tabela -->
					<th style="color:black;">Código Interno</th>
					<th style="color:black;">Descrição</th>
				</tr>
				<!--Quando da match ao node usa o template para expor os dados -->
				<xsl:apply-templates select="Instancias/Categoria"/>
			</table>
			<!--Titulo da Tabela-->
			<H1 style="color:white;">Depósitos</H1>
			<table border="1">
				<tr bgcolor="orange">
					<!--Titulo das colunas da Tabela -->
					<th style="color:black;">Código Interno</th>
					<th style="color:black;">Descrição</th>
				</tr>
				<!--Quando da match ao node usa o template para expor os dados -->
				<xsl:apply-templates select="Instancias/Deposito"/>
			</table>
			<!--Titulo da Tabela-->
			<H1 style="color:white;">Linhas de Produção</H1>
			<table border="1">
				<tr bgcolor="orange">
					<!--Titulo das colunas da Tabela -->
					<th style="color:black;">Identificador</th>
				</tr>
				<!--Quando da match ao node usa o template para expor os dados -->
				<xsl:apply-templates select="Instancias/LinhaProdução"/>
			</table>
			<!--Titulo da Tabela-->
			<H1 style="color:white;">Máquinas</H1>
			<table border="1">
				<tr bgcolor="orange">
					<!--Titulo das colunas da Tabela -->
					<th style="color:black;">Id da Linha de Produção</th>
					<th style="color:black;">Código Interno</th>
					<th style="color:black;">Descrição</th>
					<th style="color:black;">Marca</th>
					<th style="color:black;">Modelo</th>
					<th style="color:black;">Número de Série</th>
					<th style="color:black;">Versão</th>
				</tr>
				<!--Quando da match ao node usa o template para expor os dados -->
				<xsl:apply-templates select="Instancias/Maquina"/>
			</table>
			<!--Titulo da Tabela-->
			<H1 style="color:white;">Matérias-Primas</H1>
			<table border="1">
				<tr bgcolor="orange">
					<!--Titulo das colunas da Tabela -->
					<th style="color:black;">Código Interno</th>
					<th style="color:black;">Descrição</th>
					<th style="color:black;">Nome Ficha Técnica</th>
				</tr>
				<!--Quando da match ao node usa o template para expor os dados -->
				<xsl:apply-templates select="Instancias/MateriaPrima"/>
			</table>
			<!--Titulo da Tabela-->
			<H1 style="color:white;">Produtos</H1>
			<table border="1">
				<tr bgcolor="orange">
					<!--Titulo das colunas da Tabela -->
					<th style="color:black;">Categoria ID</th>
					<th style="color:black;">Código Comercial</th>
					<th style="color:black;">Código de Fabrico</th>
					<th style="color:black;">Descrição Breve</th>
					<th style="color:black;">Descrição Completa </th>
					<th style="color:black;">Unidade de Medida</th>
				</tr>
				<!--Quando da match ao node usa o template para expor os dados -->
				<xsl:apply-templates select="Instancias/Produto"/>
			</table>
			<!--Titulo da Tabela-->
			<H1 style="color:white;">Ordens de Produção</H1>
			<table border="1">
				<tr bgcolor="orange">
					<!--Titulo das colunas da Tabela -->
					<th style="color:black;">ID da Ordem de Produção</th>
					<th style="color:black;">Data Prevista de Execução</th>
					<th style="color:black;">Data de Emissão</th>
					<th style="color:black;">Quantidade Pretendida</th>
					<th style="color:black;">Quantidade Produzida</th>
					<th style="color:black;">Código Comercial do Produto</th>
					<th style="color:black;">Código Fabrico do Produto</th>
					<th style="color:black;">Categoria do Produto</th>
				</tr>
				<!--Quando da match ao node usa o template para expor os dados -->
				<xsl:apply-templates select="Instancias/OrdemProducao"/>
			</table>
			<!--Titulo da Tabela-->
			<H1 style="color:white;">Encomenda</H1>
			<table border="1">
				<tr bgcolor="orange">
					<!--Titulo das colunas da Tabela -->
					<th style="color:black;">ID da Encomenda</th>
					<th style="color:black;">ID da Ordem de Produção</th>
					<th style="color:black;">Data Prevista de Execução</th>
					<th style="color:black;">Data de Emissão</th>
					<th style="color:black;">Quantidade Pretendida</th>
					<th style="color:black;">Quantidade Produzida</th>
					<th style="color:black;">Código Comercial do Produto</th>
					<th style="color:black;">Código Fabrico do Produto</th>
					<th style="color:black;">Categoria do Produto</th>
				</tr>
				<!--Quando da match ao node usa o template para expor os dados -->
				<xsl:apply-templates select="Instancias/Encomenda"/>
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="Categoria">
	<tr>
		<td bgcolor="ffffff">
			<xsl:value-of select="codigo"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="descricao"/>
		</td>
	</tr>
</xsl:template>

<xsl:template match="Deposito">
	<tr>
		<td bgcolor="ffffff">
			<xsl:value-of select="codigoDeposito"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="descricaoDeposito"/>
		</td>
	</tr>
</xsl:template>

<xsl:template match="Maquina">
	<tr>
		<td bgcolor="ffffff">
			<xsl:value-of select="/Instancias/LinhaProdução/idLinhaProducao"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="codigoInterno"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="descricao"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="marca"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="modelo"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="numSerie"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="version"/>
		</td>
	</tr>
</xsl:template>
<xsl:template match="MateriaPrima">
	<tr>
		<td bgcolor="ffffff">
			<xsl:value-of select="codigoInterno"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="descricao"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="nomeFichaTecnica"/>
		</td>
		
	</tr>
</xsl:template>
<xsl:template match="LinhaProdução">
	<tr>
		<td bgcolor="ffffff">
			<xsl:value-of select="idLinhaProducao"/>
		</td>		
	</tr>
</xsl:template>

<xsl:template match="Produto">
	<tr>
		<td bgcolor="ffffff">
			<xsl:value-of select="categoria/codigo"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="codigoComercial"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="codigoFabrico"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="descricaoBreve"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="descricaoCompleta"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="unidadeMedidaId"/>
		</td>
	</tr>
</xsl:template>
<xsl:template match="OrdemProducao">
	<tr>
		<td bgcolor="ffffff">
			<xsl:value-of select="idOrdemProducao"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="dataPrevistaExecucao"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="datadeEmissao"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="execucaoOrdemProducao/quantidadePretendida"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="execucaoOrdemProducao/quantidadeProduzida"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="produto/codigoComercial"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="produto/codigoFabrico"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="produto/categoria/codigo"/>
		</td>
	</tr>
</xsl:template>
<xsl:template match="Encomenda">
	<tr>
		<td bgcolor="ffffff">
			<xsl:value-of select="idEncomenda"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="ordemProducao/idOrdemProducao"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="ordemProducao/dataPrevistaExecucao"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="ordemProducao/datadeEmissao"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="ordemProducao/execucaoOrdemProducao/quantidadePretendida"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="ordemProducao/execucaoOrdemProducao/quantidadeProduzida"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="ordemProducao/produto/codigoComercial"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="ordemProducao/produto/codigoFabrico"/>
		</td>
		<td bgcolor="ffffff">
			<xsl:value-of select="ordemProducao/produto/categoria/codigo"/>
		</td>
	</tr>
</xsl:template>


</xsl:stylesheet>