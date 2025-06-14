package org.fp.service.statistics;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.atomic.AtomicInteger;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class Statistics {

    // если в аквариуме будет очень много активных рыб, то AtomicInteger лучше заменить на LongAdder (p.s sum() дорогостоящий операция)
    static AtomicInteger totalBorn = new AtomicInteger(0);
    static AtomicInteger totalDied = new AtomicInteger(0);
    static AtomicInteger totalMovements = new AtomicInteger(0);

    private Statistics() {
    }

    public static void incrementTotalBorn() {
        totalBorn.incrementAndGet();
    }

    public static void incrementTotalDied() {
        totalDied.incrementAndGet();
    }

    public static void incrementTotalMovements() {
        totalMovements.incrementAndGet();
    }

    public static void resetTotalBorn() {
        totalBorn.set(0);
    }

    public static void resetTotalDied() {
        totalDied.set(0);
    }

    public static void resetTotalMovements() {
        totalMovements.set(0);
    }

    public static void resetAll() {
        resetTotalBorn();
        resetTotalDied();
        resetTotalMovements();
    }

    public static String getStatistic() {
        return "Total Born: " + totalBorn.get() + "\n" + "Total Died: " + totalDied.get() + "\n" + "Total Movements: " + totalMovements.get();
    }
}
