package com.todaytrend.authservice.domain;

import com.todaytrend.authservice.domain.enum_.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "socialUser")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialUser implements UserInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialUserId;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void changeRole(Role role) {
        this.role = role;
    }

}
