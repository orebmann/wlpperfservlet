package com.ibm.wlp.perf.servlet;

import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.util.HashSet;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.ibm.wlp.perf.namespace.CountStatistic;
import com.ibm.wlp.perf.namespace.DoubleStatistic;
import com.ibm.wlp.perf.namespace.Node;
import com.ibm.wlp.perf.namespace.ObjectFactory;
import com.ibm.wlp.perf.namespace.PerformanceMonitor;
import com.ibm.wlp.perf.namespace.Server;
import com.ibm.wlp.perf.namespace.Stat;

public class XMLPrinter {

	final protected ObjectFactory objectFactory = new ObjectFactory();
	final protected MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

	protected static long startTime;
	static {
		startTime = System.currentTimeMillis();
	}

	protected DoubleStatistic createDoubleStatistic(ObjectName mbean, String name, long sampleTime, String unit) {
		double count = 0;
		try {
			count = (Double) getmBeanServer().getAttribute(mbean, name);
		} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException | MBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getObjectFactory().createDoubleStatistic(name, count, unit, sampleTime, startTime);
	}

	protected CountStatistic createCountStatistik(String name, long count, long sampleTime, String unit) {
		return getObjectFactory().createCountStatistic(name, count, unit, sampleTime, startTime);	
	}
	
	protected CountStatistic createCountStatisticFromInteger(ObjectName mbean, String name, long sampleTime, String unit) {

		int count = 0;
		try {
			count = (Integer) getmBeanServer().getAttribute(mbean, name);
		} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException | MBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createCountStatistik(name,(long) count, sampleTime, unit);
	}
	
	protected CountStatistic createCountStatistic(ObjectName mbean, String name, long sampleTime, String unit) {

		long count = 0l;
		try {
			count = (Long) getmBeanServer().getAttribute(mbean, name);
		} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException | MBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createCountStatistik(name, count, sampleTime, unit);
	}

