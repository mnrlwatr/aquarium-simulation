package org.fp.service.managment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.fp.container.ApplicationContext;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.Position;
import org.fp.model.fish.AbstractFish;
import org.fp.service.factory.fish.FishFactory;
import org.fp.service.statistics.Statistics;
import org.fp.service.util.RandomPosition;

import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FishLive implements Runnable {
    AquariumController aquariumController = (AquariumController) ApplicationContext.getDependency("aquariumController1");
    AbstractFish abstractFish;
    Mating mating;
    Movement movement;

    public FishLive(AbstractFish abstractFish) {
        this.abstractFish = abstractFish;
        movement = new Movement(aquariumController.getAquariumLength(), aquariumController.getAquariumHeight());
        mating = new Mating((FishFactory) ApplicationContext.getDependency("FishFactory"));
        new Thread(this).start(); // Каждая рыба должна быть в отдельном потоке.(Thread)
    }

    @Override
    public void run() {
        while (aquariumController.isAquariumWorking()) {
            if (abstractFish.getLifetime() > 0) {
                try {
                    movement.randomMove();
                } catch (AquariumIsNotWorkingException e) {
                    break;
                }
                abstractFish.setLifetime(abstractFish.getLifetime() - 1);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {
                aquariumController.releasePosition(abstractFish.getPosition());
                Statistics.incrementTotalDied();
                System.out.println(abstractFish + " is Dead"); // Отчет о каждом процессе должен отображаться в консоли.
                break;
            }
        }
    }

    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @Getter
    private class Movement {
        int aquariumLength;
        int aquariumHeight;

        public Movement(int aquariumLength, int aquariumHeight) {
            this.aquariumLength = aquariumLength;
            this.aquariumHeight = aquariumHeight;
        }

        public void randomMove() throws AquariumIsNotWorkingException {
            Position positionToMove = abstractFish.calculateRandomPositionToMove(getAquariumLength(), getAquariumHeight());
            Position previosPosition = abstractFish.getPosition();
            abstractFish.setPosition(positionToMove);
            AbstractFish f2 = aquariumController.placeFish(abstractFish);
            if (f2 == null) {
                aquariumController.releasePosition(previosPosition);
                Statistics.incrementTotalMovements();
            } else {
                abstractFish.setPosition(previosPosition);
                mating.tryToMate(abstractFish, f2);
            }
        }
    }

    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    private class Mating {
        @Getter
        FishFactory fishFactory;

        private Mating(FishFactory fishFactory) {
            this.fishFactory = fishFactory;
        }

        public boolean tryToMate(AbstractFish f1, AbstractFish f2) throws AquariumIsNotWorkingException {
            //Если самцы и самки встречаются, они должны размножаться.
            if (f1.getClass().equals(f2.getClass()) && !f1.getGender().equals(f2.getGender())) {
                AbstractFish newBornFish = getFishFactory().produce(f1.getClass());
                while (aquariumController.placeFish(newBornFish) != null) {
                    newBornFish.setPosition(RandomPosition.getPosition());
                }
                System.out.println("A new fish was born = " + newBornFish);
                Statistics.incrementTotalBorn();// Отчет о каждом процессе должен отображаться в консоли.
                return true;
            }
            return false;
        }
    }
}
