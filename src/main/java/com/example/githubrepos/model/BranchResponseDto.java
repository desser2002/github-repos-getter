package com.example.githubrepos.model;

public record BranchResponseDto(
        String name,
        String lastCommitSha
) {
}
