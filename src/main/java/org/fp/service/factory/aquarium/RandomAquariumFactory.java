package org.fp.service.factory.aquarium;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.constant.AquariumParameters;
import org.fp.model.Aquarium;
import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RandomAquariumFactory implements AquariumFactory {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    AquariumParameters parameters;

    public RandomAquariumFactory(AquariumParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * @return new Aquarium instance with random height,length and capacity where (height*length)>capacity is always true
     */
    @Override
    public Aquarium create() {
        while (true) {
            int height = random.nextInt(parameters.getMIN_HEIGHT(), parameters.getMAX_HEIGHT()+1);
            int length = random.nextInt(parameters.getMIN_LENGTH(), parameters.getMAX_LENGTH()+1);
            int capacity = random.nextInt(parameters.getMIN_CAPACITY(), parameters.getMAX_CAPACITY()+1);

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
