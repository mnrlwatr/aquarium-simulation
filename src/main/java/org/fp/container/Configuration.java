package org.fp.container;

import org.fp.model.Aquarium;
import org.fp.service.factory.aquarium.RandomAquariumFactory;
import org.fp.service.factory.fish.RandomFishFactory;
import org.fp.service.managment.AquariumController;
import org.fp.service.statistics.Statistics;

public class Configuration {
    private Configuration() {}
    public static void configure(){
        ApplicationContext.addDependency("aquarium1", RandomAquariumFactory.create());
        ApplicationContext.addDependency("RandomFishFactory1", new RandomFishFactory());
        ApplicationContext.addDependency("aquariumController1", new AquariumController((Aquarium) ApplicationContext.getDependency("aquarium1")));
        ApplicationContext.addDependency("Statistics1",new Statistics());
    }
}
