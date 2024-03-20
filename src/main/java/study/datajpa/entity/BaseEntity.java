package study.datajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // JPA 이벤트를 수신해서 처리하는 클래스
@MappedSuperclass // JPA Entity 클래스들이 BaseEntity를 상속받는 경우 필드들도 컬럼으로 인식
@Getter
public class BaseEntity extends BaseTimeEntity {

    @CreatedDate // 엔티티가 생성되어 저장될 때 자동으로 생성일자를 업데이트
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate // 조회한 후에 수정이 일어날 때 자동으로 수정일자를 업데이트
    private LocalDateTime lastModifiedDate;

//    @CreatedBy
//    @Column(updatable = false)
//    private String createdBy;
//
//    @LastModifiedDate
//    private String lastModifiedBy;
}
