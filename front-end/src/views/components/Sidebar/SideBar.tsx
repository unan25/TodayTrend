import React, { useState } from "react";
import styles from "./SideBar.module.css";
import "../../../../global.css";

const Sidebar: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div
      className={
        isOpen ? styles.sidebar : `${styles.sidebar} ${styles.sidebar_closed}`
      }
    >
      <ul>
        <button className={styles.button} onClick={toggleSidebar}>
          {isOpen ? "Close" : "Menu"}
        </button>
        <a href="/">
          <li className={styles.menu}>Home</li>
        </a>
        <a href="/search">
          <li className={styles.menu}>Search</li>
        </a>
        <a href="/upload-post">
          <li className={styles.menu}>Post</li>
        </a>
        <a href="/profile">
          <li className={styles.menu}>Profile</li>
        </a>
      </ul>
    </div>
  );
};

export default Sidebar;
