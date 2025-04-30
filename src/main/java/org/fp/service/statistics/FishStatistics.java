package org.fp.service.statistics;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import java.util.concurrent.atomic.AtomicInteger;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FishStatistics {
    // если в аквариуме будет очень много активных рыб, то AtomicInteger лучше заменить на LongAdder (p.s sum() дорогостоящий операция)
    AtomicInteger totalBorn;
    AtomicInteger totalDied;
    AtomicInteger totalMovements;

    public FishStatistics() {
        totalBorn = new AtomicInteger(0);
        totalDied = new AtomicInteger(0);
        totalMovements = new AtomicInteger(0);
    }

    public void incrementTotalBorn() {
        totalBorn.incrementAndGet();
    }

    public void incrementTotalDied() {
        totalDied.incrementAndGet();
    }

    public void incrementTotalMovements() {
        totalMovements.incrementAndGet();
    }

    public void resetTotalBorn() {
        totalBorn.set(0);
    }

    public void resetTotalDied() {
        totalDied.set(0);
    }

    public void resetTotalMovements() {
        totalMovements.set(0);
    }

    public String getStatistic() {
        return "Total Born: " + totalBorn.get() + "\n" + "Total Died: " + totalDied.get() + "\n" + "Total Movements: " + totalMovements.get();
    }
}
