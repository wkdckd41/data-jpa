package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

@Repository
public class MemberJpaRepository {

    @PersistenceContext // JPA의 EntityManager를 주입받을 수 있음
    private EntityManager em; // JPA의 모든 기능을 가지고 있음

    public Member save(Member member) { // 저장
        em.persist(member); // 영속성 컨텍스트에 저장
        return member;
    }

    public Member find(Long id) { // 조회
        return em.find(Member.class, id);
    }

    public void delete(Member member) {
        em.remove(member);
    }
}
