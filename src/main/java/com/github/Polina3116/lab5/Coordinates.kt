package com.github.Polina3116.lab5;
public final class Coordinates {
    private final Long x; //Поле не может быть null
    private final float y;

    public Coordinates(Long x, float y) {
        this.x = x;
        this.y = y;
        if (this.x == null) throw new NullPointerException("x can not be null");
    }
}
