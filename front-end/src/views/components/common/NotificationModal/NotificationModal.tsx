import React, { useState } from 'react';
import styles from './NotificationModal.module.css';
import { useMutation, useQuery } from 'react-query';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { RootState } from 'redux/store';
import { useNavigate } from 'react-router-dom';

interface NotificationModalProps {
  onClose: () => void;
}
interface NotificationType {
  notificationId: number;
  sender: string;
  senderImage: string;
  content?: string;
  type: string;
  createAt: string;
}
//     POST_LIKE,
//     POST_TAG,
//     COMMENT_LIKE,
//     COMMENT_CREATE,
//     COMMENT_TAG,
//     FOLLOW
const renderNotificationContent = (nc: NotificationType) => {
  switch (nc.type) {
    case 'POST_LIKE':
      return (
        <span className={styles.content}>
          {nc.sender}님이 회원님의 게시물을 좋아합니다:{nc.content}
        </span>
      );
    case 'POST_TAG':
      return (
        <span className={styles.content}>
          {nc.sender}님이 게시물에서 회원님을 언급했습니다:{nc.content}
        </span>
      );
    case 'COMMENT_LIKE':
      return (
        <span className={styles.content}>
          {nc.sender}님이 회원님의 댓글을 좋아합니다:{nc.content}
        </span>
      );
    case 'COMMENT_CREATE':
      return (
        <span className={styles.content}>
          {nc.sender}님이 회원님의 게시물에 댓글을 남겼습니다:{nc.content}
        </span>
      );
    case 'COMMENT_TAG':
      return (
        <span className={styles.content}>
          {nc.sender}님이 댓글에서 회원님을 언급했습니다:{nc.content}
        </span>
      );
    case 'FOLLOW':
      return (
        <span className={styles.content}>
          {nc.sender}님이 회원님을 팔로우하기 시작했습니다.{nc.content}
        </span>
      );
    default:
      return null;
  }
};

const NotificationModal: React.FC<NotificationModalProps> = ({ onClose }) => {
  const [notification, setNotification] = useState<NotificationType[]>();

  const uuid = useSelector((state: RootState) => state.user.UUID);
  const navigate = useNavigate();

  const checkedNotification = async () => {
    await axios.post(`/api/notifications`, notification);
    setNotification([]);
  };

  const { data } = useQuery('notifications', async () => {
    const response = await axios.get(`/api/notifications?uuid=${uuid}`);
    setNotification(response.data);
  });

  const handleModalClose = () => {
    if (notification) {
      checkedNotification();
    }
    onClose();
  };

  return (
    <div className={styles.modalContainer}>
      <button className={styles.close} onClick={handleModalClose}>
        X
      </button>
      {notification?.map((nc: NotificationType) => (
        <div key={nc.notificationId} className={styles.notification}>
          <img
            src={nc.senderImage}
            className={styles.profileimage}
            onClick={() => navigate(`/profile/${nc.sender}`)}
          ></img>
          {renderNotificationContent(nc)}
        </div>
      ))}
    </div>
  );
};

export default NotificationModal;
