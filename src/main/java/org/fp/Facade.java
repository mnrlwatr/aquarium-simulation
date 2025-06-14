package org.fp;
import org.fp.container.ApplicationContext;
import org.fp.container.Configuration;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.Aquarium;
import org.fp.model.fish.AbstractFish;
import org.fp.model.fish.ClownFish;
import org.fp.service.factory.fish.FishFactory;
import org.fp.service.managment.AquariumController;
import org.fp.service.statistics.Statistics;
import org.fp.service.util.RandomPosition;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Facade {

    public static void start() {

        Configuration.configure();
        System.out.println("Aquarium created = "+ (Aquarium)ApplicationContext.getDependency("aquarium1"));
        System.out.println("*********************************************************************************************");

        AquariumController aquariumController = (AquariumController) ApplicationContext.getDependency("aquariumController1");
        // На момент написания в аквариуме было N самцов и M самок.
        // Значения N и M также определяются методом Random.
        // в аквариуме всегда будет минимум 200 рыб и максимум (aquarium.сapacity - 200)
        // чтобы аквариум быстро не выключалось (из-за опустошения или из-за переполнения)
        int randomFishesAmount = ThreadLocalRandom.current().nextInt(200, (aquariumController.getAquariumCapacity() - 201));
        int maleCount = 0;
        int femaleCount = 0;
        ArrayList<AbstractFish> fishList = new ArrayList<>();
        FishFactory fishFactory = (FishFactory) ApplicationContext.getDependency("FishFactory");
        for (int i = 0; i < randomFishesAmount; i++) {
            AbstractFish fish = fishFactory.produce(ClownFish.class);
            try {
                while (aquariumController.placeFish(fish)!=null) {
                    fish.setPosition(RandomPosition.getPosition());
                }
            } catch (AquariumIsNotWorkingException e) {
                throw new RuntimeException(e);
            }
            if (fish.isMale()) {
                maleCount++;
            } else if (fish.isFemale()) {
                femaleCount++;
            }
            fishList.add(fish);
        }

        System.out.println("Aquarium filled with fishes, total amount: " + randomFishesAmount);
        System.out.println("Male count = " + maleCount);
        System.out.println("Female count = " + femaleCount);

        // Одновременно оживляем всех рыб в аквариуме.
        for (AbstractFish fish : fishList) {
            fish.startLiving();
        }

        aquariumController.startObserving();

        while (aquariumController.isAquariumWorking()) {

        }
    }
}