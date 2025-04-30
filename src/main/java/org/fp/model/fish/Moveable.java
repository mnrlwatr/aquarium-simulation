package org.fp.model.fish;

import org.fp.model.Position;

public interface Moveable {
    Position calculateRandomPositionToMove(int aquariumLength, int aquariumHeight);
}
