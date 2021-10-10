package com.java.hello.api;

import java.util.Random;

import org.apache.log4j.Logger;

public class Backoff {
 Logger logger=Logger.getLogger(Backoff.class);
 public static int defaultRetries=5;
 public static long defaultWaitTimeInMills=1000;
 private int numberOfRetries;
 
 private int numberOfTriesLeft;
 
 private long defaultTimeToWait;
 
 private long timeToWait;
 
 private final Random random = new Random();

 public Backoff() {
    
    this(defaultRetries, defaultWaitTimeInMills);
 }
 
 public Backoff(int numberOfRetries, long defaultTimeToWait){
     this.numberOfRetries = numberOfRetries;
     this.numberOfTriesLeft = numberOfRetries;
     this.defaultTimeToWait = defaultTimeToWait;
     this.timeToWait = defaultTimeToWait;
 }
 
 public boolean shouldRetry() {
     return numberOfTriesLeft > 0;
 }
 public void errorOccured() {
   
   numberOfTriesLeft --;
   if (!shouldRetry()) {
	   logger.info("Retry failed");
   }
   waitUntilNextTry();
   timeToWait += random.nextInt(1000);
 }
private void waitUntilNextTry() {
  
  try {
    Thread.sleep(timeToWait);
  } catch (InterruptedException e) {
    e.printStackTrace();
  }
 }
public long getTimeToWait() {
   return this.timeToWait;
}
public void doNotRetry() {
    numberOfTriesLeft = 0;
}
 public void reset() {
  this.numberOfTriesLeft = numberOfRetries;
  this.timeToWait = defaultTimeToWait;
 }
}