package com.example.demoapp.services.impl;

import com.example.demoapp.services.MockApiService;
import com.example.demoapp.views.ProjectMembershipView;
import com.example.demoapp.views.UserView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private MockApiService mockApiService;

    // class under test
    private UserServiceImpl userServiceImpl;

    @Captor
    private ArgumentCaptor<List<String>> listCaptor;

    @BeforeEach
    void setUp() {
        userServiceImpl = spy(new UserServiceImpl(mockApiService));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockApiService, userServiceImpl);
    }

    @Test
    void getUserViews_noUsers() {
        when(mockApiService.getRegisteredUsers()).thenReturn(Collections.emptyList());
        when(mockApiService.getUnregisteredUsers()).thenReturn(Collections.emptyList());
        doReturn(Collections.emptyMap()).when(userServiceImpl).getUserIdToProjectIdsMap();

        List<UserView> actual = userServiceImpl.getUserViews();

        assertThat(actual).isEmpty();
        verify(mockApiService).getRegisteredUsers();
        verify(mockApiService).getUnregisteredUsers();
        verify(userServiceImpl).getUserIdToProjectIdsMap();
        verify(userServiceImpl).getUserViews();
    }

    @Test
    void getUserViews_registeredUser_noProjectIds() {
        // use a mock so that verifications can be used to validate no unexpected side effects occurred
        UserView userView = createUserViewMock("11");
        when(mockApiService.getRegisteredUsers()).thenReturn(List.of(userView));
        when(mockApiService.getUnregisteredUsers()).thenReturn(Collections.emptyList());
        doReturn(Collections.emptyMap()).when(userServiceImpl).getUserIdToProjectIdsMap();

        List<UserView> actual = userServiceImpl.getUserViews();

        validateSingleUser(userView, actual);
    }

    @Test
    void getUserViews_unregisteredUser_noProjectIds() {
        // use a mock so that verifications can be used to validate no unexpected side effects occurred
        UserView userView = createUserViewMock("11");
        when(mockApiService.getRegisteredUsers()).thenReturn(List.of());
        when(mockApiService.getUnregisteredUsers()).thenReturn(List.of(userView));
        doReturn(Collections.emptyMap()).when(userServiceImpl).getUserIdToProjectIdsMap();

        List<UserView> actual = userServiceImpl.getUserViews();

        validateSingleUser(userView, actual);
    }

    private void validateSingleUser(UserView userView, List<UserView> actual) {
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isSameAs(userView);
        verify(userView).setProjectIds(listCaptor.capture());
        verify(userView).getId();
        assertThat(listCaptor.getValue()).isEmpty();
        verify(mockApiService).getRegisteredUsers();
        verify(mockApiService).getUnregisteredUsers();
        verify(userServiceImpl).getUserIdToProjectIdsMap();
        verify(userServiceImpl).getUserViews();
        verifyNoMoreInteractions(userView);
    }

    /**
     * This scenario creates two registered users and one unregistered user, all with project IDs.
     */
    @Test
    void getUserViews_multipleUsers() {
        UserView user1 = createUserViewMock("11");
        UserView user2 = createUserViewMock("22");
        UserView user3 = createUserViewMock("33");
        when(mockApiService.getRegisteredUsers()).thenReturn(List.of(user1, user2));
        when(mockApiService.getUnregisteredUsers()).thenReturn(List.of(user3));
        List<String> user1ProjectIds = List.of("12");
        List<String> user2ProjectIds = List.of("23", "24");
        List<String> user3ProjectIds = List.of("34", "12"); // this user is in one of the same projects as user 1.
        Map<String, List<String>> userIdToProjectIdsMap = Map.of(
                "11", user1ProjectIds,
                "22", user2ProjectIds,
                "33", user3ProjectIds
        );
        doReturn(userIdToProjectIdsMap).when(userServiceImpl).getUserIdToProjectIdsMap();

        List<UserView> actual = userServiceImpl.getUserViews();

        assertThat(actual).hasSize(3);
        assertThat(actual).hasSameElementsAs(List.of(user1, user2, user3));
        validateUserMock(user1, user1ProjectIds);
        validateUserMock(user2, user2ProjectIds);
        validateUserMock(user3, user3ProjectIds);
        verify(mockApiService).getRegisteredUsers();
        verify(mockApiService).getUnregisteredUsers();
        verify(userServiceImpl).getUserIdToProjectIdsMap();
        verify(userServiceImpl).getUserViews();
        verifyNoMoreInteractions(user1, user2, user3);
    }

    private void validateUserMock(UserView user, List<String> user1ProjectIds) {
        verify(user).setProjectIds(listCaptor.capture());
        verify(user).getId();
        assertThat(listCaptor.getValue()).isEqualTo(user1ProjectIds);
    }

    private static UserView createUserViewMock(String userId) {
        UserView userView = mock(UserView.class);
        when(userView.getId()).thenReturn(userId);
        return userView;
    }

    @Test
    void getUserIdToProjectIdsMap_emptyList() {
        when(mockApiService.getProjectMemberships()).thenReturn(Collections.emptyList());

        Map<String, List<String>> actual = userServiceImpl.getUserIdToProjectIdsMap();

        assertThat(actual).isEmpty();
        verify(mockApiService).getProjectMemberships();
        verify(userServiceImpl).getUserIdToProjectIdsMap();
    }

    @Test
    void getUserIdToProjectIdsMap_singleProjectMembership() {
        String projectId = "11";
        String userId = "22";
        ProjectMembershipView projectMembershipView = createProjectMembershipView(projectId, userId);
        when(mockApiService.getProjectMemberships()).thenReturn(List.of(projectMembershipView));

        Map<String, List<String>> actual = userServiceImpl.getUserIdToProjectIdsMap();

        assertThat(actual).hasEntrySatisfying(userId, e -> assertThat(e).isEqualTo(List.of(projectId)));
        assertThat(actual).hasSize(1);
        verify(mockApiService).getProjectMemberships();
        verify(userServiceImpl).getUserIdToProjectIdsMap();
    }

    @Test
    void getUserIdToProjectIdsMap_multipleProjectMemberships() {
        String projectId1 = "11";
        String projectId2 = "12";
        String userId = "22";
        ProjectMembershipView projectMembershipView1 = createProjectMembershipView(projectId1, userId);
        ProjectMembershipView projectMembershipView2 = createProjectMembershipView(projectId2, userId);
        when(mockApiService.getProjectMemberships()).thenReturn(List.of(projectMembershipView1, projectMembershipView2));

        Map<String, List<String>> actual = userServiceImpl.getUserIdToProjectIdsMap();

        assertThat(actual).hasEntrySatisfying(userId, e -> assertThat(e).isEqualTo(List.of(projectId1, projectId2)));
        assertThat(actual).hasSize(1);
        verify(mockApiService).getProjectMemberships();
        verify(userServiceImpl).getUserIdToProjectIdsMap();
    }

    @Test
    void getUserIdToProjectIdsMap_multipleUsers() {
        String user1ProjectId = "11";
        String userId1 = "22";
        String userId2 = "44";
        String user2ProjectId1 = "31";
        String user2ProjectId2 = "32";
        when(mockApiService.getProjectMemberships()).thenReturn(List.of(
                        createProjectMembershipView(user1ProjectId, userId1),
                        createProjectMembershipView(user2ProjectId1, userId2),
                        createProjectMembershipView(user2ProjectId2, userId2)
                )
        );

        Map<String, List<String>> actual = userServiceImpl.getUserIdToProjectIdsMap();

        assertThat(actual).hasEntrySatisfying(userId1, e -> assertThat(e).isEqualTo(List.of(user1ProjectId)));
        assertThat(actual).hasEntrySatisfying(userId2, e -> assertThat(e).isEqualTo(List.of(user2ProjectId1, user2ProjectId2)));
        assertThat(actual).hasSize(2);
        verify(mockApiService).getProjectMemberships();
        verify(userServiceImpl).getUserIdToProjectIdsMap();
    }

    private static ProjectMembershipView createProjectMembershipView(String projectId, String userId) {
        ProjectMembershipView projectMembershipView = new ProjectMembershipView();

        projectMembershipView.setProjectId(projectId);
        projectMembershipView.setUserId(userId);

        return projectMembershipView;
    }
}