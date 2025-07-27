package com.example.githubrepos.service;

import com.example.githubrepos.client.GithubApiClient;
import com.example.githubrepos.client.dto.GithubBranchDto;
import com.example.githubrepos.client.dto.GithubRepoDto;
import com.example.githubrepos.model.BranchResponseDto;
import com.example.githubrepos.model.RepositoryResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubRepositoryService  {
    private final GithubApiClient githubApiClient;

    public GithubRepositoryService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    public List<RepositoryResponseDto> getUserRepositories(String username) {
        List<GithubRepoDto> repositories = githubApiClient.getUserRepositories(username);
        return repositories.stream()
                .filter(repo -> !repo.isFork())
                .map(repo -> mapToRepositoryResponse(username, repo))
                .toList();
    }

    private RepositoryResponseDto mapToRepositoryResponse(String username, GithubRepoDto repo) {
        List<BranchResponseDto> branches = githubApiClient.getRepositoryBranches(username, repo.name()).stream()
                .map(this::mapToBranchResponse)
                .toList();
        return new RepositoryResponseDto(repo.name(), repo.owner().login(), branches);
    }

    private BranchResponseDto mapToBranchResponse(GithubBranchDto branch) {
        return new BranchResponseDto(branch.name(), branch.commit().sha());
    }
}
