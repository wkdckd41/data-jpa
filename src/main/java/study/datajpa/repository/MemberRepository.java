package study.datajpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy(); // 메서드 이름만으로 쿼리 생성 가능

    List<Member> findByUsername(@Param("username") String username); // 메서드 이름만으로 쿼리 생성 가능

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO로 조회
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> fidMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names); // 메서드 이름만으로 쿼리 생성 가능

    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m.username) from Member m") // 카운트 쿼리 분리
    Page<Member> findByAge(int age, Pageable pageable); // 페이징

    @Modifying(clearAutomatically = true) // @Modifying 어노테이션을 사용하면 executeUpdate()를 실행하고, 영속성 컨텍스트를 초기화
    // clearAutomatically = true로 설정하면 영속성 컨텍스트를 자동으로 초기화
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin(); // 페치 조인

    @Override
    @EntityGraph(attributePaths = {"team"}) // 연관된 엔티티를 함께 조회
    List<Member> findAll(); // 페치 조인

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

//    @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all") // 엔티티 그래프 사용
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) // 읽기 전용 옵션을 사용하면 해당 영속성 컨텍스트에서 해당 엔티티를 수정할 수 없음
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // 특정 쿼리에 락을 걸 수 있음
    List<Member> findLockByUsername(String username);

    List<NestedClosedProjections> findProjectionsByUsername(@Param("username") String username, Class<NestedClosedProjections> nestedClosedProjectionsClass);

    @Query(value = "select * from member where username = ?", nativeQuery = true)
    Member findByNativeQuery(String username);

    @Query(value = "select m.member_id as id, m.username, t.name as teamName" +
            " from member m left join team t",
            countQuery = "select count(*) from member",
            nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);
}
