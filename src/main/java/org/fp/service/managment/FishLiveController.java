package org.fp.service.managment;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.container.ApplicationContext;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.Position;
import org.fp.model.fish.AbstractFish;
import org.fp.service.util.AquariumFill;
import org.fp.service.statistics.Statistics;

import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FishLiveController implements Runnable {
    AquariumController aquariumController = (AquariumController) ApplicationContext.getDependency("aquariumController1");
    Statistics statistics = (Statistics) ApplicationContext.getDependency("Statistics1");
    AbstractFish abstractFish;
    Movement movement; // композиция (двигаться можешь только если есть жизнь)

    public FishLiveController(AbstractFish abstractFish) {
        this.abstractFish = abstractFish;
        movement = new Movement();
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
                statistics.incrementTotalDied();
                System.out.println(abstractFish + " is Dead"); // Отчет о каждом процессе должен отображаться в консоли.
                break;
            }
        }
    }

    private class Movement {

        public void randomMove() throws AquariumIsNotWorkingException {
            Position positionToMove = abstractFish.calculateRandomPositionToMove(aquariumController.getAquariumLength(), aquariumController.getAquariumHeight());
            Position previosPosition = abstractFish.getPosition();
            abstractFish.setPosition(positionToMove);
            AbstractFish f2 = aquariumController.placeFish(abstractFish);
            if (f2 == null) {
                aquariumController.releasePosition(previosPosition);
                statistics.incrementTotalMovements();
            } else {
                abstractFish.setPosition(previosPosition);
                bornFish(abstractFish,f2);
            }
        }

        // Вынесу в отдельный класс
        private void bornFish(AbstractFish f1,AbstractFish f2) throws AquariumIsNotWorkingException {
            //Если самцы и самки встречаются, они должны размножаться.
            if(f1.getClass().equals(f2.getClass()) && !f1.getGender().equals(f2.getGender())) {
                AbstractFish newBornFish = AquariumFill.addOneFish(aquariumController,f1.getClass());
                newBornFish.startLiving();
                statistics.incrementTotalBorn();
                System.out.println("A new fish was born = " + newBornFish); // Отчет о каждом процессе должен отображаться в консоли.
            }


        }

    }
}
