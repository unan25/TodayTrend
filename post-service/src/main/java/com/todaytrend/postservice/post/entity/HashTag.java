package com.todaytrend.postservice.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.Normalizer;

@Entity
@NoArgsConstructor
@Table(name = "HASHTAG")
@Getter
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String hashtag;
    private Long postId;

    @Builder
    public HashTag(String hashtag, Long postId){
        this.hashtag = decomposeHangul(hashtag);
        this.postId = postId;
    }

    private static String decomposeHangul(String input) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            String decomposed = Normalizer.normalize(String.valueOf(c), Normalizer.Form.NFD);
            result.append(decomposed);
        }

        return result.toString();
    }
}
