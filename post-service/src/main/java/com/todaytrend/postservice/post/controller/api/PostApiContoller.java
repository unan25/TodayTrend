package com.todaytrend.postservice.post.controller.api;

import com.todaytrend.postservice.post.entity.HashTag;
import com.todaytrend.postservice.post.repository.HashTagRepository;
import com.todaytrend.postservice.post.service.api.PostApiService;
import com.todaytrend.postservice.post.service.api.PostApiServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class PostApiContoller {

  /*  private final PostApiService postApiService;
    private final PostApiServiceImpl se;*/

 /*   @GetMapping("")
    public ResponseEntity<?> testException() throws RuntimeException{
//        throw new CustomException(ErrorEnum.NO_RIGHT);
//        return new ResponseEntity<>(new ResponseDto(SuccessEnum.OK,new RequestDeleteReadPostDto(1l,"test")),HttpStatus.OK);
        return new ResponseEntity<>(se.testFeign(), HttpStatus.OK);
    }*/

    private final HashTagRepository hashTagRepo;

  /*  @GetMapping("{test}")
    public ResponseEntity<?> test(@PathVariable("test")String test){
        System.out.println("---------------------"+test);
        HashTag hashTag = hashTagRepo.save(new HashTag(test,1L));
        System.out.println(hashTag.getHashtag());
        System.out.println(Normalizer.normalize(hashTag.getHashtag(),Normalizer.Form.NFC));
        return new ResponseEntity<>(hashTag.getHashtag(),HttpStatus.OK);
    }

    @GetMapping("a/{test2}")
    public ResponseEntity<?> test2(@PathVariable("test2")String test2){

        List<String> hashTagByKeyword = hashTagRepo.findHashTagByKeyword(Normalizer.normalize(test2,Normalizer.Form.NFD));

        System.out.println("!!!!!!!!!!!!!!!!!!!!!");
        for (String a : hashTagByKeyword){
            System.out.println(Normalizer.normalize(a,Normalizer.Form.NFC));
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!");

        return new ResponseEntity<>(hashTagByKeyword,HttpStatus.OK);
    }
*/



/*    @Service
    @Transactional
    private class testService{

        public String test(String test){
            System.out.println("+++++++++++++++++++++++"+test);
            HashTag hashTag = hashTagRepo.save(new HashTag(test,1L));
            System.out.println(hashTag);
            return hashTag.getHashtag();
        }

    }*/

}