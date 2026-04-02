package me.jhkim.springdeveloper;


import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest // @Transctional 을 포함하고 있음 ( 재실행하면 롤백됨 )
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers() {
        // given(준비)

        // when (실행)
        List<Member> members = memberRepository.findAll();

        // then (검증)
        Assertions.assertThat(members.size()).isEqualTo(3);

    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberById() {
        // given

        // when
        Member member = memberRepository.findById(2L).get();

        // then
        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberByName() {
        // given

        // when
        // 이름이 'C'인 member 검색: select * from member where name=name;
        Member member = memberRepository.findByName("C").get();

        // then
        assertThat(member.getId()).isEqualTo(3L);
    }

    @Sql("/insert-members.sql")
    @DisplayName("레코드 삽입 테스트")
    @Test
    @Transactional
    void saveMember() {
        //given
        Member m = new Member("scpark");

        // when : 레코드 삽입
        Member saveMember = memberRepository.save(m);
        // 1. Member 객체 m에 primary key 인 id가 없으면 :
//            insert into member(name) values('scpark')
        // 2. Member 객체 m에 primary key가 이미 설정되어 있으면 "
        //    update member set name = 'scpark' where id = 1;
        //    save 메서드가 성공하면 삽입된 또는 ujpdate 된 레코드를 Member 객체로 반환
        // 3. return new Member(부여된 id, "scpark");

        // then
        // Optional<Member>
        assertThat(saveMember.getId()).isNotNull(); // 삽입에 성공했는지 체크.
        // MemberRepository의 findbyId() 메서드는
        // 1. select * from member where id = :id
        // 2. return new Optional<Member>(1L, "scpark");
        Long id = saveMember.getId();
        Optional<Member> result = memberRepository.findById(id);
        Member member = result.get();
        String name = member.getName();
        assertThat(name).isEqualTo("scpark");
//        assertThat(memberRepository.findById(saveMember.getId()).get().getName()).isEqualTo("scpark"); 위 5줄과 같음




    }
    @DisplayName("2개의 레코드를 한 번에 삽입하는 테스트")
    @Test
    void saveMembers() {
        //given
        List<Member> members = List.of(new Member("HongGillDong"),
                new Member("Park MunSu"));
        //when
        memberRepository.saveAll(members);

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @DisplayName("레코드 삭제 테스트")
    @Test
    void deleteAll() {
        // given
        // when
        memberRepository.deleteAll();

        // then
        assertThat(memberRepository.findAll().size()).isZero();
    }
    @Sql("/insert-members.sql")
    @DisplayName("레코드 삭제 테스트")
    @Test
    void update() {
        // given
        Member member = memberRepository.findById(2L).get();
        // when
        member.changeName("scpark");
        memberRepository.save(member);

        // then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("scpark");
    }

}

