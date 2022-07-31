//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.07.30 at 01:56:40 PM PDT 
//


package org.smpte_ra.schemas._433._2008.dcmltypes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.smpte_ra.schemas._433._2008.dcmltypes package. 
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

    private final static QName _DeviceDescription_QNAME = new QName("http://www.smpte-ra.org/schemas/433/2008/dcmlTypes/", "DeviceDescription");
    private final static QName _DeviceIdentifierList_QNAME = new QName("http://www.smpte-ra.org/schemas/433/2008/dcmlTypes/", "DeviceIdentifierList");
    private final static QName _VersionInfoListTypeName_QNAME = new QName("http://www.smpte-ra.org/schemas/433/2008/dcmlTypes/", "Name");
    private final static QName _VersionInfoListTypeValue_QNAME = new QName("http://www.smpte-ra.org/schemas/433/2008/dcmlTypes/", "Value");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.smpte_ra.schemas._433._2008.dcmltypes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeviceDescriptionType }
     * 
     */
    public DeviceDescriptionType createDeviceDescriptionType() {
        return new DeviceDescriptionType();
    }

    /**
     * Create an instance of {@link DeviceIdentifierListType }
     * 
     */
    public DeviceIdentifierListType createDeviceIdentifierListType() {
        return new DeviceIdentifierListType();
    }

    /**
     * Create an instance of {@link UserTextType }
     * 
     */
    public UserTextType createUserTextType() {
        return new UserTextType();
    }

    /**
     * Create an instance of {@link TemperatureType }
     * 
     */
    public TemperatureType createTemperatureType() {
        return new TemperatureType();
    }

    /**
     * Create an instance of {@link NamedParmType }
     * 
     */
    public NamedParmType createNamedParmType() {
        return new NamedParmType();
    }

    /**
     * Create an instance of {@link CurrentType }
     * 
     */
    public CurrentType createCurrentType() {
        return new CurrentType();
    }

    /**
     * Create an instance of {@link ScopedTokenType }
     * 
     */
    public ScopedTokenType createScopedTokenType() {
        return new ScopedTokenType();
    }

    /**
     * Create an instance of {@link DurationType }
     * 
     */
    public DurationType createDurationType() {
        return new DurationType();
    }

    /**
     * Create an instance of {@link RateType }
     * 
     */
    public RateType createRateType() {
        return new RateType();
    }

    /**
     * Create an instance of {@link VoltageType }
     * 
     */
    public VoltageType createVoltageType() {
        return new VoltageType();
    }

    /**
     * Create an instance of {@link ParameterListType }
     * 
     */
    public ParameterListType createParameterListType() {
        return new ParameterListType();
    }

    /**
     * Create an instance of {@link VersionInfoListType }
     * 
     */
    public VersionInfoListType createVersionInfoListType() {
        return new VersionInfoListType();
    }

    /**
     * Create an instance of {@link DeviceIdentifierPolyType }
     * 
     */
    public DeviceIdentifierPolyType createDeviceIdentifierPolyType() {
        return new DeviceIdentifierPolyType();
    }

    /**
     * Create an instance of {@link DeviceTypeType }
     * 
     */
    public DeviceTypeType createDeviceTypeType() {
        return new DeviceTypeType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceDescriptionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.smpte-ra.org/schemas/433/2008/dcmlTypes/", name = "DeviceDescription")
    public JAXBElement<DeviceDescriptionType> createDeviceDescription(DeviceDescriptionType value) {
        return new JAXBElement<DeviceDescriptionType>(_DeviceDescription_QNAME, DeviceDescriptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceIdentifierListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.smpte-ra.org/schemas/433/2008/dcmlTypes/", name = "DeviceIdentifierList")
    public JAXBElement<DeviceIdentifierListType> createDeviceIdentifierList(DeviceIdentifierListType value) {
        return new JAXBElement<DeviceIdentifierListType>(_DeviceIdentifierList_QNAME, DeviceIdentifierListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.smpte-ra.org/schemas/433/2008/dcmlTypes/", name = "Name", scope = VersionInfoListType.class)
    public JAXBElement<String> createVersionInfoListTypeName(String value) {
        return new JAXBElement<String>(_VersionInfoListTypeName_QNAME, String.class, VersionInfoListType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.smpte-ra.org/schemas/433/2008/dcmlTypes/", name = "Value", scope = VersionInfoListType.class)
    public JAXBElement<String> createVersionInfoListTypeValue(String value) {
        return new JAXBElement<String>(_VersionInfoListTypeValue_QNAME, String.class, VersionInfoListType.class, value);
    }

}
