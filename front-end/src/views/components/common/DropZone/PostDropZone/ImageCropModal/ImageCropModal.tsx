// ImageCropModal.tsx

import React, { useState } from "react";
import Cropper, { Area } from "react-easy-crop";
import styles from "./ImageCropModal.module.css";

type Props = {
  image: string;
  setSelectedFiles: React.Dispatch<React.SetStateAction<File[]>>;
  setImageToCrop: React.Dispatch<React.SetStateAction<File | null>>; // 수정된 부분
};

const ImageCropModal: React.FC<Props> = ({
  image,
  setSelectedFiles,
  setImageToCrop,
}) => {
  const [crop, setCrop] = useState({ x: 0, y: 0 });
  const [zoom, setZoom] = useState(1);
  const [croppedAreaPixels, setCroppedAreaPixels] = useState<Area>();

  const onCropComplete = (newCroppedArea: Area, newCroppedAreaPixels: Area) => {
    setCroppedAreaPixels(newCroppedAreaPixels);
  };

  const onClickHandler = () => {
    if (!croppedAreaPixels) return;

    try {
      const canvas = document.createElement("canvas");
      const ctx = canvas.getContext("2d");

      if (ctx) {
        canvas.width = croppedAreaPixels.width;
        canvas.height = croppedAreaPixels.height;

        const img = new Image();

        img.onload = () => {
          ctx.drawImage(
            img,
            croppedAreaPixels.x,
            croppedAreaPixels.y,
            croppedAreaPixels.width,
            croppedAreaPixels.height,
            0,
            0,
            croppedAreaPixels.width,
            croppedAreaPixels.height
          );

          canvas.toBlob((blob) => {
            if (blob) {
              const file = new File([blob], `${image.toString()}.jpg`, {
                type: "image/jpeg",
              });
              setSelectedFiles((prev) => [...prev, file]);
              setImageToCrop(null);
            }
          }, "image/jpeg");
        };

        img.src = image;
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className={styles.modal_body}>
      <Cropper
        classes={{
          containerClassName: styles.modal__crop_container,
        }}
        image={image}
        crop={crop}
        zoom={zoom}
        aspect={3 / 4}
        onCropChange={setCrop}
        onCropComplete={onCropComplete}
        onZoomChange={setZoom}
      />
      <button
        type="button"
        onClick={onClickHandler}
        className={styles.modal__onCrop_btn}
      >
        Crop
      </button>
    </div>
  );
};

export default ImageCropModal;
