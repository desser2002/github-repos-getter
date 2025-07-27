package com.example.githubrepos.integration;

import com.example.githubrepos.model.RepositoryResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubRepositoryIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturnNonForkRepositoriesWithBranchesForExistingUser() {
        //given
        String username = "octocat";
        String url = "http://localhost:" + port + "/api/v1/repositories/" + username;

        //when
        ResponseEntity<RepositoryResponseDto[]> response = testRestTemplate.getForEntity(url, RepositoryResponseDto[].class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        RepositoryResponseDto[] repositories = response.getBody();
        assertNotNull(repositories);
        assertTrue(repositories.length > 0);

        for (RepositoryResponseDto repository : repositories) {
            assertNotNull(repository.name());
            assertFalse(repository.name().isBlank());
            assertEquals(username, repository.ownerLogin());

            assertNotNull(repository.branches());
            assertFalse(repository.branches().isEmpty());

            repository.branches().forEach(branch -> {
                assertNotNull(branch.name());
                assertFalse(branch.name().isBlank());
                assertNotNull(branch.lastCommitSha());
                assertFalse(branch.lastCommitSha().isBlank());
            });
        }
    }
}
