package org.fp.service.creation.fish;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.model.Position;
import org.fp.model.fish.AbstractFish;
import org.fp.model.fish.ClownFish;
import org.fp.model.fish.enums.Gender;
import org.fp.service.util.RandomPosition;

import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FishFactory {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    public synchronized AbstractFish create(Class<?> clazz){
        Position randomPosition = RandomPosition.getPosition();

        // Пол каждой новорожденной рыбы определяется методом Random
        boolean gender = random.nextBoolean();
        Gender fishGender = gender?Gender.MALE:Gender.FEMALE;

        // У каждой рыбы своя продолжительность жизни, и ее длина определяется методом Random.
        int lifeTime = random.nextInt(7,12); // можно калибровать, долгая жизнь=большая рождаемость / короткая жизнь=больше смертей

        if(ClownFish.class.equals(clazz)){
            return new ClownFish(lifeTime, fishGender, randomPosition);
        } else {
            throw new IllegalArgumentException("Unsupported fish type: " + clazz.getSimpleName());
        }
    }
}
