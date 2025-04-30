package org.fp.model.fish;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.fp.model.Position;
import org.fp.model.fish.enums.Gender;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public abstract class AbstractFish implements Moveable,Live {
    @Setter
    volatile int lifetime; // Setter вызывается только из одного потока, время в секундах.
    final Gender gender;
    volatile Position position;


    public boolean isMale() {
        return getGender().equals(Gender.MALE);
    }

    public boolean isFemale() {
        return getGender().equals(Gender.FEMALE);
    }

    public synchronized void setPosition(Position position) {
        this.position = position;
    }
}
