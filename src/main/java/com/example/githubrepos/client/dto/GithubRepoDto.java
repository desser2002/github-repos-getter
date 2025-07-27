package com.example.githubrepos.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepoDto(
        String name,
        Owner owner,
        @JsonProperty("fork") boolean isFork) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Owner(String login) {
    }
}
