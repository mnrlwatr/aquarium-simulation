package org.fp;

import org.fp.service.statistics.Statistics;

public class Main {
    public static void main(String[] args) {
        Facade.start();
        System.out.println(Statistics.getStatistic());
    }
}
