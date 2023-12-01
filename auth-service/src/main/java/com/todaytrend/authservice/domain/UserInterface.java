package com.todaytrend.authservice.domain;


import com.todaytrend.authservice.domain.enum_.Role;

import java.util.Optional;

public interface UserInterface {
    String getEmail();
    String getUuid();
    Role getRole();

}
