package com.imm.common.log;

 
public final class LogFactory {
   
   /**
    * Get an instance of a logger object.
    * @param cls the Class to log from
    * @return Logger the logger instance
    */
   public static Log getLogger(Class cls) {
      return new Log4jLogger(cls);
   }
}
