package com.example.githubrepos.client.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GithubDtoDeserializationTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldDeserializeForkFieldCorrectly() throws Exception {
        // constants
        final String expectedName = "Hello-World";
        final String expectedOwner = "octocat";
        final boolean expectedFork = true;

        // given
        String json = """
                {
                    "name": "%s",
                    "owner": { "login": "%s" },
                    "fork": %b
                }
                """.formatted(expectedName, expectedOwner, expectedFork);

        // when
        GithubRepoDto dto = objectMapper.readValue(json, GithubRepoDto.class);

        // then
        assertEquals(expectedName, dto.name());
        assertEquals(expectedOwner, dto.owner().login());
        assertTrue(dto.isFork());
    }
    @Test
    void shouldDeserializeBranchDtoCorrectly() throws Exception {
        // given
        final String expectedBranch = "main";
        final String expectedSha = "abc123";

        String json = """
                {
                  "name": "%s",
                  "commit": { "sha": "%s" }
                }
                """.formatted(expectedBranch, expectedSha);

        // when
        GithubBranchDto dto = objectMapper.readValue(json, GithubBranchDto.class);

        // then
        assertEquals(expectedBranch, dto.name());
        assertEquals(expectedSha, dto.commit().sha());
    }
}
