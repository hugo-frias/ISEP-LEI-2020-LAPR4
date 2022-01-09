<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
	<map xmlns="http://www.w3.org/2005/xpath-functions"/>
	
    <xsl:template match="/">
	
    <map key='Instancias'>
      <array key="Categoria">
        <map>
            <entry key="Codigo"><xsl:value-of select="/Instancias/Categoria/codigo"/></entry>
            <string key="Descricao"><xsl:value-of select="/Instancias/Categoria/descricao"/></string>
       	</map> 
     </array>
     </map> 
	   
</xsl:template>
    
</xsl:stylesheet>