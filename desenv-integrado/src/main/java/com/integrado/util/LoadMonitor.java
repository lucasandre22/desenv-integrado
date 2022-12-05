package com.integrado.util;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class LoadMonitor implements Runnable {

    //era 1000*1000
    private final static int MB = 1024 * 1024;
    private static String memoryUsage;
    private static double cpuLoad = 0;
    private long timeout;

    public LoadMonitor(long timeout) {
        this.timeout = timeout;
    }
    
    public void sleep() {
        try {
            Thread.sleep(timeout);
        } catch(Exception e) {

        }
    }

    @Override
    public void run() {
        while(true) {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);
            double cpuLoad = ManagementFactory.getPlatformMXBean(
                    com.sun.management.OperatingSystemMXBean.class).getCpuLoad();
            LoadMonitor.cpuLoad = cpuLoad;
            sleep();
            System.out.println("Free memory: " + Runtime.getRuntime().freeMemory()/MB);
            System.out.println("Memory used: "+ (Runtime.getRuntime().totalMemory()-
                    Runtime.getRuntime().freeMemory())/MB + "Mb");
            long freeMemory = Runtime.getRuntime().freeMemory();
            System.out.println("Free memory in JVM: " + freeMemory/MB);
            long maxMemory = Runtime.getRuntime().maxMemory();
            System.out.println("Max memory in JVM: " + maxMemory/MB);
            long totalMemory = Runtime.getRuntime().totalMemory();
            System.out.println("Total memory in JVM: " + totalMemory/MB);
            System.out.println("--------------------------------------");
        }
    }

    public static double getLoadAverage() {
        return cpuLoad;
    }

    public static boolean hasMemoryToProcessMatrixOne() {
        return false;
    }

    public static boolean hasMemoryToProcessMatrixTwo() {
        return false;
    }
    //if we have XXX memory, we can process a request of type X
    //if we have YYY memory, we can process a request of type Y
}
