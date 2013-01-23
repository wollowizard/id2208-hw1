<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : transformerXSLT.xsl
    Created on : 23 de enero de 2013, 13:58
    Author     : Gerard
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns1='http://www.transcriptSchema.net'>
    <xsl:output method="xml" indent="yes" standalone="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    <xsl:template match="ns1:transcriptList">
        
        <xsl:element name="studentTranscript">
            <xsl:element name="name"> 
                <xsl:value-of select="ns1:studentTranscript/ns1:name"/>
            </xsl:element>

            <xsl:for-each select="ns1:studentTranscript[ns1:ssn='123456788']">
                <xsl:element name="university"> 
                    <xsl:value-of select="ns1:university"/>
                </xsl:element>
                
                <xsl:element name="degree"> 
                <xsl:value-of select="ns1:degree"/>
                </xsl:element>
                
                <xsl:element name="year"> 
                    <xsl:value-of select="ns1:year"/>
                </xsl:element>
                
                <xsl:variable name="gpa" select="0"/>
                <xsl:variable name="suma">0</xsl:variable>
                
                <xsl:variable name="count" select="0"/>
                
                <xsl:for-each select="ns1:courses/ns1:course">
                    <xsl:element name="year"> 
                        <xsl:element name="greadeA"/>
                        <xsl:value-of select="ns1:courseGrade"/>
                    </xsl:element>
                    <xsl:if test="ns1:courseGrade = A">
                        <xsl:variable name="gpa" select="$gpa + 4"/>
                        <xsl:variable name="sum"> 
                            <xsl:value-of select="$sum+4"/>
                        </xsl:variable>
                    </xsl:if>
                    <xsl:if test="ns1:courseGrade = B">
                        <xsl:variable name="gpa" select="$gpa + 3"/>
                    </xsl:if>
                    <xsl:if test="ns1:courseGrade = C">
                        <xsl:variable name="gpa" select="$gpa + 2"/>
                    </xsl:if>
                    <xsl:if test="ns1:courseGrade = D">
                        <xsl:variable name="gpa" select="$gpa + 1"/>
                    </xsl:if>
                    <xsl:if test="ns1:courseGrade = E">
                        <xsl:variable name="gpa" select="$gpa + 0"/>
                    </xsl:if>
                    
                </xsl:for-each>
                <xsl:variable name="suma"> 
                            <xsl:value-of select="$suma+4"/>
                </xsl:variable>
                
                <xsl:element name="GPA"> 
                    <xsl:value-of select="$suma"/>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
