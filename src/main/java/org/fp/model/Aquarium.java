package org.fp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.fp.model.fish.AbstractFish;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class Aquarium {
    int height; // Position.y
    int length; // Position.x
    int capacity;
    ConcurrentMap<Position, AbstractFish> fishesMap = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        return "Aquarium{" +
                "height=" + height +
                ", length=" + length +
                ", capacity=" + capacity +
                ", currentFishesAmount=" + fishesMap.values().size() +
                '}';
    }
}
