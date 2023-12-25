import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import axios from "axios";
import styles from "./EditPostPage.module.css";
// componenet
import TextWithHashtag from "../../components/post/TextWithHashtag/TextWithHashtag";
import PostDropZone from "../../components/common/DropZone/PostDropZone/PostDropZone";
import CategoryBox from "../../components/category/CateogoryBox/CategoryBox";

type PostDetail = {
  postId: number;
  postUserUUID: string;
  profileImage: string;
  nickName: string;
  content: string;
  createdAt: string;
  postImgs: string[];
};


const EditPostPage: React.FC = () => {
  const [Content, setContent] = useState("");
  const [hashTag, setHashTag] = useState({
    user: [""],
    fashion: [""],
  });
  const [Images, setImages] = useState<File[]>([]);
  const [category, setCategory] = useState<number[]>([]);
  const [postDetail, setPostDetail] = useState<PostDetail | null>(null);
  const formData = new FormData();
  const [isUnsavedChanges, setIsUnsavedChanges] = useState(false);
  const navigate = useNavigate();
  const location = useLocation(); // useLocation 추가


  const { postId } = useParams();

  useEffect(() => {

    const getPostDetails = async () => {

      try {
        // location에서 state를 가져와서 postDetail로 설정
        const { state } = location;
        console.log("Data from state:", state.postDetail);

        if (state && state.postDetail) {
          setPostDetail(state.postDetail);
          setContent(state.postDetail.content);
          setHashTag({
            user: state.postDetail.userTagList || [""],
            fashion: state.postDetail.hashTagList || [""],
          });
          setCategory(state.postDetail.categoryIdList || []);
          // setImages(state.postDetail.postImgs || []);
        } else {
          // state가 없을 경우 서버에서 데이터를 가져옴
          const response = await axios.get(`/api/post/postdetail?postId=${postId}`);
          setPostDetail(response.data);
          setContent(response.data.content);
          setHashTag({
            user: response.data.userTagList || [""],
            fashion: response.data.hashTagList || [""],
          });
          setCategory(response.data.categoryIdList || []);
          setImages(response.data.postImgs || []);
        }
      } catch (err) {
        console.error(err);
      }
    };

    getPostDetails();
  }, [postId,location]);


  function tagSplit(content: string) {
    const fashionTag: string[] = [];
    const userTag: string[] = [];

    const temp = content.split(/\s+/);

    temp.map((e) => {
      if (e.includes("@")) {
        userTag.push(e.substring(1));
      }
      if (e.includes("#")) {
        fashionTag.push(e.substring(1));
      }
    });

    return [fashionTag, userTag];
  }


  const onEditSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const source = axios.CancelToken.source();

    try {
      if (Images.length === 0) {
        alert("이미지를 업로드해주세요");
        return;
      }

      formData.delete("images");

      const [fashion, user] = tagSplit(Content);

      const editData = {
        postId: postDetail?.postId,
        content: Content,
        hashTagList: fashion,
        userTagList: user,
        categoryIdList: category,
      };

      const editResponse = await axios.put("/api/post", editData, {
        cancelToken: source.token,
      });

      Images.forEach((image) => {
        formData.append("images", image);
      });

      formData.append("postId", editResponse.data.postId);

      const imageResponse = await axios.post("/api/images", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        cancelToken: source.token,
      });

      console.log(imageResponse);
      setIsUnsavedChanges(false); // 저장 후 변경사항 없음으로 표시
      navigate(`/post/${postId}`); // 수정 완료 후 이전 페이지로 이동
    } catch (err) {
      if (axios.isCancel(err)) {
        console.log("Request canceled");
      } else {
        console.log(err);
      }
    }
  };

  const handleLeavePage = () => {
    if (isUnsavedChanges && window.confirm("저장되지 않은 변경 사항이 있습니다. 정말로 나가시겠습니까?")) {
      navigate(-1);
    } else {
      navigate(`/post/${postId}`);
    }
  };

  return (
    <div className="page-body">
      <form className={styles.uploadForm} onSubmit={onEditSubmit}>
        <div className={styles.section1}>
          <PostDropZone setImages={setImages} />
          <div className={styles.section2}>
            <TextWithHashtag
              type="content"
              content={Content}
              setContent={setContent}
            />
            <CategoryBox setCategory={setCategory} />
          </div>
        </div>
        <button type="button" onClick={handleLeavePage}>미 저장</button>
        <button type="submit">수정 완료</button>
      </form>
    </div>
  );

};

export default EditPostPage;
