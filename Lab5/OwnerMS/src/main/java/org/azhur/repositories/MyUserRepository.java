package org.azhur.repositories;

import org.azhur.jpaEntities.MyUserJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUserJpa, Integer> {
    MyUserJpa findByEmail(String email);
}
