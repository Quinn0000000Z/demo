package com.example.demoapp.services.impl;

import com.example.demoapp.services.MockApiService;
import com.example.demoapp.views.ProjectMembershipView;
import com.example.demoapp.views.UserView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class MockApiServiceImpl implements MockApiService {

    public List<UserView> getRegisteredUsers() {
        return executeGetRequest("registeredusers", new ParameterizedTypeReference<>() {
        });
    }

    public List<UserView> getUnregisteredUsers() {
        return executeGetRequest("unregisteredusers", new ParameterizedTypeReference<>() {
        });
    }

    public List<ProjectMembershipView> getProjectMemberships() {
        return executeGetRequest("projectmemberships", new ParameterizedTypeReference<>() {
        });
    }

    /**
     * This method executes a synchronous GET request using the given subPath. The response body is deserialized into
     * the type specified by the given {@link ParameterizedTypeReference}.
     *
     * @param subPath                    The sub path of the request.
     * @param parameterizedTypeReference The type reference containing info on what class the response data should be
     *                                   deserialized into.
     * @param <T>                        The class type that represents the response data.
     * @return T
     */
    <T> T executeGetRequest(String subPath, ParameterizedTypeReference<T> parameterizedTypeReference) {
        // TODO make configurable, the base path could be retrieved from a properties file.
        //  Sub paths could be pulled from props too potentially.
        String url = "https://5c3ce12c29429300143fe570.mockapi.io/api/" + subPath;
        return WebClient.create(url).get().retrieve().bodyToMono(parameterizedTypeReference).block();
    }
}
