package com.example.githubrepos.controller;

import com.example.githubrepos.model.RepositoryResponseDto;
import com.example.githubrepos.service.GithubRepositoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repositories")
public class GithubRepositoryController {
    private final GithubRepositoryService githubRepositoryService;

    public GithubRepositoryController(GithubRepositoryService githubRepositoryService) {
        this.githubRepositoryService = githubRepositoryService;
    }

    @GetMapping("/{username}")
    public List<RepositoryResponseDto> getUserRepositories(@PathVariable String username) {
        return githubRepositoryService.getUserRepositories(username);
    }
}
