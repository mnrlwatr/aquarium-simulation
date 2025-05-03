package org.fp;
import org.fp.container.ApplicationContext;
import org.fp.container.Configuration;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.fish.ClownFish;
import org.fp.service.factory.fish.FishFactory;
import org.fp.service.managment.AquariumController;
import org.fp.service.util.AquariumFill;
import org.fp.service.statistics.Statistics;

public class Facade {

    public static void start() {

        Configuration.configure();
        System.out.println("Aquarium created = "+ ApplicationContext.getDependency("aquarium1"));
        System.out.println("*********************************************************************************************");
        AquariumController aquariumController = (AquariumController) ApplicationContext.getDependency("aquariumController1");
        AquariumFill.setFishFactory((FishFactory) ApplicationContext.getDependency("RandomFishFactory1"));
        // На момент написания в аквариуме было N самцов и M самок.
        // Значения N и M также определяются методом Random.
        try {
            AquariumFill.fillWithFishes(aquariumController, ClownFish.class);
        } catch (AquariumIsNotWorkingException e) {
            throw new RuntimeException(e);
        }
        aquariumController.startObserving();

        while (aquariumController.isAquariumWorking()) {

        }


        Statistics statistics = (Statistics) ApplicationContext.getDependency("Statistics1");
        System.out.println(statistics.getStatistic());

    }
}