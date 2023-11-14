import React, { useCallback } from "react";

import styles from "./DropZone.module.css";

// react-dropzone
import { useDropzone } from "react-dropzone";

// component
import { Alert, Row, Image } from "react-bootstrap";

type Props = {
  setFunction?: (file: any) => void;
};

const DropZone: React.FC<Props> = ({ setFunction }) => {
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
      <Row className={styles.dropzone_row1}>
        <Image src="#" alt="" className={styles.dropzone__img} />
        <div {...getRootProps()} className={styles.dropzone__drop}>
          <input {...getInputProps()} />
        </div>
      </Row>
      <Row>
        <input type="file" />
      </Row>
    </div>
  );
};

export default DropZone;
