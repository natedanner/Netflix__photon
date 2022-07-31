//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.07.30 at 01:56:40 PM PDT 
//


package org.smpte_ra.schemas._2067_101._2014.scale_macro;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.smpte_ra.schemas._2067_101._2014.scale_macro package. 
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

    private final static QName _BoundaryCondition_QNAME = new QName("http://www.smpte-ra.org/schemas/2067-101/2014/scale-macro", "BoundaryCondition");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.smpte_ra.schemas._2067_101._2014.scale_macro
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ImageScaleMacroType }
     * 
     */
    public ImageScaleMacroType createImageScaleMacroType() {
        return new ImageScaleMacroType();
    }

    /**
     * Create an instance of {@link ImageScaleMacroType.InputImageSequence }
     * 
     */
    public ImageScaleMacroType.InputImageSequence createImageScaleMacroTypeInputImageSequence() {
        return new ImageScaleMacroType.InputImageSequence();
    }

    /**
     * Create an instance of {@link ImageScaleMacroType.OutputImageSequence }
     * 
     */
    public ImageScaleMacroType.OutputImageSequence createImageScaleMacroTypeOutputImageSequence() {
        return new ImageScaleMacroType.OutputImageSequence();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.smpte-ra.org/schemas/2067-101/2014/scale-macro", name = "BoundaryCondition")
    public JAXBElement<String> createBoundaryCondition(String value) {
        return new JAXBElement<String>(_BoundaryCondition_QNAME, String.class, null, value);
    }

}
