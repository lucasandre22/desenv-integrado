package com.integrado.util;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.integrado.algorithm.Algorithm.Model;
import com.sun.management.OperatingSystemMXBean;

public class LoadMonitor {

    private final static int MB = 1024 * 1024;
    public static AtomicLong freeMemory = new AtomicLong(0);
    public static double cpuLoad = 0;
    public static long usedMemory = 0;
    private long timeout;
    public static int toLower = 0;

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
        double cpuLoad = ManagementFactory.getPlatformMXBean(
                com.sun.management.OperatingSystemMXBean.class).getCpuLoad();
        long freeMemory = Runtime.getRuntime().freeMemory();
        LoadMonitor.cpuLoad = cpuLoad;
        LoadMonitor.freeMemory.set((freeMemory/MB) - toLower);
        LoadMonitor.usedMemory = (Runtime.getRuntime().totalMemory()-
                Runtime.getRuntime().freeMemory())/MB;

    }

    public static double getLoadAverage() {
        return cpuLoad;
    }

    public synchronized static void lowerMemoryAvailable(Model model) {
        toLower += model == Model.one ? 1000 : 230;
    }

    public synchronized static void increaseMemoryAvailable(Model model) {
        toLower -= model == Model.one ? 1000 : 230;
    }

    public static boolean hasEnoughMemory(Model model) {
        run();
        if(model == Model.one) {
            return LoadMonitor.freeMemory.get() > 950 ? true : false;
        }
        return LoadMonitor.freeMemory.get() > 230 ? true : false;
    }
}
