package org.fp.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.ApplicationContext;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.fish.AbstractFish;
import org.fp.service.creation.fish.FishFactory;
import org.fp.service.managment.AquariumController;
import org.fp.service.util.RandomPosition;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AquariumFill {
    static ThreadLocalRandom random = ThreadLocalRandom.current();
    static FishFactory fishFactory = (FishFactory) ApplicationContext.getDependency("FishFactory1");

    private AquariumFill() {
    }

    /**
     * Наполняет аквариум рыбами одного вида.
     * Общее количество рыб (самка и самец) определяется рандомно
     * @param clazz Вид/Семейство рыбы
     * @return Общее количество добавленных рыб
     */
    public static int fillWithFishes(AquariumController aquariumController, Class<?> clazz) throws AquariumIsNotWorkingException {
        // в аквариуме всегда будет минимум 200 рыб и максимум (aquarium.сapacity - 200)
        // чтобы аквариум быстро не выключалось (из-за опустошения или из-за переполнения)
        int randomFishesAmount = random.nextInt(200, (aquariumController.getAquariumCapacity() - 201));
        int maleCount = 0;
        int femaleCount = 0;
        ArrayList<AbstractFish> fishList = new ArrayList<>();
        for (int i = 0; i < randomFishesAmount; i++) {
            AbstractFish fish = addOneFish(aquariumController, clazz);
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
        for (AbstractFish fish : fishList) {
            fish.startLiving();
        }
        return randomFishesAmount;
    }

    public static AbstractFish addOneFish(AquariumController aquariumController, Class<?> clazz) throws AquariumIsNotWorkingException {
        AbstractFish newBornFish = fishFactory.create(clazz);
        while (!aquariumController.placeFish(newBornFish)) {
            newBornFish.setPosition(RandomPosition.getPosition());
        }
        return newBornFish;
    }

}
