import React, { useCallback, useEffect, useState } from "react";

import styles from "./ProfileDropZone.module.css";

// react-dropzone
import { useDropzone } from "react-dropzone";

type Props = {
  image: File[];
  setFunction: (file: File[]) => void;
};

const ProfileDropZone: React.FC<Props> = ({ setFunction, image }) => {
  const [imageURL, setImageURL] = useState<string>("");
  //------------------------------------------------------------------------------\
  // dropzone config
  const onDrop = useCallback(
    (acceptedFiles: File[]) => {
      setFunction([...acceptedFiles]);
      console.log(acceptedFiles);
    },
    [setFunction]
  );

  const { getRootProps, getInputProps } = useDropzone({ onDrop });

  useEffect(() => {
    if (image && image.length > 0) {
      const urls = URL.createObjectURL(image[0]);
      setImageURL(urls);
      return () => URL.revokeObjectURL(urls);
    }
  }, [image]);

  //------------------------------------------------------------------------------

  return (
    <div className={styles.dropzone}>
      <img src={imageURL} alt="" className={styles.dropzone__img} />
      <div {...getRootProps()} className={styles.dropzone__drop}>
        <input {...getInputProps()} />
      </div>
      프로필 이미지
    </div>
  );
};

export default ProfileDropZone;
