package com.dao;

import java.util.List;

import com.model.Notification;

public interface NotificationDao {
List<Notification> getNotification(String username,int viewed);
Notification updateNotification(int notificationId);
}
