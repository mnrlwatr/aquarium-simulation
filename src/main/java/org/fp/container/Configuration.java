package org.fp.container;

import org.fp.constant.AquariumParameters;
import org.fp.model.Aquarium;
import org.fp.service.factory.aquarium.AquariumFactory;
import org.fp.service.factory.aquarium.RandomAquariumFactory;
import org.fp.service.factory.fish.RandomFishFactory;
import org.fp.service.managment.AquariumController;

public final class Configuration {
    private Configuration() {
    }

    public static void configure() {
        ApplicationContext.addDependency("AquariumParameters", new AquariumParameters("src/main/resources/rectangular_aquarium.json"));

        AquariumParameters parameters = (AquariumParameters) ApplicationContext.getDependency("AquariumParameters");
        ApplicationContext.addDependency("AquariumFactory", new RandomAquariumFactory(parameters));

        Aquarium aquarium1 = ((AquariumFactory) ApplicationContext.getDependency("AquariumFactory")).create();
        ApplicationContext.addDependency("aquarium1", aquarium1);

        ApplicationContext.addDependency("FishFactory", new RandomFishFactory());
        ApplicationContext.addDependency("aquariumController1", new AquariumController(aquarium1));
    }
}
