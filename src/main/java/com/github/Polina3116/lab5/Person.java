package com.github.Polina3116.lab5;

import java.time.LocalDateTime;
import static kotlin.text.StringsKt.isBlank;

public final class Person {
    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Integer height; //Поле не может быть null, Значение поля должно быть больше 0
    private final int weight; //Значение поля должно быть больше 0
    private final String passportID; //Значение этого поля должно быть уникальным, Длина строки не должна быть больше 41, Поле может быть null
    private final Color hairColor; //Поле может быть null
    private final Location location; //Поле может быть null

    public Person(
            Integer id,
            String name,
            Coordinates coordinates,
            LocalDateTime creationDate,
            Integer height,
            int weight,
            String passportID,
            Color hairColor,
            Location location
    ) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.location = location;
        if (this.id == null) throw new NullPointerException("id can not be null");
        if (this.id <= 0) throw new IllegalArgumentException("id should be bigger than 0");
        if (this.name == null) throw new NullPointerException("name can not be null");
        if (isBlank(this.name)) throw new NullPointerException("name can not be empty");
        if (this.coordinates == null) throw new NullPointerException("coordinates can not be null");
        if (this.creationDate == null) throw new NullPointerException("creationDate can not be null");
        if (this.height == null) throw new NullPointerException("height can not be null");
        if (this.height <= 0) throw new IllegalArgumentException("height should be bigger than 0");
        if (this.weight <= 0) throw new IllegalArgumentException("weight should be bigger than 0");
        if (this.passportID != null && this.passportID.length() > 41) throw new IllegalArgumentException("passportID can not be longer that 41");

    }
}
