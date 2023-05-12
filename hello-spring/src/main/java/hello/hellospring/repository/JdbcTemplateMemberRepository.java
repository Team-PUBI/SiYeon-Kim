package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JdbcTemplateMemberRepository implements MemberRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate ( dataSource );
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert ( jdbcTemplate );
        jdbcInsert.withTableName ( "member" ).usingGeneratedKeyColumns ( "id" );

        Map<String, Object> parameters = new HashMap<> ();
        parameters.put ( "name", member.getName () );

        Number key = jdbcInsert.executeAndReturnKey ( new MapSqlParameterSource ( parameters ) );
        member.setId ( key.longValue () );

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query ( "select * from member where id = ?", memberRowMapper (), id );
        return result.stream ().findAny (); // 이 코드는 데이터베이스에서 가져온 회원 정보를 스트림으로 변환한 뒤, 그 중 임의의 하나를 반환하는 것입니다. 만약 결과가 비어있다면 Optional.empty()를 반환합니다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query ( "select * from member where name = ?", memberRowMapper (), name );
        return result.stream ().findAny ();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query ( "select * from member", memberRowMapper () );
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member ();
            member.setId ( rs.getLong ( "id" ) );
            member.setName ( rs.getString ( "name" ) );

            return member;
        };
    }
}
