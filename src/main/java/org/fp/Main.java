package org.fp;

import org.fp.container.ApplicationContext;
import org.fp.service.statistics.Statistics;

public class Main {
    public static void main(String[] args) {
        Facade.start();
        Statistics statistics = (Statistics) ApplicationContext.getDependency("Statistics1");
        System.out.println(statistics.getStatistic());
    }
}
