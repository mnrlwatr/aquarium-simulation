package org.fp.service.util;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.fish.AbstractFish;
import org.fp.service.factory.fish.FishFactory;
import org.fp.service.managment.AquariumController;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class AquariumFill {
    static final ThreadLocalRandom random = ThreadLocalRandom.current();
    @Setter
    static FishFactory fishFactory;

    private AquariumFill() {
    }

    /**
     * Наполняет аквариум рыбами одного вида.
     * Общее количество рыб (самка и самец) определяется рандомно
     * @param clazz Вид/Семейство рыбы
     * @return Общее количество добавленных рыб
     */
    public static int fillWithFishes(AquariumController aquariumController, Class<?> clazz) throws AquariumIsNotWorkingException {
        checkFishFactoryInitialization();
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
        checkFishFactoryInitialization();
        AbstractFish newBornFish = fishFactory.produce(clazz);
        while (aquariumController.placeFish(newBornFish)!=null) {
            newBornFish.setPosition(RandomPosition.getPosition());
        }
        return newBornFish;
    }

    private static void checkFishFactoryInitialization() {
        if (fishFactory == null) {
            throw new IllegalStateException("FishFactory is not initialized");
        }
    }

}
