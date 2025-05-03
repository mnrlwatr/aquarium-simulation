package org.fp.model.fish;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.model.Position;
import org.fp.model.fish.enums.Gender;
import org.fp.service.managment.FishLiveController;
import org.fp.service.util.RandomDirection;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClownFish extends AbstractFish {

    public ClownFish(int lifetime, Gender gender, Position position) {
        super(lifetime, gender, position);
    }

    @Override
    public Position calculateRandomPositionToMove(int aquariumLength, int aquariumHeight) {
        while (true) {
            Position direction = RandomDirection.getDirection();
            int newX = getPosition().getX() + direction.getX();
            int newY = getPosition().getY() + direction.getY();
            // новое место для перемещения не должно выходит за рамки аквариума
            boolean x = (newX > 0) && (newX < aquariumLength);
            boolean y = (newY > 0) && (newY < aquariumHeight);
            if (x && y) {
                return new Position(newX, newY);
            }
        }
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(lifetime=" + getLifetime() + ", gender=" + getGender() + ")";
    }

    @Override
    public void startLiving() {
        new FishLiveController(this);
    }
}
