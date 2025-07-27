package com.example.githubrepos.model;

import java.util.List;

public record RepositoryResponseDto(
        String name,
        String ownerLogin,
        List<BranchResponseDto> branches
) {
}
