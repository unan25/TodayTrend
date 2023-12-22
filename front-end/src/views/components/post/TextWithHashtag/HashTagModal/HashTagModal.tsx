import React, { ReactNode } from "react";
import styles from "./HashTagModal.module.css";

type UserTag = {
  nickname: string;
  profileImage: string;
  uuid: string;
};

type Props = {
  type: "content" | "comment";
  tagType: string | undefined;
  userTagList: UserTag[];
  fashionTagList: string[];
  setSelectedTag: React.Dispatch<React.SetStateAction<string | undefined>>;
};

const HashTagModal: React.FC<Props> = ({
  type,
  tagType,
  userTagList,
  fashionTagList,
  setSelectedTag,
}) => {
  // rendering function
  const renderList = () => {
    <div className=""></div>;
    const temp: ReactNode[] = [];

    if (tagType === "user" && userTagList.length > 0) {
      userTagList.map((e, i) => {
        temp.push(
          <div className={styles.userTag} key={i}>
            <img className={styles.userTag__img} src={e.profileImage} />
            <div className={styles.userTag__nickname} onClick={selectTag}>
              {e.nickname}
            </div>
          </div>
        );
      });
    }

    if (tagType === "fashion" && fashionTagList.length > 0) {
      fashionTagList.map((e, i) => {
        temp.push(
          <div className={styles.fashionTag} key={i} onClick={selectTag}>
            {e}
          </div>
        );
      });
    }

    return temp;
  };

  // select hashtag
  const selectTag = (e: React.MouseEvent<HTMLDivElement>) => {
    const selectedTag = e.currentTarget.innerHTML;
    setSelectedTag(selectedTag);
  };

  return (
    <div>
      <div
        className={
          type === "content" ? styles.modal_content : styles.modal_comment
        }
      >
        {userTagList.length || fashionTagList.length ? (
          renderList()
        ) : (
          <div>검색 결과가 없습니다.</div>
        )}
      </div>
    </div>
  );
};

export default HashTagModal;
