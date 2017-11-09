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

	public DoubleStatistic _createDoubleStatistic(double count, String name, long sampleTime, String unit) {

       return getObjectFactory().createDoubleStatistic(name, count, unit, sampleTime, startTime);
	}

	public CountStatistic _createCreateCountStatistic(long count, String name, long sampleTime, String unit) {

	   return getObjectFactory().createCountStatistic(name, count, unit, sampleTime, startTime);
	}

	public Stat createServletStats() {
		// WebSphere:type=ServletStats,name=<AppName>.<ServletName>
		final String SERVLET_STATS_MBEAN = "WebSphere:type=ServletStats,name=";

		HashSet<ObjectName> servletStatsMBeans = new HashSet<ObjectName>();
		ObjectName name;
		Stat allServletStats = null;

		try {

			name = new ObjectName(SERVLET_STATS_MBEAN + "*");
			servletStatsMBeans.addAll(getmBeanServer().queryNames(name, null));
			int allServletStatsMBeansSize = servletStatsMBeans.size();
			long lastSampleTime = System.currentTimeMillis();
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

			long __requestCount = 0l;
			double __responseTime = 0;

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

						long _requestCount = 0l;
						double _responseTime = 0;

						for (ObjectName mbean : servletStatMBeans) {
							try {
								String servletName = (String) getmBeanServer().getAttribute(mbean, "ServletName");
								servletStats = new Stat(servletName);

								long requestCount = (Long) getmBeanServer().getAttribute(mbean, "RequestCount");
								_requestCount += requestCount;
								__requestCount += requestCount;
								servletStats.addStat(_createCreateCountStatistic(requestCount, "RequestCount",
										lastSampleTime, "N/A"));

								double responseTime = (Double) getmBeanServer().getAttribute(mbean, "ResponseTime");
								_responseTime += responseTime;
								__responseTime += responseTime;
								servletStats.addStat(_createDoubleStatistic(responseTime, "ResponseTime",
										lastSampleTime, "NANOSECOND"));

								webappStats.addStat(servletStats);
							} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException
									| MBeanException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						webappStats.addStat(_createCreateCountStatistic(_requestCount / servletStatMBeansSize,
								"RequestCount", lastSampleTime, "N/A"));
						webappStats.addStat(_createDoubleStatistic(_responseTime / servletStatMBeansSize,
								"ResponseTime", lastSampleTime, "NANOSECOND"));
						allServletStats.addStat(webappStats);
					}
				}
				allServletStats.addStat(_createCreateCountStatistic(__requestCount / allServletStatsMBeansSize,
						"RequestCount", lastSampleTime, "N/A"));
				allServletStats.addStat(_createDoubleStatistic(__responseTime / allServletStatsMBeansSize,
						"ResponseTime", lastSampleTime, "NANOSECOND"));
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

					System.out.println(mbean.getCanonicalName());
					try {

						long freeMemory = (Long) getmBeanServer().getAttribute(mbean, "FreeMemory");
						jvmStat.addStat(_createCreateCountStatistic(freeMemory, "FreeMemory", lastSampleTime, "N/A"));

						long usedMemory = (Long) getmBeanServer().getAttribute(mbean, "UsedMemory");
						jvmStat.addStat(_createCreateCountStatistic(usedMemory, "UsedMemory", lastSampleTime, "N/A"));

						long gcCount = (Long) getmBeanServer().getAttribute(mbean, "GcCount");
						jvmStat.addStat(_createCreateCountStatistic(gcCount, "GcCount", lastSampleTime, "N/A"));

						long gcTime = (Long) getmBeanServer().getAttribute(mbean, "GcTime");
						jvmStat.addStat(_createCreateCountStatistic(gcTime, "GcTime", lastSampleTime, "N/A"));

						long upTime = (Long) getmBeanServer().getAttribute(mbean, "UpTime");
						jvmStat.addStat(_createCreateCountStatistic(upTime, "UpTime", lastSampleTime, "N/A"));

						double processCpu = (Double) getmBeanServer().getAttribute(mbean, "ProcessCPU");
						jvmStat.addStat(_createDoubleStatistic(processCpu, "ProcessCPU", lastSampleTime, "N/A"));

					} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException
							| MBeanException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

				long _createCount = 0l, _liveCount = 0l, _activeCount = 0l, _invalidatedCount = 0l,
						_invalidatedCountbyTimeout = 0l;

				for (ObjectName mbean : sessionStatsMBeans) {

					System.out.println(mbean.getCanonicalName());
					Stat sessionStat = new Stat(mbean.getKeyProperty("name"));

					try {

						long createCount = (Long) getmBeanServer().getAttribute(mbean, "CreateCount");
						_createCount += createCount;
						sessionStat.addStat(
								_createCreateCountStatistic(createCount, "CreateCount", lastSampleTime, "N/A"));

						long liveCount = (Long) getmBeanServer().getAttribute(mbean, "LiveCount");
						_liveCount += liveCount;
						sessionStat.addStat(_createCreateCountStatistic(liveCount, "LiveCount", lastSampleTime, "N/A"));

						long activeCount = (Long) getmBeanServer().getAttribute(mbean, "ActiveCount");
						_activeCount += activeCount;
						sessionStat.addStat(
								_createCreateCountStatistic(activeCount, "ActiveCount", lastSampleTime, "N/A"));

						long invalidatedCount = (Long) getmBeanServer().getAttribute(mbean, "InvalidatedCount");
						_invalidatedCount += invalidatedCount;
						sessionStat.addStat(_createCreateCountStatistic(invalidatedCount, "InvalidatedCount",
								lastSampleTime, "N/A"));

						long invalidatedCountbyTimeout = (Long) getmBeanServer().getAttribute(mbean, "InvalidatedCountbyTimeout");
						_invalidatedCountbyTimeout += invalidatedCountbyTimeout;
						sessionStat.addStat(_createCreateCountStatistic(invalidatedCountbyTimeout,
								"InvalidatedCountbyTimeout", lastSampleTime, "N/A"));

						sessionStats.addStat(sessionStat);

					} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException
							| MBeanException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				sessionStats.addStat(_createCreateCountStatistic(_createCount / sessionStatsMBeansSize, "CreateCount",
						lastSampleTime, "N/A"));
				sessionStats.addStat(_createCreateCountStatistic(_liveCount / sessionStatsMBeansSize, "LiveCount",
						lastSampleTime, "N/A"));
				sessionStats.addStat(_createCreateCountStatistic(_activeCount / sessionStatsMBeansSize, "ActiveCount",
						lastSampleTime, "N/A"));
				sessionStats.addStat(_createCreateCountStatistic(_invalidatedCount / sessionStatsMBeansSize,
						"InvalidatedCount", lastSampleTime, "N/A"));
				sessionStats.addStat(_createCreateCountStatistic(_invalidatedCountbyTimeout / sessionStatsMBeansSize,
						"InvalidatedCountbyTimeout", lastSampleTime, "N/A"));
				return sessionStats;
			}
		} catch (MalformedObjectNameException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Stat createThreadStats() {

		final String THREADPOOL_STATS_MBEAN = "WebSphere:type=ThreadPoolStats,name=Default Executor";

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
				int _activeThreads = 0, _poolSize = 0;

				for (ObjectName mbean : threadPoolStatsMBeans) {

					System.out.println(mbean.getCanonicalName());
					Stat threadStat = new Stat(mbean.getKeyProperty("name"));
					try {

						int activeThreads = (Integer) getmBeanServer().getAttribute(mbean, "ActiveThreads");
						_activeThreads += activeThreads;
						threadStat.addStat(_createCreateCountStatistic((long) activeThreads, "ActiveThreads",
								lastSampleTime, "N/A"));

						int poolSize = (Integer) getmBeanServer().getAttribute(mbean, "PoolSize");
						_poolSize += poolSize;
						threadStat.addStat(
								_createCreateCountStatistic((long) poolSize, "PoolSize", lastSampleTime, "N/A"));

						threadsStats.addStat(threadStat);

					} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException
							| MBeanException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				threadsStats.addStat(_createCreateCountStatistic(_activeThreads / threadPoolStatsMBeansSize,
						"ActiveThreads", lastSampleTime, "N/A"));
				threadsStats.addStat(_createCreateCountStatistic(_poolSize / threadPoolStatsMBeansSize, "PoolSize",
						lastSampleTime, "N/A"));
				return threadsStats;
			}
		} catch (MalformedObjectNameException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Stat createThreadStats2() {

		//final String THREADPOOL_STATS_MBEAN = "WebSphere:type=ThreadPoolStats,name=Default Executor";
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

				long _connectionHandleCount = 0l, _createCount = 0l, _destroyCount = 0l, _freeConnectionCount = 0l,
						_managedConnectionCount = 0l;
				double _inUseTime = 0, _waitTime = 0;

				for (ObjectName mbean : connectionPoolStatsMBeans) {

					System.out.println(mbean.getCanonicalName());
					Stat jdbcStat = new Stat(mbean.getKeyProperty("name"));

					try {

						long connectionHandleCount = (Long) getmBeanServer().getAttribute(mbean, "ConnectionHandleCount");
						_connectionHandleCount += connectionHandleCount;
						jdbcStat.addStat(_createCreateCountStatistic(connectionHandleCount, "ConnectionHandleCount",
								lastSampleTime, "N/A"));

						long createCount = (Long) getmBeanServer().getAttribute(mbean, "CreateCount");
						_createCount += createCount;
						jdbcStat.addStat(
								_createCreateCountStatistic(createCount, "CreateCount", lastSampleTime, "N/A"));

						// Destroy Count
						long destroyCount = (Long) getmBeanServer().getAttribute(mbean, "DestroyCount");
						_destroyCount += destroyCount;
						jdbcStat.addStat(
								_createCreateCountStatistic(destroyCount, "DestroyCount", lastSampleTime, "N/A"));

						long freeConnectionCount = (Long) getmBeanServer().getAttribute(mbean, "FreeConnectionCount");
						_freeConnectionCount += freeConnectionCount;
						jdbcStat.addStat(_createCreateCountStatistic(freeConnectionCount, "FreeConnectionCount",
								lastSampleTime, "N/A"));

						double inUseTime = (Double) getmBeanServer().getAttribute(mbean, "InUseTime");
						_inUseTime += inUseTime;
						jdbcStat.addStat(_createDoubleStatistic(inUseTime, "InUseTime", lastSampleTime, "MILLISECOND"));

						long managedConnectionCount = (Long) getmBeanServer().getAttribute(mbean, "ManagedConnectionCount");
						_managedConnectionCount += managedConnectionCount;
						jdbcStat.addStat(_createCreateCountStatistic(managedConnectionCount, "ManagedConnectionCount",
								lastSampleTime, "N/A"));

						double waitTime = (Double) getmBeanServer().getAttribute(mbean, "WaitTime");
						_waitTime += waitTime;
						jdbcStat.addStat(_createDoubleStatistic(waitTime, "WaitTime", lastSampleTime, "MILLISECOND"));

						jdbcStats.addStat(jdbcStat);

					} catch (InstanceNotFoundException | AttributeNotFoundException | ReflectionException
							| MBeanException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				jdbcStats.addStat(_createCreateCountStatistic(_connectionHandleCount / connectionPoolStatsMBeansSize,
						"ConnectionHandleCount", lastSampleTime, "N/A"));
				jdbcStats.addStat(_createCreateCountStatistic(_createCount / connectionPoolStatsMBeansSize,
						"CreateCount", lastSampleTime, "N/A"));
				jdbcStats.addStat(_createCreateCountStatistic(_destroyCount / connectionPoolStatsMBeansSize,
						"DestroyCount", lastSampleTime, "N/A"));
				jdbcStats.addStat(_createCreateCountStatistic(_freeConnectionCount / connectionPoolStatsMBeansSize,
						"FreeConnectionCount", lastSampleTime, "N/A"));
				jdbcStats.addStat(_createDoubleStatistic(_inUseTime / connectionPoolStatsMBeansSize, "InUseTime",
						lastSampleTime, "MILLISECOND"));
				jdbcStats.addStat(_createCreateCountStatistic(_managedConnectionCount / connectionPoolStatsMBeansSize,
						"ManagedConnectionCount", lastSampleTime, "N/A"));
				jdbcStats.addStat(_createDoubleStatistic(_waitTime / connectionPoolStatsMBeansSize, "WaitTime",
						lastSampleTime, "MILLISECOND"));
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
			System.err.println("https://developer.ibm.com/answers/questions/7203/need-to-find-the-server-name-form-an-wlp-app.html");
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

		Stat threadStats = createThreadStats2();
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
