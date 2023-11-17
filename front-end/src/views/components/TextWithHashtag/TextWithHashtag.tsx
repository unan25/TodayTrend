import React, { useState, useEffect, useRef } from "react";
import styles from "./TextWithHashtag.module.css";

type TagOn = {
  startPoint: number;
  tagOn: boolean;
};

type HashTag = {
  user: string[];
  fashion: string[];
};

type Props = {
  content: string;
  hashtag: HashTag;
  setHashtag: (value: HashTag) => void;
  setContent: (value: string) => void;
};

const TextWithHashtag: React.FC<Props> = ({
  content,
  setContent,
  hashtag,
  setHashtag,
}) => {
  const [TagOn, setTagOn] = useState<TagOn>({
    startPoint: 0,
    tagOn: false,
  });

  const textareaRef = useRef<HTMLTextAreaElement>(null);

  const onChangeHandler = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const newContent = e.target.value;
    setContent(newContent);
  };

  useEffect(() => {
    const cursorPosition = textareaRef.current?.selectionStart;

    if (cursorPosition !== undefined) {
      const charBeforeCursor = content[cursorPosition - 1];

      if (charBeforeCursor === "#" || charBeforeCursor === "@") {
        const tagType = charBeforeCursor === "#" ? "패션" : "유저";
        console.log(`${tagType} 해시태그 검색중...`);
        setTagOn({ startPoint: cursorPosition, tagOn: true });
      } else if (TagOn.tagOn && charBeforeCursor === " ") {
        console.log("태그 검색 종료");
        setTagOn({ startPoint: 0, tagOn: false });
        console.log(content.substring(TagOn.startPoint, cursorPosition - 1));
      }
    }
  }, [content]);

  return (
    <div className={styles.body}>
      <textarea
        className={styles.textarea}
        ref={textareaRef}
        value={content}
        onChange={onChangeHandler}
      ></textarea>
    </div>
  );
};

export default TextWithHashtag;
