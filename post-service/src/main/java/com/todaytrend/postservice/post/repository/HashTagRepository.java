package com.todaytrend.postservice.post.repository;

import com.todaytrend.postservice.post.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface HashTagRepository extends JpaRepository<HashTag,Long>{

    void deleteAllByPostId(Long postId);

    @Query(value = "SELECT DISTINCT h.hashtag FROM HashTag h WHERE h.hashtag LIKE CONCAT('%', :keyword ) ")
    List<String> findHashTagByKeyword(String keyword);

    @Query(value = "SELECT DISTINCT h.postId FROM HashTag h WHERE h.hashtag = :hashtag ")
    Page<Long> findPostIdByHashtag(String hashtag, PageRequest pageRequest);

    default List<String> keywordSlice(String keyword){

        StringBuilder result = new StringBuilder();

        for (char c : keyword.toCharArray()) {
            String decomposed = Normalizer.normalize(String.valueOf(c), Normalizer.Form.NFD)+"%";
            result.append(decomposed);
        }

        return findHashTagByKeyword(result.toString());
    }

}
