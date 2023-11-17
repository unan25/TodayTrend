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

  const onSumitHandle = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    formData.delete("images");

    Images.forEach((image) => {
      formData.append("images", image);
    });

    formData.append("content", Content);

    formData.append("UUID", "user1");
    // formData.append("UUID", UUID);

    console.log(formData.getAll("images"));

    // const response = axios.post("YOUR_SERVER_UPLOAD_ENDPOINT", formData, {
    //   headers: {
    //     "Content-Type": "multipart/form-data",
    //   },
    // });
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
        <button>업로드</button>
      </form>
    </div>
  );
};

export default CreatePostPage;
