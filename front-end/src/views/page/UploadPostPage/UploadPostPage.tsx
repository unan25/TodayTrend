import React, { useEffect, useState } from "react";

// componenet
import TextWithHashtag from "../../components/TextWithHashtag/TextWithHashtag";
import PostDropZone from "../../components/dropzone/PostDropZone/PostDropZone";
import CategoryBox from "../../components/CateogoryBox/CategoryBox";

// axios
import axios from "axios";

// styles
import styles from "./UploadPostPage.module.css";

// redux
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

const CreatePostPage: React.FC = () => {
  const UUID = useSelector((state: RootState) => state.user.UUID);

  const [Content, setContent] = useState("");
  const [hashTag, setHashTag] = useState({
    user: [""],
    fashion: [""],
  });
  const [Images, setImages] = useState<File[]>([]);
  const formData = new FormData();

  // Send files to the server using Axios

  function tagSplit(content: string) {
    const fashionTag: string[] = [];
    const userTag: string[] = [];

    const temp = content.split(" ");

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
      };

      console.log(postData);

      const postResponse = await axios.post("/api/post", postData, {
        cancelToken: source.token,
      });

      Images.forEach((image) => {
        formData.append("images", image);
      });

      formData.append("postId", postResponse.data);

      const imageResponse = await axios.post("/api/image/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        cancelToken: source.token,
      });

      console.log(imageResponse);
    } catch (err) {
      if (axios.isCancel(err)) {
        console.log("Request canceled");
      } else {
        console.log(err);
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
            <CategoryBox />
          </div>
        </div>
        <button disabled={Images.length === 0}>업로드</button>
      </form>
    </div>
  );
};

export default CreatePostPage;
