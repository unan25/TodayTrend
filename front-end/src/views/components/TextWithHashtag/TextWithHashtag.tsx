import React, { useState, useEffect, useRef } from "react";
import styles from "./TextWithHashtag.module.css";

type TagSwitch = {
  startPoint: number;
  tagType?: string;
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

const TextWithHashtag: React.FC<Props> = ({ content, setContent }) => {
  // state
  const [tagSwitch, setTagSwitch] = useState<TagSwitch>({
    startPoint: 0,
    tagOn: false,
  });

  const [cursor, setCursor] = useState(0);

  // ref
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  // event
  const onChangeHandler = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const newContent = e.target.value;
    setContent(newContent);
  };

  const onKeyDownHandler = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    setTimeout(() => {
      const cursorPosition = textareaRef.current?.selectionStart;
      if (cursorPosition !== undefined) setCursor(cursorPosition);
    }, 0);
  };

  // function
  const extractWord = (cursor: number) => {
    let anchor = cursor;
    let focus = cursor;

    while (true) {
      anchor--;
      if (
        content[anchor] === " " ||
        anchor === -1 ||
        content[anchor] === "\n"
      ) {
        break;
      }
    }

    if (anchor === -1) {
      anchor = 0;
    }

    while (true) {
      if (content[focus] === " " || focus === content.length) {
        break;
      }
      focus++;
    }

    console.log("cursor" + cursor);

    const word = content.substring(anchor, focus);

    if (word.includes("@")) {
      setTagSwitch({
        startPoint: anchor ? anchor + 2 : anchor + 1,
        tagType: "user",
        tagOn: true,
      });
    }

    if (word.includes("#")) {
      setTagSwitch({
        startPoint: anchor ? anchor + 2 : anchor + 1,
        tagType: "fahsion",
        tagOn: true,
      });
    }

    if (!word.includes("@" || "#") && tagSwitch.tagOn === true) {
      setTagSwitch({
        startPoint: 0,
        tagType: undefined,
        tagOn: false,
      });
    }
  };

  const createHashtag = () => {
    const cursorPosition = textareaRef.current?.selectionStart;

    if (cursorPosition !== undefined) {
      const charBeforeCursor = content[cursorPosition - 1];

      if (charBeforeCursor === "#" || charBeforeCursor === "@") {
        const tagType = charBeforeCursor === "#" ? "패션" : "유저";
        setTagSwitch({
          startPoint: cursorPosition,
          tagType: tagType,
          tagOn: true,
        });
      }

      if (tagSwitch.tagOn && charBeforeCursor === " ") {
        setTagSwitch({
          startPoint: 0,
          tagType: undefined,
          tagOn: false,
        });

        const hashtag = content.substring(tagSwitch.startPoint, cursorPosition);
        return hashtag;
      }

      if (
        (tagSwitch.tagOn && charBeforeCursor === "\n") ||
        cursorPosition === 0
      ) {
        setTagSwitch({
          startPoint: 0,
          tagType: undefined,
          tagOn: false,
        });
      }

      if (tagSwitch.tagOn) {
        const hashtag = content.substring(tagSwitch.startPoint, cursorPosition);
        return hashtag;
      }
    }
  };

  // effect
  useEffect(() => {
    if (cursor) {
      extractWord(cursor);
    }
  }, [cursor]);

  useEffect(() => {
    const temp = createHashtag();
    if (temp) {
      console.log("해시태그 = " + temp);
    }
  }, [content]);

  // elements
  return (
    <div className={styles.body}>
      <textarea
        className={styles.textarea}
        ref={textareaRef}
        value={content}
        onChange={onChangeHandler}
        onKeyDown={onKeyDownHandler}
      ></textarea>
    </div>
  );
};

export default TextWithHashtag;
