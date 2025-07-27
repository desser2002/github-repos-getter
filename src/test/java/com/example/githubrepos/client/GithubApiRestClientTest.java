package com.example.githubrepos.client;

import com.example.githubrepos.client.dto.GithubRepoDto;
import com.example.githubrepos.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GithubApiRestClientTest {
    private RestTemplate restTemplate;
    private GithubApiRestClient gitApiClient;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        gitApiClient = new GithubApiRestClient(restTemplate);
    }

    @Test
    void getUserRepositories_whenUserExists_thenReturnsList() {
        //given
        String username = "octocat";
        String repoName = "repo";

        GithubRepoDto[] repos = {
                new GithubRepoDto(repoName, new GithubRepoDto.Owner(username), false)};
        when(restTemplate.getForObject(anyString(), eq(GithubRepoDto[].class))).thenReturn(repos);

        //when
        List<GithubRepoDto> result = gitApiClient.getUserRepositories(username);

        //then
        assertNotNull(result);
        assertEquals(repos.length, result.size());
        assertEquals(repoName, result.getFirst().name());
        assertEquals(username, result.getFirst().owner().login());
    }

    @Test
    void getUserRepositories_whenUserNotFound_thenThrowsUserNotFoundException() {
        //given
        String username = "doesnotexist";
        when(restTemplate.getForObject(anyString(), eq(GithubRepoDto[].class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        //when //then
        assertThrows(UserNotFoundException.class, () -> gitApiClient.getUserRepositories(username));
    }

    @Test
    void getUserRepositories_whenNullResponse_thenReturnsEmptyList() {
        //given
        String username = "empty";
        when(restTemplate.getForObject(anyString(), eq(GithubRepoDto[].class))).thenReturn(null);

        //when
        List<GithubRepoDto> result = gitApiClient.getUserRepositories(username);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}