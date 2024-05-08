<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:tux="http://myGame/tux"
                xmlns="http://www.w3.org/1999/xhtml">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Profil</title>
                <link href="./profil.css" rel="stylesheet" type="text/css"/>
            </head>
            <body>
                <div>
                    <table id="tableId">
                        <tr>
                            <td>
                                <img src="{//tux:avatar}" alt="Avatar"/>
                                <br/>
                                <br/>
                            </td>
                            <td id="nomP">
                                <b>
                                    <xsl:value-of select="//tux:nom"/>
                                </b>
                                <br/>
                                <br/>
                            </td>
                        </tr>
                    </table>

                    <b id="pj"> Parties jouées : </b>
                    <br/>
                    <br/>
                    <table id="tableStats">
                        <tr> 
                            <th> 
                                Date : 
                            </th>
                            <th> 
                                Temps :
                            </th>
                            <th> 
                                Niveau :
                            </th>
                            <th> 
                                Mot :
                            </th>
                        </tr>
                        <xsl:apply-templates select="//tux:partie"/>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="tux:partie">
        <tr>
            <td>
                <xsl:value-of select="@date"/>
            </td>
            <td>
                <xsl:value-of select="tux:temps"/>
            </td>
            <td>
                <xsl:value-of select="tux:mot/@niveau"/>
            </td>
            <td>
                <xsl:value-of select="tux:mot"/>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
