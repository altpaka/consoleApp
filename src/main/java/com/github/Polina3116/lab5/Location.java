package com.github.Polina3116.lab5;
import static kotlin.text.StringsKt.isBlank;

public final class Location {
    private final double x;
    private final Float y; //Поле не может быть null
    private final String name; //Строка не может быть пустой, Поле может быть null

    public Location(double x, Float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        if (this.y == null) throw new NullPointerException("y can not be null");
        if (this.name != null && isBlank(this.name)) throw new IllegalArgumentException("name can not be empty");
    }
}
