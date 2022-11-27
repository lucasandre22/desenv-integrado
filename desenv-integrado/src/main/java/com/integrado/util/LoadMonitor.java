package com.integrado.util;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class LoadMonitor implements Runnable {

    private static String memoryUsage;
    private static double cpuLoad = 0;
    private long timeout;

    public LoadMonitor(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public void run() {
        while(true) {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);
            double cpuLoad = ManagementFactory.getPlatformMXBean(
                    com.sun.management.OperatingSystemMXBean.class).getCpuLoad();
            this.cpuLoad = cpuLoad;
            try {
                Thread.sleep(timeout);
            } catch(Exception e) {

            }
            System.out.println(Runtime.getRuntime().freeMemory()/(1000*1000));
            System.out.println("Meg used="+(Runtime.getRuntime().totalMemory()-
                    Runtime.getRuntime().freeMemory())/(1000*1000)+"Mb");
            long freeMemory = Runtime.getRuntime().freeMemory();
            System.out.println("Free memory in JVM: " + freeMemory/(1000*1000));
     
            long maxMemory = Runtime.getRuntime().maxMemory();
            System.out.println("Max memory in JVM: " + maxMemory/(1000*1000));
     
            long totalMemory = Runtime.getRuntime().totalMemory();
            System.out.println("Total memory in JVM: " + totalMemory/(1000*1000));
        }
    }

    public static double getLoadAverage() {
        return cpuLoad;
    }

}
