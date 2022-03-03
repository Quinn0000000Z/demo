package com.example.demoapp.services.impl;

import com.example.demoapp.services.MockApiService;
import com.example.demoapp.services.UserService;
import com.example.demoapp.views.ProjectMembershipView;
import com.example.demoapp.views.UserView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final MockApiService mockApiService;

    public UserServiceImpl(MockApiService mockApiService) {
        this.mockApiService = mockApiService;
    }

    @Override
    public List<UserView> getUserViews() {
        List<UserView> users = new ArrayList<>();

        users.addAll(mockApiService.getRegisteredUsers());
        users.addAll(mockApiService.getUnregisteredUsers());

        Map<String, List<String>> userIdToProjectIds = getUserIdToProjectIdsMap();

        users.forEach(u -> {
            List<String> projectIds = userIdToProjectIds.get(u.getId());
            u.setProjectIds(projectIds != null ? projectIds : Collections.emptyList());
        });

        return users;
    }

    Map<String, List<String>> getUserIdToProjectIdsMap() {
        List<ProjectMembershipView> projectMemberships = mockApiService.getProjectMemberships();

        return projectMemberships.stream()
                .collect(Collectors.groupingBy(ProjectMembershipView::getUserId,
                        Collectors.mapping(ProjectMembershipView::getProjectId, Collectors.toList())
                ));
    }

}
