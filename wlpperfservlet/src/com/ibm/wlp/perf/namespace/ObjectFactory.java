//
// Diese Datei wurde generiert von JavaTM Architecture for XML Binding(JAXB) Reference Implementation Version 2.2.8-b130911.1802 
// Weitere Informationen: <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Alle Modifikationen an dieser Datei gehen bei erneuter Kompilierung des Quellenschemas verloren. 
// Generiert am: 2017.11.01 um 11:23:36 AM CET 
//


package com.ibm.wlp.perf.namespace;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.w3.namespace package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.w3.namespace
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DoubleStatistic }
     * 
     */
    public DoubleStatistic createDoubleStatistic() {
        return new DoubleStatistic();
    }

    public DoubleStatistic createDoubleStatistic(String name, short id, double count, String unit, long sampleTime, long startTime) {
        return new DoubleStatistic(name, id, count, unit, sampleTime, startTime);
    }
    
    /**
     * Create an instance of {@link Stat }
     * 
     */
    public Stat createStat() {
        return new Stat();
    }
    public Stat createStat(String name) {
        return new Stat(name);
    }
    /**
     * Create an instance of {@link AverageStatistic }
     * 
     */
    public AverageStatistic createAverageStatistic() {
        return new AverageStatistic();
    }

    /**
     * Create an instance of {@link BoundedRangeStatistic }
     * 
     */
    public BoundedRangeStatistic createBoundedRangeStatistic() {
        return new BoundedRangeStatistic();
    }

    /**
     * Create an instance of {@link CountStatistic }
     * 
     */
    public CountStatistic createCountStatistic() {
        return new CountStatistic();
    }
    public CountStatistic createCountStatistic(String name, short id, long count, String unit, long sampleTime, long startTime) {
        return new CountStatistic(name, id, count, unit, sampleTime, startTime);
    }
    /**
     * Create an instance of {@link RangeStatistic }
     * 
     */
    public RangeStatistic createRangeStatistic() {
        return new RangeStatistic();
    }

    /**
     * Create an instance of {@link TimeStatistic }
     * 
     */
    public TimeStatistic createTimeStatistic() {
        return new TimeStatistic();
    }

    /**
     * Create an instance of {@link Server }
     * 
     */
    public Server createServer() {
        return new Server();
    }

    /**
     * Create an instance of {@link Comments }
     * 
     */
    public Comments createComments() {
        return new Comments();
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }
    public Node createNode(String name) {
        return new Node(name);
    }
    /**
     * Create an instance of {@link PerformanceMonitor }
     * 
     */
    public PerformanceMonitor createPerformanceMonitor() {
        return new PerformanceMonitor();
    }

}
