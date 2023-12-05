import React, { useState } from "react";
import ReactCrop, {
  type Crop,
  centerCrop,
  makeAspectCrop,
} from "react-image-crop";

// styles
import "react-image-crop/dist/ReactCrop.css";
import styles from "./ImageCropModal.module.css";

type Props = {
  image: string;
};

const ImageCropModal: React.FC<Props> = ({ image }) => {
  const [crop, setCrop] = useState<Crop>({
    unit: "%", // Can be 'px' or '%'
    x: 25,
    y: 25,
    width: 50,
    height: 50,
  });

  function onImageLoad(e: React.TransitionEvent<HTMLImageElement>) {
    const { naturalWidth: width, naturalHeight: height } = e.currentTarget;

    const crop = centerCrop(
      makeAspectCrop(
        {
          unit: "%",
          width: 90,
        },
        16 / 9,
        width,
        height
      ),
      width,
      height
    );

    setCrop(crop);
  }

  return (
    <div className={styles.modal_body}>
      {image && (
        <ReactCrop crop={crop} onChange={(c) => setCrop(c)}>
          <img src={image} onLoad={onImageLoad} />
        </ReactCrop>
      )}
    </div>
  );
};

export default ImageCropModal;
