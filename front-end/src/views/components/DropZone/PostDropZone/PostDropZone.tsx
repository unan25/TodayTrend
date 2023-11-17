import React, { useCallback, useEffect, useState } from "react";
import styles from "./PostDropZone.module.css";
import { useDropzone } from "react-dropzone";

type Props = {
  setImages: React.Dispatch<React.SetStateAction<File[]>>;
};

const PostDropZone: React.FC<Props> = ({ setImages }) => {
  const [SelectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [ImageURLs, setImageURLs] = useState<string[]>();

  const onDrop = useCallback(
    (acceptedFiles: File[]) => {
      setImages((prevImages: File[]) => [...prevImages, ...acceptedFiles]);
      setSelectedFiles((prevFiles) => [...prevFiles, ...acceptedFiles]);
    },
    [setImages]
  );

  const handlePrevious = () => {
    setSelectedFiles((prevFiles) => {
      const updatedFiles = [...prevFiles];
      const currentImage = updatedFiles.pop();
      if (currentImage) {
        updatedFiles.unshift(currentImage);
      }
      return updatedFiles;
    });
  };

  const handleNext = () => {
    setSelectedFiles((prevFiles) => {
      const updatedFiles = [...prevFiles];
      const currentImage = updatedFiles.shift();
      if (currentImage) {
        updatedFiles.push(currentImage);
      }
      return updatedFiles;
    });
  };

  useEffect(() => {
    const urls = SelectedFiles.map((file) => URL.createObjectURL(file));
    setImageURLs(urls);
    return () => {
      urls.forEach((url) => URL.revokeObjectURL(url));
    };
  }, [SelectedFiles]);

  const { getRootProps, getInputProps } = useDropzone({ onDrop });

  return (
    <div className={styles.dropzone}>
      {ImageURLs?.map((url, i) => (
        <img
          key={i}
          src={url}
          alt={`Image ${i}`}
          className={styles.dropzone__img}
        />
      ))}
      <div {...getRootProps()} className={styles.dropzone__drop}>
        <input {...getInputProps()} />
      </div>
      <div className={styles.dzopzone__img__buttonBox}>
        <div className={styles.button_previous} onClick={handlePrevious}>
          이전
        </div>
        <div className={styles.button_next} onClick={handleNext}>
          다음
        </div>
      </div>
    </div>
  );
};

export default PostDropZone;
