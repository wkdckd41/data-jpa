package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    @PersistenceContext // JPA의 EntityManager를 주입받을 수 있음
    private EntityManager em; // JPA의 모든 기능을 가지고 있음

    public Member save(Member member) { // 저장
        em.persist(member); // 영속성 컨텍스트에 저장
        return member;
    }

    public void delete(Member member) { // 삭제
        em.remove(member);
    }

    public List<Member> findAll() { // 전체 조회
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id) { // 단건 조회
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count() { // 카운트
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }

    public Member find(Long id) { // 수정
        return em.find(Member.class, id);
    }
}
