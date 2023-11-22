import React, { useEffect, useState } from "react";
import TextWithHashtag from "../../components/TextWithHashtag/TextWithHashtag";
import PostDropZone from "../../components/DropZone/PostDropZone/PostDropZone";
import styles from "./UploadPostPage.module.css";
import axios from "axios";
import { useSelector } from "react-redux";
// State
import { RootState } from "redux/store";

const CreatePostPage: React.FC = () => {
  const UUID = useSelector((state: RootState) => state.user.UUID);

  const [Content, setContent] = useState("");
  const [HashTag, setHashTag] = useState({
    user: [""],
    fashion: [""],
  });
  const [Images, setImages] = useState<File[]>([]);
  const formData = new FormData();

  useEffect(() => {}, [Images]);
  // Send files to the server using Axios

  const onSumitHandle = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    formData.delete("images");    

    formData.append("content", Content);
    formData.append("UUID", UUID);
    
    

    const postResponse = await axios.post("/api/post", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    Images.forEach((image) => {
      formData.append("images", image);
    });
    

    formData.append('postId', postResponse.data)

    const imageResponse = await axios.post("/api/image/upload", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      }, 
    });

    console.log(imageResponse);
    
  };

  return (
    <div className="page-body">
      <form className={styles.uploadForm} onSubmit={onSumitHandle}>
        <div className={styles.section1}>
          <PostDropZone setImages={setImages} />
          <TextWithHashtag
            content={Content}
            setContent={setContent}
            hashtag={HashTag}
            setHashtag={setHashTag}
          />
        </div>
        <button disabled={Images.length ? false : true}>업로드</button>
      </form>
    </div>
  );
};

export default CreatePostPage;
