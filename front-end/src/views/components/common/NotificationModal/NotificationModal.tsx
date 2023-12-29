import React, { useEffect, useState } from 'react';
import styles from './NotificationModal.module.css';
import { useMutation, useQuery } from 'react-query';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { RootState } from 'redux/store';
import { useNavigate } from 'react-router-dom';

interface NotificationModalProps {
  onClose: () => void;
  refetch: () => void;
}
interface NotificationType {
  notificationId: number;
  sender: string;
  senderImage: string;
  content?: string;
  type: string;
  createdBefore: string;
  postId?: number;
  postImage?: string;
}

const renderNotificationContent = (nc: NotificationType) => {
  switch (nc.type) {
    case 'POST_LIKE':
      return (
        <div className={styles.content}>
          {nc.sender}님이 회원님의 게시물을 좋아합니다:{nc.content}
          <div className={styles.created}>{nc.createdBefore}</div>
        </div>
      );
    case 'POST_TAG':
      return (
        <div className={styles.content}>
          {nc.sender}님이 게시물에서 회원님을 언급했습니다:{nc.content}
          <div className={styles.created}>{nc.createdBefore}</div>
        </div>
      );
    case 'COMMENT_LIKE':
      return (
        <div className={styles.content}>
          {nc.sender}님이 회원님의 댓글을 좋아합니다:{nc.content}
          <div className={styles.created}>{nc.createdBefore}</div>
        </div>
      );
    case 'COMMENT_CREATE':
      return (
        <div className={styles.content}>
          {nc.sender}님이 회원님의 게시물에 댓글을 남겼습니다:{nc.content}
          <div className={styles.created}>{nc.createdBefore}</div>
        </div>
      );
    case 'COMMENT_TAG':
      return (
        <div className={styles.content}>
          {nc.sender}님이 댓글에서 회원님을 언급했습니다:{nc.content}
          <div className={styles.created}>{nc.createdBefore}</div>
        </div>
      );
    case 'FOLLOW':
      return (
        <div className={styles.content}>
          {nc.sender}님이 회원님을 팔로우하기 시작했습니다.{nc.content}
          <div className={styles.created}>{nc.createdBefore}</div>
        </div>
      );
    default:
      return null;
  }
};

const NotificationModal: React.FC<NotificationModalProps> = ({
  onClose,
  refetch,
}) => {
  const [notification, setNotification] = useState<NotificationType[]>();

  const uuid = useSelector((state: RootState) => state.user.UUID);
  const navigate = useNavigate();

  const checkedNotifications = async () => {
    await axios.post(`/api/notifications`, notification);
    setNotification([]);
  };
  const checkedNotification = async (noti: NotificationType) => {
    await axios
      .delete(`/api/notifications?notificationId=${noti.notificationId}`)
      .then(getNotification);
    refetch();
  };

  const getNotification = async () => {
    const response = await axios.get(`/api/notifications?uuid=${uuid}`);
    setNotification(response.data);
  };

  const handleModalClose = () => {
    if (notification && notification.length > 0) {
      checkedNotifications().then(() => {
        refetch();
      });
    }
    onClose();
  };

  useEffect(() => {
    getNotification();
  }, []);

  return (
    <div className={styles.modalContainer}>
      <button className={styles.close} onClick={handleModalClose}>
        전체삭제
      </button>
      {notification?.map((nc: NotificationType) => (
        <div
          key={nc.notificationId}
          className={styles.notification}
          onClick={() => checkedNotification(nc)}
        >
          <img
            src={nc.senderImage}
            className={styles.profileImage}
            onClick={() => {
              navigate(`/profile/${nc.sender}`);
              onClose();
            }}
          ></img>
          {renderNotificationContent(nc)}
          {nc.postId && (
            <img
              src={nc.postImage}
              className={styles.postImage}
              onClick={() => {
                navigate(`/post/${nc.postId}`);
                onClose();
              }}
            />
          )}
        </div>
      ))}
    </div>
  );
};

export default NotificationModal;
