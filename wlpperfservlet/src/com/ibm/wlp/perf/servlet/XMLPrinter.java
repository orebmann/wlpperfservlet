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
	final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

	protected static long startTime;
	static {
		startTime = System.currentTimeMillis();
	}

	protected DoubleStatistic _createDoubleStatistic(double count, String name, long sampleTime, String unit) {

		return getObjectFactory().createDoubleStatistic(name, count, unit, sampleTime, startTime);
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

	protected CountStatistic createCountStatistic(ObjectName mbean, String name, long sampleTime, String unit) {

		long count = 0l;
		try {
			count = (Long) getmBeanServer().getAttribute(mbean, name);
		} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException | MBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getObjectFactory().createCountStatistic(name, count, unit, sampleTime, startTime);
	}

	protected CountStatistic _createCreateCountStatistic(long count, String name, long sampleTime, String unit) {

		return getObjectFactory().createCountStatistic(name, count, unit, sampleTime, startTime);
	}

	protected HashSet<String> determineWebapps() {

		final String SERVLET_STATS_MBEAN = "WebSphere:type=ServletStats,name=*";

		HashSet<ObjectName> servletStatsMBeans = new HashSet<ObjectName>();
		ObjectName name;

		try {
			name = new ObjectName(SERVLET_STATS_MBEAN);

			servletStatsMBeans.addAll(getmBeanServer().queryNames(name, null));
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
		} catch (MalformedObjectNameException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return new HashSet<String>();
	}

	public Stat createServletStats() {
		// WebSphere:type=ServletStats,name=<AppName>.<ServletName>
		final String SERVLET_STATS_MBEAN = "WebSphere:type=ServletStats,name=";

		HashSet<ObjectName> servletStatsMBeans = new HashSet<ObjectName>();
		ObjectName name;
		Stat allServletStats = null;

		try {

			long lastSampleTime = System.currentTimeMillis();
			HashSet<String> webapps = determineWebapps();

			if (!webapps.isEmpty()) {

				allServletStats = new Stat("Web Applications");

				for (String webapp : webapps) {

					name = new ObjectName(SERVLET_STATS_MBEAN + webapp + "*");
					HashSet<ObjectName> servletStatMBeans = new HashSet<ObjectName>();
					servletStatMBeans.addAll(getmBeanServer().queryNames(name, null));
					int servletStatMBeansSize = servletStatMBeans.size();
					Stat webappStats = null;

					if (servletStatMBeansSize != 0) {

						webappStats = new Stat(webapp);
						Stat servletStats = null;

						for (ObjectName mbean : servletStatMBeans) {
							String servletName;
							try {
								servletName = (String) getmBeanServer().getAttribute(mbean, "ServletName");

								servletStats = new Stat(servletName);

								servletStats
										.addStat(createCountStatistic(mbean, "RequestCount", lastSampleTime, "N/A"));
								servletStats.addStat(
										createDoubleStatistic(mbean, "ResponseTime", lastSampleTime, "NANOSECOND"));

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
		} catch (MalformedObjectNameException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Stat createJvmStats() {

		final String JVM_STATS_MBEAN = "WebSphere:type=JvmStats";

		HashSet<ObjectName> jvmStatsMBeans = new HashSet<ObjectName>();
		ObjectName name;
		Stat jvmStat = null;

		try {

			name = new ObjectName(JVM_STATS_MBEAN);
			jvmStatsMBeans.addAll(getmBeanServer().queryNames(name, null));

			// should be always 1
			int jvmStatsMBeansSize = jvmStatsMBeans.size();
			long lastSampleTime = System.currentTimeMillis();

			if (jvmStatsMBeansSize == 1) {

				jvmStat = new Stat("JVM Runtime");
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
		} catch (MalformedObjectNameException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Stat createSessionStats() {
		final String SESSION_STATS_MBEAN = "WebSphere:type=SessionStats,name=*";

		HashSet<ObjectName> sessionStatsMBeans = new HashSet<ObjectName>();
		ObjectName name;
		Stat sessionStats = null;

		try {

			name = new ObjectName(SESSION_STATS_MBEAN);
			sessionStatsMBeans.addAll(getmBeanServer().queryNames(name, null));
			int sessionStatsMBeansSize = sessionStatsMBeans.size();
			long lastSampleTime = System.currentTimeMillis();

			if (sessionStatsMBeansSize != 0) {

				sessionStats = new Stat("Servlet Session Manager");

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
		} catch (MalformedObjectNameException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Stat createThreadStats() {

		// final String THREADPOOL_STATS_MBEAN =
		// "WebSphere:type=ThreadPoolStats,name=Default Executor";
		final String THREADPOOL_STATS_MBEAN = "WebSphere:type=ThreadPoolStats,name=*";

		HashSet<ObjectName> threadPoolStatsMBeans = new HashSet<ObjectName>();
		ObjectName name;

		try {

			name = new ObjectName(THREADPOOL_STATS_MBEAN);
			threadPoolStatsMBeans.addAll(getmBeanServer().queryNames(name, null));

			// should be always 1
			int threadPoolStatsMBeansSize = threadPoolStatsMBeans.size();
			long lastSampleTime = System.currentTimeMillis();

			if (threadPoolStatsMBeansSize != 0) {

				Stat threadsStats = new Stat("Thread Pools");
				for (ObjectName mbean : threadPoolStatsMBeans) {

					System.out.println(mbean.getCanonicalName());
					Stat threadStat = new Stat(mbean.getKeyProperty("name"));
					try {

						int activeThreads = (Integer) getmBeanServer().getAttribute(mbean, "ActiveThreads");
						threadStat.addStat(_createCreateCountStatistic((long) activeThreads, "ActiveThreads",
								lastSampleTime, "N/A"));

						int poolSize = (Integer) getmBeanServer().getAttribute(mbean, "PoolSize");
						threadStat.addStat(
								_createCreateCountStatistic((long) poolSize, "PoolSize", lastSampleTime, "N/A"));

						threadsStats.addNewStat(threadStat);

					} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException
							| MBeanException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return threadsStats;
			}
		} catch (MalformedObjectNameException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
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

		HashSet<ObjectName> connectionPoolStatsMBeans = new HashSet<ObjectName>();
		ObjectName name;
		Stat jdbcStats = null;

		if (filter == null)
			filter = "*";

		try {

			name = new ObjectName(CONNPOOL_STATS_MBEAN + filter);
			connectionPoolStatsMBeans.addAll(getmBeanServer().queryNames(name, null));
			int connectionPoolStatsMBeansSize = connectionPoolStatsMBeans.size();
			long lastSampleTime = System.currentTimeMillis();

			if (connectionPoolStatsMBeansSize != 0) {

				jdbcStats = new Stat(statsName);
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
		} catch (MalformedObjectNameException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
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
