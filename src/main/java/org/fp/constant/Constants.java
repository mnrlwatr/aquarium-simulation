package org.fp.constant;

public class Constants {
    // Временно сделал параметры как константы чтобы можно было тестировать проложения
    // Потом сделаю:параметры будут читаться из json файла и инициализировать поля (перед инициализацией будет валидация) (json лежит в /resources)
    // Условия для валидации : 1) MIN_HEIGHT * MIN_LENGTH >= MIN_CAPACITY ; 2) (MAX_HEIGHT-1) * (MAX_LENGTH-1) >= MAX_CAPACITY-1

    public static final int RECTANGULAR_AQUARIUM_MIN_HEIGHT = 30;
    public static final int RECTANGULAR_AQUARIUM_MAX_HEIGHT = 50;

    public static final int RECTANGULAR_AQUARIUM_MIN_LENGTH = 40;
    public static final int RECTANGULAR_AQUARIUM_MAX_LENGTH = 60;

    public static final int RECTANGULAR_AQUARIUM_MIN_CAPACITY = 1200;
    public static final int RECTANGULAR_AQUARIUM_MAX_CAPACITY = 2880;

    private Constants(){}
}