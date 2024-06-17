

## Introduction

Welcome to GitHub Crawler! A tool designed to extract and organize data from GitHub. Given an organization, the crawler collects insights into developer activities, repositories, and programming languages utilized within the organization. The project doesn't only provide an API but also an interactive frontend interface.

*TechStack:* 
* Backend: Spring Boot 3
	* DB: PostgreSQL
* Frontend: ReactJS
* Containerization: Docker
### Features

- **Data Extraction**: Retrieves informations about repositories, developers, and their contributions from GitHub.
    
- **Interactive Interface**: Offers a user-friendly frontend interface for visualizing and interacting with extracted data.
- **Possible Interactions:**
	- Get all developers
	- Get list of developers that use given programming language
### Prerequisites

Before running GitHub Crawler, make sure you have the following:

- **GitHub Token**: Generate a GitHub personal access token with necessary permissions(**repo permission**).
- **Docker**

*Only when you want to run outside docker*

- **Database Setup**: Configure aPostgreSQL database instance.
    
- **Java Development Kit (JDK)**:  JDK11+.
    
- **Node.js and npm**
## HowTo

1. Clone the project
   ```{bash}
   git clone https://github.com/your-github-username/github-crawler.git
   cd github-crawler
   ```

2. Configure environment variables by creating an `.env` file at the root directory level.
```{bash}
GITHUB_TOKEN=TOKEN_PLACEHOLDER  
DATABASE=codecentric_github_db  
DATASOURCE_URL=jdbc:postgresql://postgres_db:5432/codecentric_github_db
DATASOURCE_USERNAME=crawler_admin #Can change, those are used for dev  
DATASOURCE_PASSWORD=Berlin030! # Can change, those are used for dev
```

**Important:***Edit the .env accordingly*

3. Run the docker compose
```bash
docker-compose up --build
```
  Per default the active profile is dev. Update `application.properties` to change to prod profile(less logs, different data loading strategy)

It is also possible to run the application locally without Docker :

 - Reproduce the configuration described in docker-compose.yml (db,ports)
 - cd in `frontend` and install dependencies
 - Run the Spring Boot Application
 - Run `npm run dev` to run frontend on port `3000`

# Docker images for codecentric

The application is fully containerized and hosted in docker-hub so it can be ran without the actual source code. *It is possible to just pull the images --> create .env file -->use the docker-compose.yml.*

```bash
docker pull neziak/github-crawler_backend:latest
docker pull neziak/github-crawler_frontend:latest
```

## API documentation

The API documentation can be accessed at the SwaggerUI endpoint `/api-docs.html` 
## Improvement areas
* Track timestamps better to avoid unnecessary update
	* Persist timestamp
	* Check for change type in repository before updating
*  Improve update mechanism
* Improve precision in attributing programming languages to specific developers based on their contributions.
	* Scenario : Developer X can work in Repository Y where programming languages A,B,C,D are used but X has only contributed using programming language A. In the current state of the application all the programming languages (A,B,C,D) are attributed to developer X
* Write additional tests to achieve better coverage across the application.
* TODOs
	* Make frontend UI more responsive
	* Deploy the application
