import React, { useState, useEffect, useRef } from "react";

// style
import styles from "./TextWithHashtag.module.css";
import push from "../../../../images/comment/comment-upload.png";

// axios
import axios from "axios";

// component
import HashTagModal from "./HashTagModal/HashTagModal";

type TagSwitch = {
  startPoint: number;
  tagType?: string;
  anchor?: number;
  focus?: number;
  tagOn: boolean;
};

type Props = {
  type: "content" | "comment";
  content: string;
  setContent: (value: string) => void;
  submitComment?: React.MouseEventHandler<HTMLImageElement>;
  placeHolder?: string;
};

type UserTag = {
  nickname: string;
  profileImage: string;
  uuid: string;
};

const TextWithHashtag: React.FC<Props> = ({
  content,
  setContent,
  type,
  submitComment,
  placeHolder,
}) => {
  const USER = "user";
  const FASHION = "fashion";

  /* ==================================================================== */
  // state
  const [tagSwitch, setTagSwitch] = useState<TagSwitch>({
    startPoint: 0,
    tagOn: false,
  });

  const [userTagList, setUserTagList] = useState<UserTag[]>([]);
  const [fashionTagList, setFashionTagList] = useState<string[]>([]);
  const [cursor, setCursor] = useState(0);
  const [selectedTag, setSelectedTag] = useState<string>();

  /* ==================================================================== */
  // ref
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  /* ==================================================================== */
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

  /* ==================================================================== */
  // detect hashtag
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
      if (
        content[focus] === " " ||
        focus === content.length ||
        content[anchor] === "\n"
      ) {
        break;
      }
      focus++;
    }

    const word = content.substring(anchor, focus);

    let startPoint = anchor ? anchor + 2 : anchor + 1;

    if (word.includes("@")) {
      setTagSwitch({
        startPoint: startPoint,
        anchor: startPoint,
        focus: focus,
        tagType: USER,
        tagOn: true,
      });
    }

    if (word.includes("#")) {
      setTagSwitch({
        startPoint: startPoint,
        anchor: startPoint,
        focus: focus,
        tagType: FASHION,
        tagOn: true,
      });
    }

    if (
      !word.includes("@") &&
      !word.includes("#") &&
      tagSwitch.tagOn === true
    ) {
      setTagSwitch({
        startPoint: 0,
        tagType: undefined,
        tagOn: false,
      });
    }
  };

  // create hashtag
  const createHashtag = () => {
    const cursorPosition = textareaRef.current?.selectionStart;

    if (cursorPosition !== undefined) {
      extractWord(cursorPosition);
      const charBeforeCursor = content[cursorPosition - 1];

      if (charBeforeCursor === "#" || charBeforeCursor === "@") {
        const tagType = charBeforeCursor === "#" ? FASHION : USER;

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

  /* ==================================================================== */
  // axios
  const fetchData = async (temp: string) => {
    try {
      if (tagSwitch.tagType === USER) {
        const response = await axios.get(`/api/users/nickname/${temp}`);

        setUserTagList(response.data);
      }

      if (tagSwitch.tagType === FASHION) {
        const resonse = await axios.get(`/api/post/hashtag?hashtag=${temp}`);
        setFashionTagList(resonse.data);
      }
    } catch (err) {}
  };

  // insert Tag
  const insertTag = () => {
    if (selectedTag && tagSwitch.tagOn) {
      const newContent =
        content.slice(0, tagSwitch.startPoint) +
        selectedTag +
        content.slice(tagSwitch.focus, content.length);

      setContent(newContent);

      setTagSwitch({
        startPoint: 0,
        tagType: undefined,
        tagOn: false,
      });
    }
  };

  /* ==================================================================== */
  // effect
  useEffect(() => {
    if (cursor) {
      extractWord(cursor);
    }
  }, [cursor, selectedTag]);

  useEffect(() => {
    const tag = createHashtag();

    if (tag) {
      fetchData(tag);
    }
  }, [content]);

  useEffect(() => {
    if (selectedTag !== "") {
      insertTag();
      setSelectedTag("");
    }
  }, [selectedTag]);

  /* ==================================================================== */
  // elements
  return (
    <div
      className={type === "content" ? styles.body_content : styles.body_comment}
    >
      <textarea
        className={type === "content" ? styles.content : styles.comment}
        ref={textareaRef}
        value={content}
        onChange={onChangeHandler}
        onKeyDown={onKeyDownHandler}
        placeholder={placeHolder}
      ></textarea>
      {type === "comment" && (
        <img
          onClick={submitComment}
          className={styles.comment_input_image}
          src={push}
          alt={push}
        />
      )}
      {tagSwitch.tagOn && (
        <HashTagModal
          type={type}
          tagType={tagSwitch.tagType}
          userTagList={userTagList}
          fashionTagList={fashionTagList}
          setSelectedTag={setSelectedTag}
        />
      )}
    </div>
  );
};

export default TextWithHashtag;
