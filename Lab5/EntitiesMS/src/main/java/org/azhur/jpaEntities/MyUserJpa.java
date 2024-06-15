package org.azhur.jpaEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class MyUserJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String role;
    @JoinColumn(name = "owner_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Owner owner;

    public int getOwnerId() {
        return owner.getId();
    }
}
