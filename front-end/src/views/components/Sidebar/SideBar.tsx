import React, { useState } from "react";
import "./SideBar.css";

const Sidebar: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div>
      <ul className={`sidebar ${isOpen ? "" : "close"}`}>
        <button onClick={toggleSidebar}>{isOpen ? "Close" : "Menu"}</button>
        <a href="/">
          <li>Home</li>
        </a>
        <a href="/search">
          <li>Search</li>
        </a>
        <a href="/upload-post">
          <li>Post</li>
        </a>
        <a href="/profile">
          <li>Profile</li>
        </a>
      </ul>
    </div>
  );
};

export default Sidebar;
