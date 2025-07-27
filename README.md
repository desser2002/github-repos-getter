# GitHub Repositories Parser

A REST API service that retrieves **all non-fork GitHub repositories** for a given user, including **branches with the last commit SHA**.

## Features
- Lists **all non-fork repositories** for a GitHub user.
- For each repository, lists **all branches** with the **latest commit SHA**.
- Returns **custom JSON error (404)** if the user does not exist.
- **Integration test** covering the main business flow.
- **GitHub Actions CI**: automatic build & test on every push.

---

## Requirements
- Java 21
- Maven 3.9+
- Internet connection (for GitHub API calls)

## Run locally
```bash
mvn clean install
mvn spring-boot:run
````

The application will be available at:

```
http://localhost:8080
```

---

## API Documentation

### Get repositories for a user

`GET /api/v1/repositories/{username}`

#### Example request:

```bash
curl -X GET http://localhost:8080/api/v1/repositories/octocat
```

#### Example response:

```json
[
  {
    "repositoryName": "Hello-World",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "7fd1a60b01f91b314f59955a4e4d4e80d8edf11d"
      }
    ]
  }
]
```

### Error response (user not found):

```json
{
  "status": 404,
  "message": "User 'unknownuser' not found"
}
```

## Tests

To run all tests:

```bash
mvn test
```

Includes:

* **Unit tests** for services and clients.
* **Integration test** (Happy Path): checks the full flow (Controller → Service → GitHub API → Response).

---

## Postman Collection

You can quickly test the API using the public Postman collection:  
[**Open in Postman**](https://www.postman.com/cryosat-candidate-16977917/workspace/desser/collection/33331757-a9da9e89-2d69-4860-b940-7c9d2a6219f1?action=share&source=copy-link&creator=33331757)


It includes:

* **Happy Path**: GET repositories for `octocat`.
* **404 Case**: GET repositories for a non-existent user.

---

## Notes

* This project uses **GitHub REST API v3**: [https://developer.github.com/v3](https://developer.github.com/v3).
