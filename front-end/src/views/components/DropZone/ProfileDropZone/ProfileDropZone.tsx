import React, { useCallback } from "react";

import styles from "./ProfileDropZone.module.css";

// react-dropzone
import { useDropzone } from "react-dropzone";

type Props = {
  setFunction?: (file: any) => void;
};

const ProfileDropZone: React.FC<Props> = ({ setFunction }) => {
  //------------------------------------------------------------------------------\
  // dropzone config
  const onDrop = useCallback(
    (acceptedFiles: any) => {
      setFunction?.(acceptedFiles);
      console.log(acceptedFiles);
    },
    [setFunction]
  );

  const { getRootProps, getInputProps } = useDropzone({ onDrop });

  //------------------------------------------------------------------------------

  return (
    <div className={styles.dropzone}>
      <div className={styles.dropzone_row1}>
        <img src="#" alt="" className={styles.dropzone__img} />
        <div {...getRootProps()} className={styles.dropzone__drop}>
          <input {...getInputProps()} />
        </div>
      </div>
      <div>
        <input type="file" />
      </div>
    </div>
  );
};

export default ProfileDropZone;
