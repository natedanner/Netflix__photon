//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.07.30 at 01:56:40 PM PDT 
//


package org.smpte_ra.schemas._2067_100._2014;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.smpte_ra.schemas._2067_100._2014 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OutputProfileList_QNAME = new QName("http://www.smpte-ra.org/schemas/2067-100/2014", "OutputProfileList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.smpte_ra.schemas._2067_100._2014
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OutputProfileListType }
     * 
     */
    public OutputProfileListType createOutputProfileListType() {
        return new OutputProfileListType();
    }

    /**
     * Create an instance of {@link OutputProfileListType.AliasList }
     * 
     */
    public OutputProfileListType.AliasList createOutputProfileListTypeAliasList() {
        return new OutputProfileListType.AliasList();
    }

    /**
     * Create an instance of {@link PresetMacroType }
     * 
     */
    public PresetMacroType createPresetMacroType() {
        return new PresetMacroType();
    }

    /**
     * Create an instance of {@link OutputProfileListType.ExtensionProperties }
     * 
     */
    public OutputProfileListType.ExtensionProperties createOutputProfileListTypeExtensionProperties() {
        return new OutputProfileListType.ExtensionProperties();
    }

    /**
     * Create an instance of {@link OutputProfileListType.MacroList }
     * 
     */
    public OutputProfileListType.MacroList createOutputProfileListTypeMacroList() {
        return new OutputProfileListType.MacroList();
    }

    /**
     * Create an instance of {@link OutputProfileListType.AliasList.Alias }
     * 
     */
    public OutputProfileListType.AliasList.Alias createOutputProfileListTypeAliasListAlias() {
        return new OutputProfileListType.AliasList.Alias();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutputProfileListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.smpte-ra.org/schemas/2067-100/2014", name = "OutputProfileList")
    public JAXBElement<OutputProfileListType> createOutputProfileList(OutputProfileListType value) {
        return new JAXBElement<OutputProfileListType>(_OutputProfileList_QNAME, OutputProfileListType.class, null, value);
    }

}
