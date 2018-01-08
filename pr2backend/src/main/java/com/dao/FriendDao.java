package com.dao;

import java.util.List;

import com.model.Friend;
import com.model.User;

public interface FriendDao {
List<User> suggestedUsersList(String username);
void addFriendRequest(Friend friend);
List<Friend> pendingRequests(String username);
void updatePendingRequest(Friend friend);
List<User> listOfFriends(String username);
}
