import React, { ReactNode, useEffect, useRef, useState } from 'react';

// axios
import axios from 'axios';

// state
import { useSelector } from 'react-redux';
import { RootState } from 'redux/store';

// style
import styles from './CommentsBox.module.css';

// components
import MainComment from '../MainCommnet/MainComment';
import TextWithHashtag from '../../../../views/components/post/TextWithHashtag/TextWithHashtag';

type Comment = {
  commentId: number;
  content: string;
  createAt: Date;
  parentId?: number;
  uuid: string;
  nickname: string;
  profileImage: string;
  hasNext: boolean;
};

type CommentUplaod = {
  postId: string;
  uuid: string;
  parentId: number | undefined;
  content: string;
  userTagList: string[];
};

type Props = {
  postId: string | undefined;
  reCount?: () => void;
};

const CommentsBox: React.FC<Props> = ({ postId, reCount }) => {
  // store
  const UUID = useSelector((state: RootState) => state.user.UUID);

  // state

  const [myComments, setMyComments] = useState<Comment[]>([]);

  const [mainComments, setMainComments] = useState<Comment[]>([]);

  const [content, setContent] = useState('');

  const [parentCommentId, setParentCommentId] = useState<number>();

  const [page, setPage] = useState<number>(0);

  const [hasNext, setHasNext] = useState<boolean>(true);

  /* ============================================================================ */

  // infinite scroll

  const lastCommentRef = useRef<HTMLDivElement>(null);

  // Intersection Observer를 생성
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (
          entry.isIntersecting &&
          hasNext &&
          entry.target === lastCommentRef.current
        ) {
          // 페이지 증가 및 댓글 불러오기
          setTimeout(() => {
            setPage((prevPage) => prevPage + 1);
          }, 500);
        }
      });
    },
    { threshold: 0.8 } // 나타날 때 반응할 기준
  );

  // Ref에 Observer를 연결
  useEffect(() => {
    if (lastCommentRef.current) {
      observer.observe(lastCommentRef.current);
    }

    // 컴포넌트가 언마운트되면 옵저버 해제
    return () => {
      if (lastCommentRef.current) {
        observer.unobserve(lastCommentRef.current);
      }
    };
  }, [lastCommentRef, observer]);

  // rendering MainComments
  const renderMyComments = () => {
    try {
      const comments: ReactNode[] = [];

      myComments?.map((e, i) => {
        comments.push(
          <MainComment
            type="me"
            parentId={parentCommentId}
            key={e.commentId}
            comment={e}
            setParentComment={setParentCommentId}
            reRender={getMyComments}
            reCount={reCount}
          />
        );
      });

      return comments;
    } catch (err) {
      console.error(err);
    }
  };

  const renderMainComments = () => {
    try {
      const comments: ReactNode[] = [];

      mainComments.map((e, i) => {
        comments.push(
          <>
            <MainComment
              parentId={parentCommentId}
              key={e.commentId}
              comment={e}
              setParentComment={setParentCommentId}
            />
            <div
              key={e.commentId + '_' + i}
              ref={i === mainComments.length - 1 ? lastCommentRef : null}
            ></div>
          </>
        );
      });

      return comments;
    } catch (err) {
      console.error(err);
    }
  };

  // comment
  function tagSplit(content: string) {
    const userTag: string[] = [];

    const temp = content.split(/\s+/);

    temp.map((e) => {
      if (e.includes('@')) {
        userTag.push(e.substring(1));
      }
    });

    return userTag;
  }

  /* ============================================================================ */

  // axios

  const getMyComments = async () => {
    try {
      const params = { postId: postId };

      const response = await axios.get(`/api/post/comments/${UUID}`, {
        params: params,
      });

      setMyComments(response.data.commentList);
    } catch (err) {
      console.error(err);
    }
  };

  const getMainComments = async (page: number) => {
    try {
      const params = { postId: postId, page: page, size: 5, uuid: UUID };

      const response = await axios.get('/api/post/comments', {
        params: params,
      });

      // hasNext 업데이트
      setHasNext(response.data.hasNext);

      // 기존 댓글 리스트에 새로운 댓글 추가
      setMainComments((prevComments) => [
        ...prevComments,
        ...response.data.commentList,
      ]);

      console.log(page, hasNext);
    } catch (err) {
      console.error(err);
    }
  };

  const postComment = async () => {
    if (content.length <= 0) {
      alert('댓글을 작성해주세용!');
      return;
    }

    try {
      const data: CommentUplaod = {
        postId: postId!,
        uuid: UUID,
        parentId: parentCommentId,
        content: content,
        userTagList: tagSplit(content),
      };

      const response = await axios.post('/api/post/comments', data);

      if (response.status === 201) {
        setContent('');
        setParentCommentId(undefined);
        getMyComments();
        getMainComments(0);
        if (reCount) reCount();
      }
    } catch (err) {
      console.error(err);
    }
  };

  /* ============================================================================ */

  useEffect(() => {
    getMyComments();
    getMainComments(page);
  }, [postId, page]);

  return (
    <div className={styles.component_body}>
      <div className={styles.commentsBox}>
        {myComments.length > 0 && renderMyComments()}
        {mainComments.length > 0 && renderMainComments()}
      </div>
      <TextWithHashtag
        type="comment"
        content={content}
        setContent={setContent}
        submitComment={postComment}
        placeHolder={parentCommentId ? '답글 달기' : '댓글 쓰기'}
      />
    </div>
  );
};

export default CommentsBox;
