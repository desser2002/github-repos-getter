package com.example.githubrepos.client;

import com.example.githubrepos.client.dto.GithubBranchDto;
import com.example.githubrepos.client.dto.GithubRepoDto;
import com.example.githubrepos.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class GithubApiRestClient implements GithubApiClient {
    private final RestTemplate restTemplate;
    private static final String GITHUB_API_URL = "https://api.github.com";

    public GithubApiRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<GithubRepoDto> getUserRepositories(String username) {
        try {
            String url = GITHUB_API_URL + "/users/" + username + "/repos";
            GithubRepoDto[] repositories = restTemplate.getForObject(url, GithubRepoDto[].class);
            return Arrays.asList(repositories != null ? repositories : new GithubRepoDto[0]);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("User '" + username + "' not found");
            }
            throw e;
        }
    }

    @Override
    public List<GithubBranchDto> getRepositoryBranches(String username, String repository) {
        String url = GITHUB_API_URL + "/repos/" + username + "/" + repository + "/branches";
        GithubBranchDto[] branches = restTemplate.getForObject(url, GithubBranchDto[].class);
        return Arrays.asList(branches != null ? branches : new GithubBranchDto[0]);
    }
}
