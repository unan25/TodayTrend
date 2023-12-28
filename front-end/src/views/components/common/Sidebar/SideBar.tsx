import React, { useState } from "react";
import styles from "./SideBar.module.css";
import { Link } from "react-router-dom";

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
      onMouseEnter={toggleSidebar}
      onMouseLeave={toggleSidebar}
    >
      <ul>
        <button className={styles.button} onClick={toggleSidebar}>
          {isOpen ? "Close" : "Menu"}
        </button>
        <Link to="/">
          <li className={styles.menu}>Home</li>
        </Link>
        <Link to="/search">
          <li className={styles.menu}>Search</li>
        </Link>
        <Link to="/upload-post">
          <li className={styles.menu}>Post</li>
        </Link>
      </ul>
    </div>
  );
};

export default Sidebar;
