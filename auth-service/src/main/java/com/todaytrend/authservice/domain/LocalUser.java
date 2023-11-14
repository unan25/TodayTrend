package com.todaytrend.authservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "localuser",
        indexes = {@Index(name = "index_uuid", columnList = "uuid", unique = true)})
//@Table 어노테이션에 indexes 속성을 추가, @Index 어노테이션을 사용하여 uuid 컬럼에 인덱스를 추가
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LocalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long local_user_id;

    private String email;

    private String password;

    @Column(unique = true)
    private String uuid;
}
