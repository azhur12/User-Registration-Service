package org.azhur.builder;

import org.azhur.jpaEntities.Breed;
import org.azhur.jpaEntities.Cat;
import org.azhur.jpaEntities.Owner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CatBuilder {
    private int id;
    private String name;
    private Date birthday;
    private String color;
    private Breed breed;
    private List<Cat> friendsList;
    private List<Cat> friendOfList;
    private Owner owner;

    public CatBuilder() {
        this.friendsList = new ArrayList<>();
        this.friendOfList = new ArrayList<>();
    }

    public CatBuilder id(int id) {
        this.id = id;
        return this;
    }

    public CatBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CatBuilder birthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public CatBuilder color(String color) {
        this.color = color;
        return this;
    }

    public CatBuilder breed(Breed breed) {
        this.breed = breed;
        return this;
    }

    public CatBuilder friendsList(List<Cat> friendsList) {
        this.friendsList = friendsList;
        return this;
    }

    public CatBuilder friendOfList(List<Cat> friendOfList) {
        this.friendOfList = friendOfList;
        return this;
    }

    public CatBuilder owner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public Cat build() {
        return new Cat(id, name, birthday, breed, color, friendsList, friendOfList, owner);
    }
}
