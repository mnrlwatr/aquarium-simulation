package org.fp.service.factory.fish;

import org.fp.model.fish.AbstractFish;

public interface FishFactory {
    AbstractFish produce(Class<?> clazz);
}
