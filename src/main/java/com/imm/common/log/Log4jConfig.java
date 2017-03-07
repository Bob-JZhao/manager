package com.imm.common.log;

import org.apache.log4j.xml.DOMConfigurator;

 
public class Log4jConfig {
	public static void configure(String configFile) {
		DOMConfigurator.configure(configFile);
	}
}
