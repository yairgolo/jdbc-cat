package com.company.model;

import lombok.*;

@Data
@AllArgsConstructor
@ToString
public class Cat {
    private Long id;
    private String name;
    public int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