	protected HashSet<String> determineWebapps() {

		final String SERVLET_STATS_MBEAN = "WebSphere:type=ServletStats,name=*";

		HashSet<ObjectName> servletStatsMBeans = retrieveStatMBeans(SERVLET_STATS_MBEAN);
		HashSet<String> webapps = new HashSet<String>();

		// determine all webapps
		for (ObjectName mbean : servletStatsMBeans) {

			System.out.println(mbean.getCanonicalName());
			String appName;
			try {
				appName = (String) getmBeanServer().getAttribute(mbean, "AppName");
				webapps.add(appName);
			} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException
					| MBeanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return webapps;
	}

	protected HashSet<ObjectName> retrieveStatMBeans(String name) {
	
		ObjectName objectName;
		try {
			objectName = new ObjectName(name);
			HashSet<ObjectName> statMBeans = new HashSet<ObjectName>();
			statMBeans.addAll(getmBeanServer().queryNames(objectName, null));
			
			return statMBeans;
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Stat createServletStats() {
		// WebSphere:type=ServletStats,name=<AppName>.<ServletName>
		final String SERVLET_STATS_MBEAN = "WebSphere:type=ServletStats,name=";

		long lastSampleTime = System.currentTimeMillis();
		HashSet<String> webapps = determineWebapps();

		if (!webapps.isEmpty()) {
			
			Stat allServletStats = null;
			allServletStats = new Stat("Web Applications");
			for (String webapp : webapps) {

				HashSet<ObjectName> servletStatMBeans = retrieveStatMBeans(SERVLET_STATS_MBEAN + webapp + "*");
				if (servletStatMBeans.size() != 0) {

					Stat webappStats = null;
					webappStats = new Stat(webapp);
					for (ObjectName mbean : servletStatMBeans) {
					
						String servletName;
						Stat servletStats = null;
						try {
							servletName = (String) getmBeanServer().getAttribute(mbean, "ServletName");

							servletStats = new Stat(servletName);
							servletStats.addStat(createCountStatistic(mbean, "RequestCount", lastSampleTime, "N/A"));
							servletStats.addStat(createDoubleStatistic(mbean, "ResponseTime", lastSampleTime, "NANOSECOND"));
							
							webappStats.addNewStat(servletStats);
						} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException
								| MBeanException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					allServletStats.addNewStat(webappStats);
				}
			}
			return allServletStats;
		}
		return null;
	}

	public Stat createJvmStats() {

		final String JVM_STATS_MBEAN = "WebSphere:type=JvmStats";

		long lastSampleTime = System.currentTimeMillis();
		HashSet<ObjectName> jvmStatsMBeans = retrieveStatMBeans(JVM_STATS_MBEAN);

		if (jvmStatsMBeans.size() == 1) {
			
			Stat jvmStat = new Stat("JVM Runtime");
			for (ObjectName mbean : jvmStatsMBeans) {

				jvmStat.addStat(createCountStatistic(mbean, "FreeMemory", lastSampleTime, "N/A"));
				jvmStat.addStat(createCountStatistic(mbean, "UsedMemory", lastSampleTime, "N/A"));
				jvmStat.addStat(createCountStatistic(mbean, "GcCount", lastSampleTime, "N/A"));
				jvmStat.addStat(createCountStatistic(mbean, "GcTime", lastSampleTime, "N/A"));
				jvmStat.addStat(createCountStatistic(mbean, "UpTime", lastSampleTime, "N/A"));
				jvmStat.addStat(createDoubleStatistic(mbean, "ProcessCPU", lastSampleTime, "N/A"));
			}
			return jvmStat;
		}
		return null;
	}

	public Stat createSessionStats() {
		final String SESSION_STATS_MBEAN = "WebSphere:type=SessionStats,name=*";

		long lastSampleTime = System.currentTimeMillis();
		HashSet<ObjectName> sessionStatsMBeans = retrieveStatMBeans(SESSION_STATS_MBEAN);

		if (sessionStatsMBeans.size() != 0) {

			Stat sessionStats = new Stat("Servlet Session Manager");
			for (ObjectName mbean : sessionStatsMBeans) {

				System.out.println(mbean.getCanonicalName());
				Stat sessionStat = new Stat(mbean.getKeyProperty("name"));

				sessionStat.addStat(createCountStatistic(mbean, "CreateCount", lastSampleTime, "N/A"));
				sessionStat.addStat(createCountStatistic(mbean, "LiveCount", lastSampleTime, "N/A"));
				sessionStat.addStat(createCountStatistic(mbean, "ActiveCount", lastSampleTime, "N/A"));
				sessionStat.addStat(createCountStatistic(mbean, "InvalidatedCount", lastSampleTime, "N/A"));
				sessionStat
						.addStat(createCountStatistic(mbean, "InvalidatedCountbyTimeout", lastSampleTime, "N/A"));
				sessionStats.addNewStat(sessionStat);
			}
			return sessionStats;
		}
		return null;
	}

	public Stat createThreadStats() {

		// final String THREADPOOL_STATS_MBEAN =
		// "WebSphere:type=ThreadPoolStats,name=Default Executor";
		final String THREADPOOL_STATS_MBEAN = "WebSphere:type=ThreadPoolStats,name=*";

		long lastSampleTime = System.currentTimeMillis();
		HashSet<ObjectName> threadPoolStatsMBeans = retrieveStatMBeans(THREADPOOL_STATS_MBEAN);

		// should be always 1
		if (threadPoolStatsMBeans.size() != 0) {

			Stat threadsStats = new Stat("Thread Pools");
			for (ObjectName mbean : threadPoolStatsMBeans) {

				System.out.println(mbean.getCanonicalName());
				Stat threadStat = new Stat(mbean.getKeyProperty("name"));

				threadStat.addStat(createCountStatisticFromInteger(mbean, "ActiveThreads",
							lastSampleTime, "N/A"));
				threadStat.addStat(
							createCountStatisticFromInteger(mbean, "PoolSize", lastSampleTime, "N/A"));

				threadsStats.addNewStat(threadStat);
			}
			return threadsStats;
		}
		return null;
	}

	public Stat createJMSConnectionPoolStats() {

		return createConnectionPoolStats("JMS Connection Pools", "jms*");
	}

	public Stat createJDBCConnectionPoolStats() {

		return createConnectionPoolStats("JDBC Connection Pools", "jdbc*");
	}

	protected Stat createConnectionPoolStats(String statsName, String filter) {
		final String CONNPOOL_STATS_MBEAN = "WebSphere:type=ConnectionPoolStats,name=";

		if (filter == null)
			filter = "*";

		long lastSampleTime = System.currentTimeMillis();
		HashSet<ObjectName> connectionPoolStatsMBeans = retrieveStatMBeans(CONNPOOL_STATS_MBEAN);

		if (connectionPoolStatsMBeans.size() != 0) {

			Stat jdbcStats = new Stat(statsName);
			for (ObjectName mbean : connectionPoolStatsMBeans) {

				System.out.println(mbean.getCanonicalName());
				Stat jdbcStat = new Stat(mbean.getKeyProperty("name"));

				jdbcStat.addStat(createCountStatistic(mbean, "ConnectionHandleCount", lastSampleTime, "N/A"));
				jdbcStat.addStat(createCountStatistic(mbean, "CreateCount", lastSampleTime, "N/A"));
				jdbcStat.addStat(createCountStatistic(mbean, "DestroyCount", lastSampleTime, "N/A"));
				jdbcStat.addStat(createCountStatistic(mbean, "FreeConnectionCount", lastSampleTime, "N/A"));
				jdbcStat.addStat(createDoubleStatistic(mbean, "InUseTime", lastSampleTime, "MILLISECOND"));
				jdbcStat.addStat(createCountStatistic(mbean, "ManagedConnectionCount", lastSampleTime, "N/A"));
				jdbcStat.addStat(createDoubleStatistic(mbean, "WaitTime", lastSampleTime, "MILLISECOND"));
				jdbcStats.addNewStat(jdbcStat);
			}
			return jdbcStats;
		}
		return null;
	}

	public void generateXML(OutputStream os) throws JAXBException {

		PerformanceMonitor pm = new PerformanceMonitor();
		Node node = new Node("dummynode");

		String serverName = null;
		try {

			serverName = (String) new InitialContext().lookup("serverName");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(
					"https://developer.ibm.com/answers/questions/7203/need-to-find-the-server-name-form-an-wlp-app.html");
			System.err.println("Add the following to your server.xml to avoid this error:");
			System.err.println("<featureManager>");
			System.err.println("  <feature>jndi-1.0</feature>");
			System.err.println("</featureManager>");
			System.err.println("<jndiEntry jndiName=\"serverName\" value=\"${wlp.server.name}\" /> ");
		}
		Server server = null;
		if (serverName != null)
			server = new Server(serverName);
		else
			server = new Server();

		Stat jdbcStats = createJDBCConnectionPoolStats();
		if (jdbcStats != null)
			server.addStat(jdbcStats);

		Stat jmsStats = createJMSConnectionPoolStats();
		if (jmsStats != null)
			server.addStat(jmsStats);

		Stat threadStats = createThreadStats();
		if (threadStats != null)
			server.addStat(threadStats);

		Stat jvmStats = createJvmStats();
		if (jvmStats != null)
			server.addStat(jvmStats);

		Stat sessionStats = createSessionStats();
		if (sessionStats != null)
			server.addStat(sessionStats);

		Stat servletStats = createServletStats();
		if (servletStats != null)
			server.addStat(servletStats);

		node.addServer(server);
		pm.addNode(node);
		pm.setResponseStatus("success");

		JAXBContext jaxbContext = JAXBContext.newInstance(pm.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.marshal(pm, os);
	}

	public ObjectFactory getObjectFactory() {
		return objectFactory;
	}

	public MBeanServer getmBeanServer() {
		return mBeanServer;
	}
}
