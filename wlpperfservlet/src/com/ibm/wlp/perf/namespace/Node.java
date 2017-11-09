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
 *         &lt;element ref="{http://www.w3.org/namespace/}Server" maxOccurs="unbounded" minOccurs="0"/>
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
    "server"
})
@XmlRootElement(name = "Node", namespace = "http://www.w3.org/namespace/")
public class Node {

    @XmlElement(name = "Server")
    protected List<Server> server;
    @XmlAttribute(name = "name")
    protected String name;

    public Node() {
    	
    	super();
    }
    public Node(String name) {
    	
    	super();
    	this.name = name;
    }
    /**
     * Gets the value of the server property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the server property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Server }
     * 
     * 
     */
    public List<Server> getServer() {
        if (server == null) {
            server = new ArrayList<Server>();
        }
        return this.server;
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
     * @param newServer
     */
    public void addServer(Server newServer) {
        if (server == null) {
            server = new ArrayList<Server>();
        }
        server.add(newServer);
    }

}
