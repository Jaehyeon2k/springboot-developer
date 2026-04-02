package me.jhkim.springdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByName(String name);

    // save(), findById(id),findAll() ...

}
