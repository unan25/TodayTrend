package com.todaytrend.authservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "localuser")
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


}
