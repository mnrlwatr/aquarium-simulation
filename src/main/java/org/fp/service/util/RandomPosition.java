package org.fp.service.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.ApplicationContext;
import org.fp.model.Position;
import org.fp.service.managment.AquariumController;
import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class RandomPosition {
    static AquariumController aquariumController = (AquariumController) ApplicationContext.getDependency("aquariumController1");
    static ThreadLocalRandom random=ThreadLocalRandom.current();

    private RandomPosition() {
    }
    public static synchronized Position getPosition() {
        int x = random.nextInt(1, aquariumController.getAquariumLength());
        int y = random.nextInt(1, aquariumController.getAquariumHeight());
        return new Position(x, y);
    }
}
