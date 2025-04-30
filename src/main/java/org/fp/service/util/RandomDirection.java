package org.fp.service.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.model.Position;

import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class RandomDirection {
    private RandomDirection() {
    }

    static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static synchronized Position getDirection() {
        int randomInt = random.nextInt(1, 5);
        // RIGHT=1, LEFT=2, UP=3, DOWN=4
        switch (randomInt) {
            case 1:
                return new Position(1, 0);
            case 2:
                return new Position(-1, 0);
            case 3:
                return new Position(0, 1);
            case 4:
                return new Position(0, -1);
            default:
                return new Position(0, 0);
        }
    }
}
