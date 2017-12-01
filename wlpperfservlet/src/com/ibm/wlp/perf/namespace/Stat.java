//
// Diese Datei wurde generiert von JavaTM Architecture for XML Binding(JAXB) Reference Implementation Version 2.2.8-b130911.1802 
// Weitere Informationen: <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Alle Modifikationen an dieser Datei gehen bei erneuter Kompilierung des Quellenschemas verloren. 
// Generiert am: 2017.11.01 um 11:23:36 AM CET 
//


package com.ibm.wlp.perf.namespace;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.w3.org/namespace/}AverageStatistic"/>
 *         &lt;element ref="{http://www.w3.org/namespace/}BoundedRangeStatistic"/>
 *         &lt;element ref="{http://www.w3.org/namespace/}CountStatistic"/>
 *         &lt;element ref="{http://www.w3.org/namespace/}DoubleStatistic"/>
 *         &lt;element ref="{http://www.w3.org/namespace/}RangeStatistic"/>
 *         &lt;element ref="{http://www.w3.org/namespace/}TimeStatistic"/>
 *         &lt;element ref="{http://www.w3.org/namespace/}Stat"/>
 *       &lt;/choice>
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
    "averageStatisticOrBoundedRangeStatisticOrCountStatistic"
})
@XmlRootElement(name = "Stat", namespace = "http://www.w3.org/namespace/")
public class Stat {

    @XmlElements({
        @XmlElement(name = "AverageStatistic", type = AverageStatistic.class),
        @XmlElement(name = "BoundedRangeStatistic", type = BoundedRangeStatistic.class),
        @XmlElement(name = "CountStatistic", type = CountStatistic.class),
        @XmlElement(name = "DoubleStatistic", type = DoubleStatistic.class),
        @XmlElement(name = "RangeStatistic", type = RangeStatistic.class),
        @XmlElement(name = "TimeStatistic", type = TimeStatistic.class),
        @XmlElement(name = "Stat", type = Stat.class)
    })
    protected List<Object> averageStatisticOrBoundedRangeStatisticOrCountStatistic;
    @XmlAttribute(name = "name")
    protected String name;

