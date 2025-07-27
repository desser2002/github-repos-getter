package com.example.githubrepos.service;

import com.example.githubrepos.client.GithubApiClient;
import com.example.githubrepos.client.dto.GithubBranchDto;
import com.example.githubrepos.client.dto.GithubRepoDto;
import com.example.githubrepos.model.RepositoryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GithubRepositoryServiceTest {
    private GithubApiClient githubApiClient;
    private GithubRepositoryService service;

    @BeforeEach
    void setUp() {
        githubApiClient = mock(GithubApiClient.class);
        service = new GithubRepositoryService(githubApiClient);
    }

    @Test
    void getUserRepositories_whenUserHasForks_thenReturnOnlyNonForkRepositoriesWithBranches() {
        //given
        String username = "octocat";
        String nonForkRepoName = "nonfork";
        String forkRepoName = "fork";
        String branchName = "main";
        String commitSha = "sha";

        GithubRepoDto nonForkRepo = new GithubRepoDto(nonForkRepoName, new GithubRepoDto.Owner(username), false);
        GithubRepoDto forkRepo = new GithubRepoDto(forkRepoName, new GithubRepoDto.Owner(username), true);
        GithubBranchDto branches = new GithubBranchDto(branchName, new GithubBranchDto.Commit(commitSha));

        when(githubApiClient.getUserRepositories(username)).thenReturn(List.of(nonForkRepo, forkRepo));
        when(githubApiClient.getRepositoryBranches(username, nonForkRepoName)).thenReturn(List.of(branches));

        //when
        List<RepositoryResponseDto> result = service.getUserRepositories(username);

        //then
        assertEquals(1, result.size());
        RepositoryResponseDto response = result.getFirst();
        assertEquals(nonForkRepoName, response.name());
        assertEquals(username, response.ownerLogin());
        assertEquals(1, response.branches().size());
        assertEquals(branchName, response.branches().getFirst().name());
        assertEquals(commitSha, response.branches().getFirst().lastCommitSha());

        verify(githubApiClient, times(0)).getRepositoryBranches(username, forkRepoName);
    }

    @Test
    void getUserRepositories_whenNoRepositories_thenReturnEmptyList() {
        //given
        String username = "empty";
        when(githubApiClient.getUserRepositories(username)).thenReturn(List.of());

        //when
        List<RepositoryResponseDto> result = service.getUserRepositories(username);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getUserRepositories_whenRepositoryHasNoBranches_thenReturnEmptyBranchesList() {
        //given
        String username = "octocat";
        String repoName = "nonFork";
        GithubRepoDto nonForkRepo = new GithubRepoDto(repoName, new GithubRepoDto.Owner(username), false);

        when(githubApiClient.getUserRepositories(username)).thenReturn(List.of(nonForkRepo));
        when(githubApiClient.getRepositoryBranches(username, repoName)).thenReturn(List.of());

        //when
        List<RepositoryResponseDto> result = service.getUserRepositories(username);

        //then
        assertEquals(1, result.size());
        assertTrue(result.getFirst().branches().isEmpty());
    }
}