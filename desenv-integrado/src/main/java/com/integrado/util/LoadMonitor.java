package com.integrado.util;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import com.integrado.algorithm.Algorithm.Model;
import com.sun.management.OperatingSystemMXBean;

public class LoadMonitor {

    private final static int MB = 1024 * 1024;
    public static long freeMemory = 0;
    public static double cpuLoad = 0;
    public static long usedMemory = 0;
    private long timeout;
    private static int toLower = 0;
    
    public static AtomicBoolean isAboutToProcessModelOne = new AtomicBoolean(false);
    public static AtomicBoolean isAboutToProcessModelTwo = new AtomicBoolean(false);

    public LoadMonitor(long timeout) {
        this.timeout = timeout;
    }
    
    public void sleep() {
        try {
            Thread.sleep(timeout);
        } catch(Exception e) {

        }
    }

    public static synchronized void run() {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);
            double cpuLoad = ManagementFactory.getPlatformMXBean(
                    com.sun.management.OperatingSystemMXBean.class).getCpuLoad();
            long freeMemory = Runtime.getRuntime().freeMemory();
            //long maxMemory = Runtime.getRuntime().maxMemory();
            //long totalMemory = Runtime.getRuntime().totalMemory();
            LoadMonitor.cpuLoad = cpuLoad;
            LoadMonitor.freeMemory = freeMemory/MB - toLower;
            /*if(isAboutToProcessModelOne.get())
                LoadMonitor.freeMemory = freeMemory/MB - 900;
            else if(isAboutToProcessModelTwo.get())
                LoadMonitor.freeMemory = freeMemory/MB - 140;
            else
                LoadMonitor.freeMemory =  freeMemory/MB;*/
            LoadMonitor.usedMemory = (Runtime.getRuntime().totalMemory()-
                    Runtime.getRuntime().freeMemory())/MB;
            //System.out.println("Free memory: " + Runtime.getRuntime().freeMemory()/MB);
            //System.out.println("Memory used: "+ (Runtime.getRuntime().totalMemory()-
                    //Runtime.getRuntime().freeMemory())/MB + "Mb");
            //System.out.println("Free memory in JVM: " + freeMemory/MB);
            //System.out.println("Max memory in JVM: " + maxMemory/MB);
            //System.out.println("Total memory in JVM: " + totalMemory/MB);
            //System.out.println("--------------------------------------");
            System.out.println(LoadMonitor.freeMemory);
            if(toLower < 0) {
                System.err.println("toLower:" + toLower);
            }
    }

    public static double getLoadAverage() {
        return cpuLoad;
    }

    public static void lowerMemoryAvailable(Model model) {
        toLower += model == Model.one ? 900 : 150;
    }

    public static void increaseMemoryAvailable(Model model) {
        toLower -= model == Model.one ? 900 : 150;
    }

    public static boolean hasEnoughMemory(Model model) {
        run();
        if(model == Model.one) {
            return LoadMonitor.freeMemory > 850 ? true : false;
        }
        return LoadMonitor.freeMemory > 140 ? true : false;
    }
}
