package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<> ();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId ( ++sequence );
        store.put ( member.getId (), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable ( store.get ( id ) );
    }

    @Override
    public Optional<Member> findByName(String name) {
        // Stream API는 Java에서 컬렉션, 배열 등의 데이터를 처리하기 위한 새로운 방식을 제공.
        // Collection이나 배열등의 인자들을 하나씩 처리하는 것이 아닌 filter를 이용해서 바로 값을 추출하기 위한 전단계.
        return store.values ().stream ()
                .filter ( member -> member.getName ().equals ( name ) )
                .findAny ();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<> (store.values ());
    }

    public void clearStore() {
        store.clear ();
    }
}
