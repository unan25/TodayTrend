import React, { useState } from "react";
import ReactCrop, { type Crop } from "react-image-crop";

type Props = {
  image: string;
};

const ImageCropModal: React.FC<Props> = ({ image }) => {
  const [crop, setCrop] = useState<Crop>();

  return (
    <ReactCrop crop={crop} onChange={(c) => setCrop(c)}>
      <img src={image} />
    </ReactCrop>
  );
};

export default ImageCropModal;
