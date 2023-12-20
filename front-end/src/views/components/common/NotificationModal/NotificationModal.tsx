import React, { useState } from 'react';
import styles from './NotificationModal.module.css';
import { useQuery } from 'react-query';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { RootState } from 'redux/store';

interface NotificationModalProps {
  onClose: () => void;
}
interface NotificationType {
  sender: string;
  senderImage: string;
  content: string;
  type: string;
  createAt: string;
}

const NotificationModal: React.FC<NotificationModalProps> = ({ onClose }) => {
  const [notification, setNotification] = useState<NotificationType[]>();
  const uuid = useSelector((state: RootState) => state.user.UUID);

  const { data: notifications } = useQuery('notifications', async () => {
    const response = await axios.get(`/api/notifications?uuid=${uuid}`);
    setNotification(response.data.data);
  });

  return (
    <div className={styles.modalContainer}>
      <button className={styles.close} onClick={onClose}>
        X
      </button>
      {notification?.map((nc: NotificationType, i) => (
        <div key={i}>
          <p>{nc.sender}</p>
          <p>{nc.content}</p>
        </div>
      ))}
    </div>
  );
};

export default NotificationModal;
