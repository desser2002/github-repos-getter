package com.example.githubrepos.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepoDto(
        String name,
        Owner owner,
        boolean isFork) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Owner(String login) {
    }
}
