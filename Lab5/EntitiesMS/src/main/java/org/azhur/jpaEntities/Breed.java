package org.azhur.jpaEntities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "breeds")
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="breed_id")
    @Getter
    private Long id;

    @Column(name = "type_of_breed")
    @Getter
    private String breedName;

    public Breed(String breed) {
        this.breedName = breed;
    }

    public Breed(long id, String breed) {
        this.id = id;
        this.breedName = breed;
    }

    public Breed() {

    }
}
