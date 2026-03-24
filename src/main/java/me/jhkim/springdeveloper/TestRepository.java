package me.jhkim.springdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Member, Long> {
    Long id(Long id);

    Long Id(Long id);
}
