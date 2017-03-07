package com.imm.common.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

 
public final class Log4jLogger implements Log {

   /** the log object to log to */
   private transient Logger logger = null;
   // ------------------------------------------------------------- Attributes

   /** The fully qualified name of the Log4JLogger class. */
   private static final String FQCN = Log4jLogger.class.getName();
   
   /**
    * Constructor for creating a logger object using jdk 1.4 or higher logging.
    *
    * @param cls the class which wants to log
    */
   public Log4jLogger(Class cls) {
      logger = Logger.getLogger(cls);
   }
   

   /**
    * Logging a fine message
    * @param message the message to log.
    */
   public void fine(String message) {
      logger.log(FQCN,Level.DEBUG,message,null);
   }


   /**
    * Logging a config message.
    * @param message the message to log
    */
   public void config(String message) {
       logger.log(FQCN,Level.DEBUG,message,null);
   }


   /**
    * Logging a info message.
    * @param message the message to log
    */
   public void info(String message) {
       logger.log(FQCN,Level.INFO,message,null);
   }


   /**
    * Logging a warning message.
    * @param message the message to log
    */
   public void warning(String message) {
       logger.log(FQCN,Level.WARN,message,null);
   }


  

   //****************************************************
   //*  The methods from log4j also implemented below   *
   //****************************************************


   /**
    * Logging a debug message.
    * @param message the message to log
    */
   public void debug(String message) {
       logger.log(FQCN, Level.DEBUG, message, null);
   }

   /**
    * Logging a fatal message with the throwable message.
    * @param message the message to log
    * @param t the exception
    */
   public void fatal(String message, Throwable t) {
      logger.log(FQCN,Level.FATAL,message, t);
   }


   /**
    * Logging a debug message with the throwable message.
    * @param message the message to log
    * @param t the exception
    */
   public void debug(String message, Throwable t) {
       logger.log(FQCN,Level.DEBUG,message,t);
   }


   /**
    * Logging an info message with the throwable message.
    * @param message the message to log
    * @param t the exception
    */
   public void info(String message, Throwable t) {
       logger.log(FQCN,Level.INFO,message,t);
   }


   /**
    * Logging a warning message.
    * @param message the message to log
    */
   public void warn(String message) {
      logger.log(FQCN,Level.WARN,message,null);
   }


   /**
    * Logging a warning message with the throwable message.
    * @param message the message to log
    * @param t the exception
    */
   public void warn(String message, Throwable t) {
      logger.log(FQCN,Level.WARN,message, t);
   }


   /**
    * Logging an error message.
    * @param message the message to log
    */
   public void error(String message) {
      logger.log(FQCN,Level.ERROR,message,null);
   }


   /**
    * Logging an error message with the throwable message.
    * @param message the message to log
    * @param t the exception
    */
   public void error(String message, Throwable t) {
      logger.log(FQCN,Level.ERROR,message, t);
   }

   public void error(Throwable e) {
       logger.log(FQCN,Level.ERROR, "", e);      
   }

   /**
    * Logging a fatal message.
    * @param message the message to log
    */
   public void fatal(String message) {
      logger.log(FQCN,Level.FATAL,message,null);
   }

}
