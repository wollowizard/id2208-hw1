<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : transformerXSLT.xsl
    Created on : 23 de enero de 2013, 13:58
    Author     : Gerard
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:cv='http://www.shortcvns.net'
                xmlns:ns2="http://www.companyInfoSchema.net"
                xmlns="http://www.applicantProfile.com">
    <xsl:output method="xml" indent="yes" standalone="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    <xsl:template match="/">       
        <xsl:element name="applicantProfile">
            <xsl:element name="personalInformation">
                <xsl:element name="firstname">
                    <xsl:value-of select="document('shortCv.xml')/cv:person/cv:firstname"/>
                </xsl:element>
                <xsl:element name="lastname">
                    <xsl:value-of select="document('shortCv.xml')/cv:person/cv:lastname"/>
                </xsl:element>
                <xsl:element name="ssn">
                    <xsl:value-of select="document('shortCv.xml')/cv:person/cv:ssn"/>
                </xsl:element>
            </xsl:element>
            
            <xsl:element name="studiesInformation">
                <xsl:copy-of select="document('transcriptOutput.xml')/studentTranscript/*"/>
            </xsl:element>
            
            <xsl:element name="workExperiences">
                <xsl:for-each select="document('employmentOutput.xml')/employments/workExperiences/workExperience">
                    <xsl:element name="workExperience">
                        <xsl:element name="companyCode">
                            <xsl:value-of select="companyCode"/>
                        </xsl:element>
                        <xsl:variable name="mycompanyCode" select="companyCode"/>
                        <xsl:element name="companyName">
                            <xsl:value-of select="companyName"/>
                        </xsl:element>
                        <xsl:element name="fromDate">
                            <xsl:value-of select="fromDate"/>
                        </xsl:element>
                        <xsl:element name="upToDate">
                            <xsl:value-of select="upToDate"/>
                        </xsl:element>
                        <xsl:element name="information">
                            <xsl:element name="telefonNumber">
                                <xsl:value-of select="document('companyInfoOutput.xml')/local/ns2:companyInfo[ns2:companyCode=$mycompanyCode]/ns2:telefonNumber"/>
                            </xsl:element>
                        </xsl:element> 
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
