package com.todaytrend.postservice.comment.service;

import com.todaytrend.postservice.comment.dto.request.RequestCommentDto;
import com.todaytrend.postservice.comment.dto.request.RequestCommentLikeDto;
import com.todaytrend.postservice.comment.dto.request.RequestDeleteCommentDto;
import com.todaytrend.postservice.comment.dto.response.ResponseCommentDto;
import com.todaytrend.postservice.comment.dto.response.ResponseCommentLikeDto;
import com.todaytrend.postservice.comment.dto.response.ResponseCommentLikeUserDto;
import com.todaytrend.postservice.comment.dto.response.ResponseCommentListDto;
import com.todaytrend.postservice.comment.entity.Comment;
import com.todaytrend.postservice.comment.entity.CommentLike;
import com.todaytrend.postservice.comment.entity.CommentTag;
import com.todaytrend.postservice.comment.feignClient.UserCommentFeignClient;
import com.todaytrend.postservice.comment.feignClient.dto.UserCommentFeignDto;
import com.todaytrend.postservice.comment.repository.CommentLikeRepository;
import com.todaytrend.postservice.comment.repository.CommentRepository;
import com.todaytrend.postservice.comment.repository.CommentTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentTagRepository commentTagRepository;
     private final UserCommentFeignClient userCommentFeignClient;

    //---------------------------댓글 검증--------------------------

    //나의 댓글인지 확인하기 - 내꺼면 true , 아니면 false
    public boolean isMyComment(Comment comment, String userUuid){
        return comment.getUuid().equals(userUuid);
    }

    //대댓글인지 확인하기 - 대댓글이면 true , 아니면 false
    public boolean isReplyComment(Comment comment) {
        return comment.getParentId() != null;
    }

    //대댓글이 있는지 확인하기 - 있으면 true ,없으면 false
    public boolean hasReplyComments(Comment comment) {
        Long commentId = comment.getCommentId();
        List<Comment> byParentId = commentRepository.findByParentId(commentId);
        System.out.println("byParentId = " + byParentId);
        return !byParentId.isEmpty();
    }

    //---------------------------댓글 등록--------------------------

    //기본 댓글 등록
    public ResponseCommentDto createComment(RequestCommentDto requestCommentDto) {
        Comment comment = requestCommentDto.toEntity();
        commentRepository.save(comment);

        //댓글 태그 등록
        makeCommentTag(requestCommentDto.getUserTagList() , comment.getCommentId());

        return ResponseCommentDto.builder()
                .createAt(comment.getCreateAt())
                .content(comment.getContent())
                .commentId(comment.getCommentId())
                .uuid(comment.getUuid())
                .build();
    }

    //---------------------------댓글 조회--------------------------

    // postId로 부모 댓글만 조회 /페이징 처리
    public ResponseCommentListDto findParentCommentByPostId(Long postId, int page, int size, String uuid) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Comment> commentPage = commentRepository.findCommentsByPostIdAndParentIdIsNullAndUuidNot(postId, uuid,pageRequest);
        List<Comment> comments = commentPage.getContent();
        List<ResponseCommentDto> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            // 유저 프로필 이미지랑 닉네임 얻어 오는 feign 실행
            UserCommentFeignDto userCommentFeignDto = userCommentFeignClient.findImageAndNickname(comment.getUuid());
            String nickname = userCommentFeignDto.getNickname();
            String profileImage = userCommentFeignDto.getProfileImage();

            ResponseCommentDto build = ResponseCommentDto.builder()
                    .createAt(comment.getCreateAt())
                    .content(comment.getContent())
                    .commentId(comment.getCommentId())
                    .uuid(comment.getUuid())
                    .nickname(nickname)
                    .profileImage(profileImage)
                    .build();
            commentList.add(build);
        }
        return ResponseCommentListDto.builder()
                .commentList(commentList)
                .page(page)
                .totalPages(commentPage.getTotalPages())
                .build();
    }
    // commentId로 대댓글 조회
    public ResponseCommentListDto findCommentByCommentId(Long commentId, int page, int size) {
        List<Comment> comments = commentRepository.findByParentId(commentId);

        List<ResponseCommentDto> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            // 유저 프로필 이미지랑 닉네임 얻어 오는 feign 실행
            UserCommentFeignDto userCommentFeignDto = userCommentFeignClient.findImageAndNickname(comment.getUuid());

            String nickname = userCommentFeignDto.getNickname();
            String profileImage = userCommentFeignDto.getProfileImage();

            ResponseCommentDto build = ResponseCommentDto.builder()
                    .createAt(comment.getCreateAt())
                    .content(comment.getContent())
                    .commentId(comment.getCommentId())
                    .uuid(comment.getUuid())
                    .nickname(nickname)
                    .profileImage(profileImage)
                    .build();
            commentList.add(build);
        }
        return ResponseCommentListDto.builder()
                .commentList(commentList)
                .build();
    }
    // 총 댓글수 조회
    public Long getTotalCount(Long postId) {
       return commentRepository.countByPostId(postId);
    }

    // 대댓글수 조회
    public Long getReplyCount(Long commentId) {
        return commentRepository.countByParentId(commentId);
    }

    // 내가 쓴 댓글 조회
    public ResponseCommentListDto findMyComment(Long postId, String uuid){
        List<ResponseCommentDto> commentList = new ArrayList<>();
        List<Comment> comments = commentRepository.findByPostIdAndUuidAndParentIdIsNull(postId, uuid);

        for (Comment comment : comments) {
            ResponseCommentDto build = ResponseCommentDto.builder()
                    .createAt(comment.getCreateAt())
                    .content(comment.getContent())
                    .commentId(comment.getCommentId())
                    .uuid(comment.getUuid())
                    .build();
            commentList.add(build);
        }
        return ResponseCommentListDto.builder()
                .commentList(commentList)
                .build();
    }

    //---------------------------댓글 삭제--------------------------

    // commentId로 선택한 댓글 삭제
    @Transactional
    public String deleteCommentByCommentId(RequestDeleteCommentDto requestDeleteCommentDto) {
        String userUuid = requestDeleteCommentDto.getUserUuid();
        Long commentId = requestDeleteCommentDto.getCommentId();
        Comment comment = commentRepository.findByCommentId(commentId);

        // 1. 내가 쓴 댓글이고 , 대댓글이 아니고, 대댓글이 없으면 찐 삭제
        if(isMyComment(comment, userUuid) && !isReplyComment(comment) && !hasReplyComments(comment)) {
            commentRepository.delete(comment);
            return "commentId =" +commentId + "삭제 완료";
        }
        // 2. 내가 쓴 댓글이고, 대댓글이 아니고, 대댓글이 있으면 댓글 수정
        if (isMyComment(comment, userUuid) && !isReplyComment(comment) && hasReplyComments(comment)) {
            comment.updateContent("삭제된 댓글입니다.");

            return "commentId =" +commentId + "내용 수정 완료";
        }
        // 3. 내가 쓴 댓글이고, 대댓글이면 찐 삭제
        if(isMyComment(comment,userUuid) && isReplyComment(comment)) {
            commentRepository.delete(comment);
            return "commentId =" +commentId + "삭제 완료";
        }
        return "삭제 요청 잘못 됨" ;
    }

    //postId로 조회한 모든 댓글 삭제
    @Transactional
    public String deleteCommentByPostId(Long postId) {
        commentRepository.deleteAllByPostId(postId);
        return postId +"인 댓글 삭제 완료.";
    }
    //---------------------------댓글 좋아요--------------------------
    // 댓글 좋아요 등록/삭제
    @Transactional
    public ResponseCommentLikeDto commentLike(RequestCommentLikeDto requestCommentLikeDto) {
        Long commentId = requestCommentLikeDto.getCommentId();
        String uuid = requestCommentLikeDto.getUuid();

        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUuid(commentId, uuid);

        boolean isLiked = commentLike != null;
        // 좋아요 삭제
        if (isLiked) {
            commentLikeRepository.delete(commentLike);

            return ResponseCommentLikeDto.builder()
                    .commentId(commentId)
                    .isLiked(false)
                    .build();
        }
        // 좋아요 등록
        if (!isLiked) {
            commentLikeRepository.save(CommentLike.builder().uuid(uuid).commentId(commentId).build());

            return ResponseCommentLikeDto.builder()
                    .commentId(commentId)
                    .isLiked(true)
                    .build();
        }
        // if 실행 안되면 예외 처리
        throw new IllegalStateException("좋아요 등록/삭제 에러 발생");
    }
    //댓글 좋아요 수 조회  return Integer
    public Long getLikeCount(Long commentId) {
       return commentLikeRepository.countByCommentId(commentId);
    }

    //댓글 좋아요 내가 눌렀는지 조회 return boolean
    public boolean checkLike(RequestCommentLikeDto requestCommentLikeDto) {
        return commentLikeRepository.existsByCommentIdAndUuid
                (requestCommentLikeDto.getCommentId(),
                requestCommentLikeDto.getUuid());
    }

    // 댓글 좋아요 목록 조회
    public ResponseCommentLikeUserDto getCommentLikeUserList(Long commentId) {
        return ResponseCommentLikeUserDto.builder()
                .uuidList(commentLikeRepository.findUuidByCommentId(commentId))
                .build();
    }
    //---------------------------댓글 태그--------------------------
    // 댓글 태그 등록
    public void makeCommentTag(List<String> userTag ,Long commentId) {
        for (String nickname : userTag) {
            commentTagRepository.save(CommentTag.builder()
                            .nickname(nickname)
                            .commentId(commentId)
                    .build());
        }
    }
//    // 댓글 태그 조회
//    public List<String> getCommentTag(Long commentId) {
//        List<String> uuidList = commentTagRepository.findByUuidCommentId(commentId);
//        List<String> userTagList = new ArrayList<>();
//
//        for (String uuid : uuidList) {
//            // uuid를 nickname으로 변환하는 feign
//            String nickname = uuid;
//            userTagList.add(nickname);
//        }
//        return userTagList;
//    }


}
