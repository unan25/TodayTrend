import React, { useEffect, useState } from "react";

// componenet
import TextWithHashtag from "../../components/post/TextWithHashtag/TextWithHashtag";
import PostDropZone from "../../components/common/DropZone/PostDropZone/PostDropZone";
import CategoryBox from "../../components/category/CateogoryBox/CategoryBox";

// axios
import axios from "axios";

// styles
import styles from "./UploadPostPage.module.css";

// redux
import { useSelector } from "react-redux";
import { RootState } from "redux/store";
import { useNavigate } from "react-router-dom";

const CreatePostPage: React.FC = () => {
  const navigate = useNavigate();

  // Store
  const UUID = useSelector((state: RootState) => state.user.UUID);

  // state
  const [Content, setContent] = useState("");
  const [hashTag, setHashTag] = useState({
    user: [""],
    fashion: [""],
  });
  const [Images, setImages] = useState<File[]>([]);
  const [category, setCategory] = useState<number[]>([]);
  const formData = new FormData();

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

  const onSumitHandle = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const source = axios.CancelToken.source();

    try {
      formData.delete("images");

      const [fahsion, user] = tagSplit(Content);

      const postData = {
        uuid: UUID,
        content: Content,
        hashTagList: fahsion,
        userTagList: user,
        categoryIdList: category,
      };

      const postResponse = await axios.post("/api/post", postData, {
        cancelToken: source.token,
      });

      const postId = postResponse.data.postId;

      formData.append("postId", postId);

      Images.forEach((image) => {
        formData.append("images", image);
      });

      const imageResponse = await axios.post("/api/images", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        cancelToken: source.token,
      });

      if (imageResponse.status === 200) {
        navigate(`/post/${postId}`);
      }
    } catch (err) {
      if (axios.isCancel(err)) {
        console.log("Request canceled");
      } else {
        console.error(err);
      }
    }
  };

  return (
    <div className="page-body">
      <form className={styles.uploadForm} onSubmit={onSumitHandle}>
        <div className={styles.section1}>
          <PostDropZone setImages={setImages} />
          <div className={styles.section2}>
            <TextWithHashtag
              content={Content}
              setContent={setContent}
              hashtag={hashTag}
              setHashtag={setHashTag}
            />
            <CategoryBox setCategory={setCategory} />
          </div>
        </div>
        <button disabled={Images.length === 0}>업로드</button>
      </form>
    </div>
  );
};

export default CreatePostPage;
