# Tutorial: Simple Access to Third-Party OAuth2 Protected Resources with Spring WebClient

This repository contains all the code for the WebClient tutorial, illustrating how to mock authorization and how to do integration testing for code that uses WebClient for accessing third-party services.

**Prerequisites**:
- [Java 11+](https://openjdk.java.net/install/index.html)
- [Okta CLI](https://cli.okta.com)

## Getting Started

To install this example, run the following commands:
```bash
git clone https://github.com/indiepopart/search-service.git
```

## Configure the search-service

```shell
cd search-service
```

With OktaCLI, register for a free developer account:

```shell
okta register
```
Provide the required information. Once you complete the registration, create a client application with the following command:

```shell
okta apps create
```
You will be prompted to select the following options:

- Application name: search-service
- Type of Application: Web
- Type of Application: Okta Spring Boot Starter
- Redirect URI: Default
- Post Logout Redirect URI: Default

The OktaCLI will create the client application and configure the issuer, clientId and clientSecret in `src/main/resources/application.properties`. Update the `issuer`, `client-id` and `client-secret` in `application.yml`. Delete `application.properties`.

```yml
okta:
  oauth2:
    issuer: https://{yourOktaDomain}/oauth2/default
    client-id: {clientId}
    client-secret: {clientSecret}
```

For the Github client registration, you need a GitHub account. Sign in at GitHub and go to the top-right user menu and choose **Settings**. Then on the left menu, choose **Developer settings**. From the left menu, select **OAuth Apps**, then click on **New OAuth App**. For the example, set the following values:

- Application name: search-service
- Homepage URL: http://localhost:8080
- Authorization callback URL: http://localhost:8080

Click **Register application**. Now, on the application page, click on **Generate a new client secret**. Copy the **Client ID** and the generated **Client secret**. Update the values in `application.yml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: {githubClientID}
            client-secret: {githubClientSecret}
```            


## Run the application

```shell
./mvnw spring-boot:run
```

Open an incognito window and go to http://localhost:8080/totalCount/root:%20DEBUG to request a code search for the string `root: DEBUG`. First, the authentication flow will redirect to Okta for the login. Sign in with your Okta credentials. Spring Security will trigger the GitHub authorization flow, to instantiate the authorized client required for the Api request. Sign in with your GitHub credentials and authorize the application. After the authorization, the search result will show a response similar to the following:

```json
{"total_count":114761362}
```
