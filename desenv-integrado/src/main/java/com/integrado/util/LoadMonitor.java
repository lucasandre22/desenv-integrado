package com.integrado.util;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class LoadMonitor implements Runnable {

    @Override
    public void run() {
        while(true) {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);
            System.out.println("Process cpu load: " + osBean.getProcessCpuLoad());
            double cpuLoad = ManagementFactory.getPlatformMXBean(
                    com.sun.management.OperatingSystemMXBean.class).getCpuLoad();
            System.out.println("Cpu load: " + cpuLoad);
            try {
                Thread.sleep(1000);
            } catch(Exception e) {
                
            }
        }
    }

}
