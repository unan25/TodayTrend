// state
import React, { useCallback, useEffect, useState } from "react";

// style
import styles from "./PostDropZone.module.css";

// dropzone
import { useDropzone } from "react-dropzone";

// component
import ImageCropModal from "./ImageCropModal/ImageCropModal";

type Props = {
  setImages: React.Dispatch<React.SetStateAction<File[]>>;
};

const PostDropZone: React.FC<Props> = ({ setImages }) => {
  //
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [imageURLs, setImageURLs] = useState<string[]>();
  const [URLToCrop, setURLToCrop] = useState<string | null>();
  const [imageToCrop, setImageToCrop] = useState<File | null>(null);

  //
  /* ============================================================================= */
  const onDrop = useCallback(
    (acceptedFiles: File[]) => {
      setImageToCrop(acceptedFiles[0]);
    },
    [setImageToCrop]
  );

  /* ============================================================================= */
  // controller
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

  const handleDelete = () => {
    setSelectedFiles((prevFiles) => {
      const newImages = [...prevFiles];
      newImages.pop();
      return newImages;
    });
  };
  /* ============================================================================= */

  useEffect(() => {
    setImages((prev) => [...selectedFiles]);

    const urls = selectedFiles.map((file) => URL.createObjectURL(file));
    setImageURLs(urls);
    return () => {
      urls.forEach((url) => URL.revokeObjectURL(url));
    };
  }, [selectedFiles]);

  useEffect(() => {
    setURLToCrop(null);
    if (imageToCrop) {
      const url = URL.createObjectURL(imageToCrop);

      setURLToCrop(url);
      return () => {
        URL.revokeObjectURL(url);
      };
    }
  }, [imageToCrop]);

  /* ============================================================================= */

  const { getRootProps, getInputProps } = useDropzone({ onDrop });

  /* ============================================================================= */

  return (
    <div className={styles.dropzone}>
      {imageURLs?.map((url, i) => (
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
        <div className={styles.button__previous} onClick={handlePrevious}>
          이전
        </div>
        <div className={styles.button__delete} onClick={handleDelete}>
          지우기
        </div>
        <div className={styles.button__next} onClick={handleNext}>
          다음
        </div>
        {URLToCrop && (
          <ImageCropModal
            setSelectedFiles={setSelectedFiles}
            setImageToCrop={setImageToCrop}
            image={URLToCrop}
          />
        )}
      </div>
    </div>
  );
};

export default PostDropZone;
