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
@XmlRootElement(name = "RangeStatistic", namespace = "http://www.w3.org/namespace/")
public class RangeStatistic {

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
    @XmlAttribute(name = "mean")
    protected double mean=0.0;
    @XmlAttribute(name = "integral")
    protected double integral=0.0;  
    @XmlAttribute(name = "value")
    protected long value;
    public RangeStatistic() {
    	super();
    }
    

    public RangeStatistic(String name, short id, long value, String unit, long sampleTime, long startTime) {
    	super();
    	this.name = name;
    	this.id = id;
    	this.value = value;
    	this.unit = unit;
    	this.lastSampleTime = sampleTime;
    	this.startTime = startTime;
    }
    
    public RangeStatistic(RangeStatistic stat) {
		// TODO Auto-generated constructor stub
    	super();
    	this.name = stat.name;
    	this.id = stat.id;
    	this.value = stat.value;
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

	public long getLastSampleTime() {
		return lastSampleTime;
	}

	public void setLastSampleTime(long lastSampleTime) {
		this.lastSampleTime = lastSampleTime;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getIntegral() {
		return integral;
	}

	public void setIntegral(double integral) {
		this.integral = integral;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

}
