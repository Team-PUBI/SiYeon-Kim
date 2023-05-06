package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    // Test 코드 작성할 때 서로 다른 객체를 이용하는 문제를 해결하기 위함.
    // DI (Dependency Injection - 의존성 주입)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //     회원 가입
    public Long join(Member member) {
//         같은 이름이 있는 중복회원 X
//         Optional<Member> result = memberRepository.findByName ( member.getName () );
//         result.orElseGet (  )
//         m : 매개변수
//        result.ifPresent ( m -> {
//            throw new IllegalStateException ( "이미 존재하는 회원입니다." );
//        } );

        validateDuplicateMember( member ); // 중복회원 검증

        memberRepository.save ( member );
        return member.getId ();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName ( member.getName () )
                .ifPresent ( m -> {
                    throw new IllegalStateException ( "이미 존재하는 회원입니다." );
                } );
    }

//     전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll ();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById ( memberId );
    }
}
