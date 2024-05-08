<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:tux="http://myGame/tux"
                xmlns="http://www.w3.org/1999/xhtml">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>dico.xsl</title>
            </head>
            <body>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th style="text-align:left">Mots</th>
                        <th style="text-align:left">Niveau</th>
                    </tr>
                    
                    <xsl:apply-templates select="//tux:mot">
                        <xsl:sort select="."/>
                    </xsl:apply-templates>
                   
                </table>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="tux:mot">
        <tr>
            <td>
                <xsl:value-of select="." />
            </td>
            <td>
                <xsl:value-of select="@niveau"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
