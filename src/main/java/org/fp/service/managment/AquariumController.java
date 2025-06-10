package org.fp.service.managment;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.Position;
import org.fp.model.Aquarium;
import org.fp.model.fish.AbstractFish;

import java.util.concurrent.ConcurrentMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AquariumController {
    Aquarium aquarium;
    @NonFinal
    volatile boolean working; // значение меняет только один тред

    /*
     *  Есть идея: использовать TreeMap вместо ConcurrentHashMap,
     *  так можно ускорить поиск нужной позиции и определять является ли позиция свободным или занятым,
     *  в начале инициализации аквариума нужно будет заполнить TreeMap<K, V> (может быть трудозатратной операцией если размер аквариума большой)
     *  K это сгенерированный id, а V - объект Position.
     *  Алгоритм для генерации id = (Position.y * Aquarium.length) + Position.x
     */
    ConcurrentMap<Position, AbstractFish> fishesMap;

    public AquariumController(Aquarium aquarium) {
        this.aquarium = aquarium;
        fishesMap = this.aquarium.getFishesMap();
        working = true;
    }

    public AbstractFish placeFish(AbstractFish fish) throws AquariumIsNotWorkingException {
        if (fish.getPosition()==null){ // avoiding putting a null key to the map
            throw new IllegalArgumentException("Position is null");
        }
        else if (isAquariumWorking()) {
            return fishesMap.putIfAbsent(fish.getPosition(), fish);
        } else {
            throw new AquariumIsNotWorkingException();
        }
    }

    public boolean isAquariumEmpty() {
        return fishesMap.isEmpty();
    }

    public boolean isAquariumFull() {
        return fishesMap.size() >= aquarium.getCapacity();
    }

    public void releasePosition(Position position) {
        fishesMap.remove(position);
    }

    public int getAquariumLength() {
        return aquarium.getLength();
    }

    public int getAquariumHeight() {
        return aquarium.getHeight();
    }

    public boolean isAquariumWorking() {
        return working;
    }

    public int getAquariumCapacity() {
        return aquarium.getCapacity();
    }

    public void startObserving() {
        if(working){
            Thread t1 = new Thread(() -> {
                while (!(isAquariumFull()) && !(isAquariumEmpty())) {
                }
                working = false;
                System.out.println("Aquarium stopped. It is " + (isAquariumFull() ? "Full" : "Empty") + " !");

            });
            t1.setDaemon(true);
            t1.start();
        } else {
            throw new IllegalStateException("Aquarium is not working");
        }

    }

}
