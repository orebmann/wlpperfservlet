//
// Diese Datei wurde generiert von JavaTM Architecture for XML Binding(JAXB) Reference Implementation Version 2.2.8-b130911.1802 
// Weitere Informationen: <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Alle Modifikationen an dieser Datei gehen bei erneuter Kompilierung des Quellenschemas verloren. 
// Generiert am: 2017.11.01 um 11:23:36 AM CET 
//


package com.ibm.wlp.perf.namespace;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Inhalt in dieser Klasse an.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="unit" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "TimeStatistic", namespace = "http://www.w3.org/namespace/")
public class TimeStatistic {

    @XmlValue
    protected String content;
    @XmlAttribute(name = "unit")
    protected String unit;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "ID")
    protected short id;    
    @XmlAttribute(name = "startTime")
    protected long startTime;
    @XmlAttribute(name = "lastSampleTime")
    protected long lastSampleTime;   
    @XmlAttribute(name = "min")
    protected long min=0;   
    @XmlAttribute(name = "max")
    protected long max=0;
    @XmlAttribute(name = "totalTime")
    protected long totalTime;  
   
/**
    <!ELEMENT TimeStatistic (#PCDATA)>
    <!ATTLIST TimeStatistic name CDATA #IMPLIED>
    <!ATTLIST TimeStatistic ID CDATA #IMPLIED>
    <!ATTLIST TimeStatistic totalTime CDATA #IMPLIED>
    <!ATTLIST TimeStatistic min CDATA #IMPLIED>
    <!ATTLIST TimeStatistic max CDATA #IMPLIED>
    <!ATTLIST TimeStatistic startTime CDATA #IMPLIED>
    <!ATTLIST TimeStatistic lastSampleTime CDATA #IMPLIED>
    <!ATTLIST TimeStatistic unit CDATA #IMPLIED>
**/
    
    public TimeStatistic() {
    	super();
    }
    public TimeStatistic(String name, short id, long totalTime, String unit, long sampleTime, long startTime) {
    	super();
    	this.name = name;
    	this.id = id;
    	this.totalTime = totalTime;
    	this.unit = unit;
    	this.lastSampleTime = sampleTime;
    	this.startTime = startTime;
    }
    
    public TimeStatistic(TimeStatistic stat) {
		// TODO Auto-generated constructor stub
    	super();
    	this.name = stat.name;
    	this.id = stat.id;
    	this.totalTime = stat.totalTime;
    	this.unit = stat.unit;
    	this.lastSampleTime = stat.lastSampleTime;
    	this.startTime = stat.startTime;
	}
    /**
     * Ruft den Wert der Eigenschaft content ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Legt den Wert der Eigenschaft content fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Ruft den Wert der Eigenschaft unit ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Legt den Wert der Eigenschaft unit fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public long getLastSampleTime() {
		return lastSampleTime;
	}
	public void setLastSampleTime(long lastSampleTime) {
		this.lastSampleTime = lastSampleTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}
