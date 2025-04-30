package org.fp.service.creation.aquarium;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.model.Aquarium;
import java.util.concurrent.ThreadLocalRandom;
import static org.fp.constant.Constants.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class RandomAquariumFactory {
    static ThreadLocalRandom random=ThreadLocalRandom.current();
    private RandomAquariumFactory() {
    }

    /**
     * @return new Aquarium instance with random height,length and capacity where (height*length)>capacity is always true
     */
    public static Aquarium create() {
        while (true) {
            int height = random.nextInt(RECTANGULAR_AQUARIUM_MIN_HEIGHT, RECTANGULAR_AQUARIUM_MAX_HEIGHT);
            int length = random.nextInt(RECTANGULAR_AQUARIUM_MIN_LENGTH, RECTANGULAR_AQUARIUM_MAX_LENGTH);
            int capacity = random.nextInt(RECTANGULAR_AQUARIUM_MIN_CAPACITY, RECTANGULAR_AQUARIUM_MAX_CAPACITY);

            // height*length это общее количество мест (координатных точек x,y) в которых могут находиться рыбы
            // на одной точке (x,y) может находиться только одна рыба
            // random может вернуть capacity которая будет больше (height*length)
            // в таком случаи получиться что например: вместимость аквариума на 500 рыб, а координатных точек для размещения хватит только на 400 рыб
            // генерируем значения до тех пор, пока не будет удовлетворенно условия (height * length) > capacity
            if ((height * length) > capacity) {
                return new Aquarium(height, length, capacity);
            }
        }
    }
}
