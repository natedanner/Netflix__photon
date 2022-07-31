//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.07.30 at 01:56:40 PM PDT 
//


package org.smpte_ra.schemas._433._2008.dcmltypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for currentUnitsToken.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="currentUnitsToken">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="amps"/>
 *     &lt;enumeration value="milliamps"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "currentUnitsToken")
@XmlEnum
public enum CurrentUnitsToken {

    @XmlEnumValue("amps")
    AMPS("amps"),
    @XmlEnumValue("milliamps")
    MILLIAMPS("milliamps");
    private final String value;

    CurrentUnitsToken(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CurrentUnitsToken fromValue(String v) {
        for (CurrentUnitsToken c: CurrentUnitsToken.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
