<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : transformerXSLT.xsl
    Created on : 23 de enero de 2013, 13:58
    Author     : Gerard
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
        xmlns="http://www.applicantProfile.com">
    <xsl:output method="xml" indent="yes" standalone="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    <xsl:template match="/">
        
        <xsl:element name="applicantProfile">
            
            <xsl:element name="personalInformation">
                <xsl:copy-of select="document('shortCv.xml')/person/*"/>
            </xsl:element>
            
            <xsl:element name="studiesInformation">
                <xsl:copy-of select="document('transcription.xml')/transcript/*"/>
            </xsl:element>
            
            <xsl:element name="workExperiences">
                <xsl:copy-of select="document('employmentOutput.xml')/employments/workExperiences/*" />
            </xsl:element>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
