package com.integrado.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Report {
    long freeMemoryMb;
    long usedMemoryMb;
    double cpuUsage;
}
