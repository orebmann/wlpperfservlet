//
// Diese Datei wurde generiert von JavaTM Architecture for XML Binding(JAXB) Reference Implementation Version 2.2.8-b130911.1802 
// Weitere Informationen: <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Alle Modifikationen an dieser Datei gehen bei erneuter Kompilierung des Quellenschemas verloren. 
// Generiert am: 2017.11.01 um 11:23:36 AM CET 
//


package com.ibm.wlp.perf.namespace;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Inhalt in dieser Klasse an.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/namespace/}Stat" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stat"
})
@XmlRootElement(name = "Server", namespace = "http://www.w3.org/namespace/")
public class Server {

    @XmlElement(name = "Stat")
    protected List<Stat> stat;
    @XmlAttribute(name = "name")
    protected String name;

    public Server() {
    	super();
    	
    }
    public Server(String name) {
    	
    	super();
    	this.name = name;
    }
    /**
     * Gets the value of the stat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Stat }
     * 
     * 
     */
    public List<Stat> getStat() {
        if (stat == null) {
            stat = new ArrayList<Stat>();
        }
        return this.stat;
    }

    /**
     * Ruft den Wert der Eigenschaft name ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der Eigenschaft name fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }
    /**
     * 
     * @param newStat
     */
    public void addStat(Stat newStat) {
        if (stat == null) {
            stat = new ArrayList<Stat>();
        }
        stat.add(newStat);
    }
}
