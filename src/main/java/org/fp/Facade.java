package org.fp;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.fish.ClownFish;
import org.fp.service.creation.aquarium.RandomAquariumFactory;
import org.fp.service.creation.fish.FishFactory;
import org.fp.service.managment.AquariumController;
import org.fp.model.Aquarium;
import org.fp.service.AquariumFill;
import org.fp.service.statistics.FishStatistics;

public class Facade {
    static final String LINE_SEPARATOR ="************************************************************************";
    public static void start() {

        Aquarium aquarium1 = RandomAquariumFactory.create();
        System.out.println("Aquarium created = "+aquarium1);

        ApplicationContext.addDependency("FishFactory1", new FishFactory());
        ApplicationContext.addDependency("aquariumController1", new AquariumController(aquarium1));
        ApplicationContext.addDependency("FishLifeStatistics1",new FishStatistics());

        AquariumController aquariumController = (AquariumController) ApplicationContext.getDependency("aquariumController1");

        // На момент написания в аквариуме было N самцов и M самок.
        // Значения N и M также определяются методом Random.
        try {
            AquariumFill.fillWithFishes(aquariumController, ClownFish.class);
        } catch (AquariumIsNotWorkingException e) {
            throw new RuntimeException(e);
        }
        aquariumController.startObserving();
        System.out.println(LINE_SEPARATOR);

        FishStatistics fishStatistics = (FishStatistics) ApplicationContext.getDependency("FishLifeStatistics1");
        while (aquariumController.isAquariumWorking()) {

        }

        String status = aquariumController.isAquariumFull() ? "Full" : "Empty";
        System.out.println(LINE_SEPARATOR);
        System.out.println("Aquarium stopped. It is " + status + "! \n" + fishStatistics.getStatistic());
        System.out.println(LINE_SEPARATOR);
    }
}