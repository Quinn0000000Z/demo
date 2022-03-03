package com.example.demoapp.services.impl;

import com.example.demoapp.views.ProjectMembershipView;
import com.example.demoapp.views.UserView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class MockApiServiceImplTest {

    private MockApiServiceImpl mockApiService;

    @Mock
    private List<UserView> userViewListMock;

    @Mock
    private List<ProjectMembershipView> projectMembershipViewListMock;

    @BeforeEach
    void setUp() {
        mockApiService = spy(MockApiServiceImpl.class);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockApiService, userViewListMock, projectMembershipViewListMock);

    }

    @Test
    void getRegisteredUsers() {
        doReturn(userViewListMock).when(mockApiService).executeGetRequest(any(), any());

        List<UserView> actual = mockApiService.getRegisteredUsers();

        assertThat(actual).isSameAs(userViewListMock);
        verify(mockApiService).executeGetRequest(eq("registeredusers"), any());
        verify(mockApiService).getRegisteredUsers();
    }

    @Test
    void getUnregisteredUsers() {
        doReturn(userViewListMock).when(mockApiService).executeGetRequest(any(), any());

        List<UserView> actual = mockApiService.getUnregisteredUsers();

        assertThat(actual).isSameAs(userViewListMock);
        verify(mockApiService).executeGetRequest(eq("unregisteredusers"), any());
        verify(mockApiService).getUnregisteredUsers();
    }

    @Test
    void getProjectMemberships() {
        doReturn(projectMembershipViewListMock).when(mockApiService).executeGetRequest(any(), any());

        List<ProjectMembershipView> actual = mockApiService.getProjectMemberships();

        assertThat(actual).isSameAs(projectMembershipViewListMock);
        verify(mockApiService).executeGetRequest(eq("projectmemberships"), any());
        verify(mockApiService).getProjectMemberships();
    }
}