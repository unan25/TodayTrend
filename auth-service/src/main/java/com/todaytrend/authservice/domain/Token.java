package com.todaytrend.authservice.domain;

import jakarta.persistence.*;
import jakarta.ws.rs.ext.ParamConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "token")
@Getter @AllArgsConstructor @Builder
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;

    private String token; // JWT token

    @OneToOne
    @JoinColumn(name = "uuid", referencedColumnName = "uuid")
    private LocalUser localUser;


}
