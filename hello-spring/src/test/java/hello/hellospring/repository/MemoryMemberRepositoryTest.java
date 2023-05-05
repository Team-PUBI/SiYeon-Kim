package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository ();

    // 테스트가 끝날 때 마다 리포지토리를 깔끔하게 비워주는 역할
    @AfterEach
    public void afterEach() {
        repository.clearStore ();
    }

    @Test
    public void save() {
        Member member = new Member ();
        member.setName ( "Spring" );

        repository.save ( member );
        Member result = repository.findById ( member.getId () ).get ();

        // System.out.println ("result = " + (result == member));
        // System.out.println ("result : " + result.getId ());
        // System.out.println ("member : " + member.getId ());

        // Assertions.assertEquals ( member, result );
        assertThat ( member ).isEqualTo ( result );
    }

    @Test
    public void findByName() {
        Member member1 = new Member ();
        member1.setName ( "Spring1" );
        repository.save ( member1 );

        Member member2 = new Member ();
        member2.setName ( "Spring2" );
        repository.save ( member2 );

        Member result = repository.findByName ( "Spring1" ).get();

        assertThat ( result ).isEqualTo ( member1 );
    }

    @Test
    public void findAll() {
        Member member1 = new Member ();
        member1.setName ( "Spring1" );
        repository.save ( member1 );

        Member member2 = new Member ();
        member2.setName ( "Spring2" );
        repository.save ( member2 );

        List<Member> result = repository.findAll ();

//        System.out.println (result.get ( 0 ).getId ());
//        System.out.println (result.get ( 0 ).getName ());
//
//        System.out.println (result.get ( 1 ).getId ());
//        System.out.println (result.get ( 1 ).getName ());

        assertThat ( result.size () ).isEqualTo ( 2 );
    }
}
