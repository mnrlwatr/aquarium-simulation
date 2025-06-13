package org.fp.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class AquariumParameters {
    int MIN_HEIGHT;
    int MAX_HEIGHT;
    int MIN_LENGTH;
    int MAX_LENGTH;
    int MIN_CAPACITY;
    int MAX_CAPACITY;

    public AquariumParameters(String jsonSourcePath) {
        // Временное решение, в будущем сделаю загрузку параметров из json файла,
        // перед инициализацией параметры будут проверяться
        // Условия для валидации : 1) MIN_HEIGHT * MIN_LENGTH >= MIN_CAPACITY ; 2) (MAX_HEIGHT-1) * (MAX_LENGTH-1) >= MAX_CAPACITY-1
        MIN_HEIGHT = 30;
        MAX_HEIGHT = 50;
        MIN_LENGTH = 40;
        MAX_LENGTH = 60;
        MIN_CAPACITY = 1200;
        MAX_CAPACITY = 2880;
    }

}