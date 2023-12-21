import React from "react";

import styles from "./renderContentWithLinks.module.css";

export const renderContentWithLinks = (content: string) => {
  const words = content.split(/\s+/);

  return words.map((word, index) => {
    if (word.startsWith("@")) {
      const username = word.substring(1);

      return (
        <a key={index} className={styles.userTag} href={`/profile/${username}`}>
          {word}{" "}
        </a>
      );
    } else if (word.startsWith("#")) {
      const hashtag = word.substring(1);
      return (
        <a key={index} className={styles.hashTag} href={`/search/${hashtag}`}>
          {word}{" "}
        </a>
      );
    } else {
      return <span key={index}>{word} </span>;
    }
  });
};
