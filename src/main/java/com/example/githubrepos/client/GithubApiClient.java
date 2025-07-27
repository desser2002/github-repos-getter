package com.example.githubrepos.client;

import com.example.githubrepos.client.dto.GithubBranchDto;
import com.example.githubrepos.client.dto.GithubRepoDto;

import java.util.List;

public interface GithubApiClient {
    List<GithubRepoDto> getUserRepositories(String username);

    List<GithubBranchDto> getRepositoryBranches(String username, String repository);
}
