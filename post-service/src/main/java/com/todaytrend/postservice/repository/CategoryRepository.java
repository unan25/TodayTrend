package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer>{
}


/*
* package com.todaytrend.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class postserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(postserviceApplication.class, args);
    }

}

*
* */