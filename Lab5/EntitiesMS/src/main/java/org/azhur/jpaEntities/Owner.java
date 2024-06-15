package org.azhur.jpaEntities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owners")
public class Owner{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name="owner_id")
    private Integer id;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "lastname")
    @Getter @Setter
    private String lastName;

    @Column(name = "birthday")
    @Getter
    private Date birthday;

    @Column(name = "passport_number")
    @Getter@Setter
    private String passportNumber;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @Getter @Setter
    private List<Cat> catsList = new ArrayList<>();

    @Builder
    public Owner(String name, String lastName, Date birthday, List<Cat> catsList, String passportNumber) {
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.catsList = catsList;
        this.passportNumber = passportNumber;
    }

    public Owner(int id, String name, String lastName, Date birthday, List<Cat> catsList, String passportNumber) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.catsList = catsList;
        this.passportNumber = passportNumber;
    }

    public void addCat(Cat cat) {
        cat.setOwner(this);
        catsList.add(cat);
    }

    public void removeCat(Cat cat) {
        catsList.remove(cat);
    }



}
