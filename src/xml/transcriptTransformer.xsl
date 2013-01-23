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
            <xsl:for-each select="ns1:studentTranscript[ns1:ssn='123456789']">
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
                <!--<xsl:variable name="sum">0</xsl:variable>-->
                
                <xsl:variable name="count" select="0"/>
                
            </xsl:for-each>
            <xsl:variable name="thesum" select="sum(ns1:studentTranscript[ns1:ssn='123456789']/ns1:courses/ns1:course/ns1:courseGrade)"/>
            <xsl:variable name="numberOfExams" select="count(ns1:studentTranscript[ns1:ssn='123456789']/ns1:courses/ns1:course/ns1:courseGrade)" />
            
            <xsl:variable name="gpa" select="$thesum div $numberOfExams" />    
             
            <xsl:element name="summmm"> 
                <xsl:value-of select="$thesum"/>
            </xsl:element>
            <xsl:element name="nofexams"> 
                <xsl:value-of select="$numberOfExams"/>
            </xsl:element>   
            <xsl:element name="GPA"> 
                <xsl:value-of select="$gpa"/>
            </xsl:element>
       
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
