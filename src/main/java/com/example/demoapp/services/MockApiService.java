package com.example.demoapp.services;

import com.example.demoapp.views.ProjectMembershipView;
import com.example.demoapp.views.UserView;

import java.util.List;

public interface MockApiService {

    List<UserView> getRegisteredUsers();

    List<UserView> getUnregisteredUsers();

    List<ProjectMembershipView> getProjectMemberships();
}
