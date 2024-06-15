package org.azhur.jpaEntities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cats")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name="cat_id")
    private Integer id;

    @Column (name = "name")
    @Getter@Setter
    private String name;

    @Column(name = "birthday")
    @Getter@Setter
    private Date birthday;

    @Column(name = "color")
    @Getter@Setter
    private String color;

    @ManyToOne
    @JoinColumn(name = "breed", referencedColumnName = "breed_id")
    @Getter@Setter
    private Breed breed;

    @ManyToMany
    @JoinTable(name = "cats_relations",
            joinColumns = @JoinColumn(name="cur_cat_id"),
            inverseJoinColumns = @JoinColumn(name="cat_friends_id"))
    @Getter@Setter
    private List<Cat> friendsList;

    @ManyToMany
    @JoinTable(name="cats_relations",
            joinColumns = @JoinColumn(name="cat_friends_id"),
            inverseJoinColumns = @JoinColumn(name="cur_cat_id"))
    @Getter@Setter
    private List<Cat> friendOfList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @Getter
    private Owner owner;

    public Cat(String name, Date birthday, Breed breed, String color) {
        this.name = name;
        this.birthday = birthday;
        this.breed = breed;
        this.color = color;
        this.friendsList = new ArrayList<>();
        this.friendOfList = new ArrayList<>();

    }

    public Cat(int id, String name, Date birthday, Breed breed, String color) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.breed = breed;
        this.color = color;
        this.friendsList = new ArrayList<>();
        this.friendOfList = new ArrayList<>();

    }



    public Cat(int id, String name, Date birthday, Breed breed, String color, List<Cat> friendsList, List<Cat> friendOfList, Owner owner) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.breed = breed;
        this.color = color;
        this.friendsList = friendsList;
        this.friendOfList = friendOfList;
        this.owner = owner;
    }

    public Cat() {

    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void addFriend(Cat cat) {
        friendsList.add(cat);
        friendOfList.add(cat);
    }

}