    public Stat() {
    	super();
    }
    public Stat(String name) {
    	super();
    	this.name = name;
    }
    /**
     * Gets the value of the averageStatisticOrBoundedRangeStatisticOrCountStatistic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the averageStatisticOrBoundedRangeStatisticOrCountStatistic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAverageStatisticOrBoundedRangeStatisticOrCountStatistic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AverageStatistic }
     * {@link BoundedRangeStatistic }
     * {@link CountStatistic }
     * {@link DoubleStatistic }
     * {@link RangeStatistic }
     * {@link TimeStatistic }
     * {@link Stat }
     * 
     * 
     */
    public List<Object> getAverageStatisticOrBoundedRangeStatisticOrCountStatistic() {
        if (averageStatisticOrBoundedRangeStatisticOrCountStatistic == null) {
            averageStatisticOrBoundedRangeStatisticOrCountStatistic = new ArrayList<Object>();
        }
        return this.averageStatisticOrBoundedRangeStatisticOrCountStatistic;
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
    
    protected void _addStatistic(Object newStatistic) {
        if (averageStatisticOrBoundedRangeStatisticOrCountStatistic == null) {
            averageStatisticOrBoundedRangeStatisticOrCountStatistic = new ArrayList<Object>();
        }
        averageStatisticOrBoundedRangeStatisticOrCountStatistic.add(newStatistic);
    	
    }
    public void addStat(Stat newStat) {
    	
    	// calculate average
        _addStatistic(newStat);
    }
    
    public void addNewStat(Stat newStat) {
    	
    	// calculate average
    	_calculateAverage(newStat);
        _addStatistic(newStat);
    }
    protected void _calculateAverage(Stat newStat) {
    	
    	List<Object> stats = newStat.getAverageStatisticOrBoundedRangeStatisticOrCountStatistic();
    	for (Object stat : stats) {
    		
    	  if (stat instanceof CountStatistic) {
    		  
    		CountStatistic cs = getCountStatistic(((CountStatistic) stat).getName());
    		if (cs == null) {
    			
    			cs = new CountStatistic((CountStatistic) stat);
    			averageStatisticOrBoundedRangeStatisticOrCountStatistic.add(cs);
    		} else {
    		   
     			long newCount = (((CountStatistic) stat).getCount() + cs.getCount()) / 2;
     			cs.setCount(newCount);
    		}
    	  } 
    	  if (stat instanceof DoubleStatistic) {
    		  
    		DoubleStatistic ds = getDoubleStatistic(((DoubleStatistic) stat).getName());
    		if (ds == null) {
    			
    			ds = new DoubleStatistic((DoubleStatistic) stat);
    			averageStatisticOrBoundedRangeStatisticOrCountStatistic.add(ds);
    		} else {
    		   
     			double newCount = (((DoubleStatistic) stat).getCount() + ds.getCount()) / 2;
     			ds.setCount(newCount);
    		}
    	  }
    	  if (stat instanceof TimeStatistic) {
    		  
    		TimeStatistic ts = getTimeStatistic(((TimeStatistic) stat).getName());
    		if (ts == null) {
    			
    			ts = new TimeStatistic((TimeStatistic) stat);
    			averageStatisticOrBoundedRangeStatisticOrCountStatistic.add(ts);
    		} else {
    		   
     			long totalTime = (((TimeStatistic) stat).getTotalTime() + ts.getTotalTime()) / 2;
     			ts.setTotalTime(totalTime);
    		}
    	  }
    	  if (stat instanceof BoundedRangeStatistic) {
    		  
    		BoundedRangeStatistic brs = getBoundedRangeStatistic(((BoundedRangeStatistic) stat).getName());
    		if (brs == null) {
    			
    			brs = new BoundedRangeStatistic((BoundedRangeStatistic) stat);
    			averageStatisticOrBoundedRangeStatisticOrCountStatistic.add(brs);
    		} else {
    		   
     			long value = (((BoundedRangeStatistic) stat).getValue() + brs.getValue()) / 2;
     			brs.setValue(value);
    		}
    	  }
    	  if (stat instanceof RangeStatistic) {
    		  
    		RangeStatistic rs = getRangeStatistic(((RangeStatistic) stat).getName());
    		if (rs == null) {
    			
    			rs = new RangeStatistic((RangeStatistic) stat);
    			averageStatisticOrBoundedRangeStatisticOrCountStatistic.add(rs);
    		} else {
    		   
     			long value = (((RangeStatistic) stat).getValue() + rs.getValue()) / 2;
     			rs.setValue(value);
    		}
    	  }
    	}
    }
    private BoundedRangeStatistic getBoundedRangeStatistic(String name) {
    	Iterator<Object> it = getAverageStatisticOrBoundedRangeStatisticOrCountStatistic().iterator();
    	while (it.hasNext()) {
    		Object obj = it.next(); 
    		if (obj instanceof BoundedRangeStatistic) {
      		  if (((BoundedRangeStatistic) obj).getName().equals(name))
                return (BoundedRangeStatistic) obj;
      	  }
    	}
        return null;
	}
    
    private RangeStatistic getRangeStatistic(String name) {
    	Iterator<Object> it = getAverageStatisticOrBoundedRangeStatisticOrCountStatistic().iterator();
    	while (it.hasNext()) {
    		Object obj = it.next(); 
    		if (obj instanceof RangeStatistic) {
      		  if (((RangeStatistic) obj).getName().equals(name))
                return (RangeStatistic) obj;
      	  }
    	}
        return null;
	}

    private TimeStatistic getTimeStatistic(String name) {
    	Iterator<Object> it = getAverageStatisticOrBoundedRangeStatisticOrCountStatistic().iterator();
    	while (it.hasNext()) {
    		Object obj = it.next(); 
    		if (obj instanceof TimeStatistic) {
      		  if (((TimeStatistic) obj).getName().equals(name))
                return (TimeStatistic) obj;
      	  }
    	}
        return null;
	}
    private DoubleStatistic getDoubleStatistic(String name) {
    	Iterator<Object> it = getAverageStatisticOrBoundedRangeStatisticOrCountStatistic().iterator();
    	while (it.hasNext()) {
    		Object obj = it.next(); 
    		if (obj instanceof DoubleStatistic) {
      		  if (((DoubleStatistic) obj).getName().equals(name))
                return (DoubleStatistic) obj;
      	  }
    	}
        return null;
	}
	protected CountStatistic getCountStatistic(String name) {
    	
    	Iterator<Object> it = getAverageStatisticOrBoundedRangeStatisticOrCountStatistic().iterator();
    	while (it.hasNext()) {
    		Object obj = it.next(); 
    		if (obj instanceof CountStatistic) {
      		  if (((CountStatistic) obj).getName().equals(name))
                return (CountStatistic) obj;
      	  }
    	}
        return null;
    }
    		
    public void addStat(BoundedRangeStatistic newStat) {
        _addStatistic(newStat);
    }
    public void addStat(AverageStatistic newStat) {
        _addStatistic(newStat);
    }
    public void addStat(CountStatistic newStat) {
        _addStatistic(newStat);
    }    
    public void addStat(DoubleStatistic newStat) {
        _addStatistic(newStat);
    }  
    public void addStat(RangeStatistic newStat) {
        _addStatistic(newStat);
    }   
    public void addStat(TimeStatistic newStat) {
        _addStatistic(newStat);
    }   
}
