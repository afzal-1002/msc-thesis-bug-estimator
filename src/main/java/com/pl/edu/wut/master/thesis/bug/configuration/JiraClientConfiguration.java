package com.pl.edu.wut.master.thesis.bug.configuration;

import com.pl.edu.wut.master.thesis.bug.dto.user.usersession.SessionContext;
import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
import com.pl.edu.wut.master.thesis.bug.exception.UserNotAuthorizedException;
import com.pl.edu.wut.master.thesis.bug.model.user.SessionAttributes;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Slf4j
public class JiraClientConfiguration {

    @Value("${jira.api-version}")
    private String jiraApiVersion;


    private final RestTemplate restTemplate;
    private final HttpSession session;

    private static final String ATTR_ISSUE_KEY = "issueKey";
    private static final String ATTR_ISSUE_ID = "issueId";
    private static final String ATTR_USERNAME = "username";
    private static final String ATTR_LOGIN_NAME = "loginName";
    private static final String ATTR_TOKEN = "token";
    private static final String ATTR_HOST_URL = "hostUrl";
    private static final String ATTR_INSTANCE_ID = "instanceId";
    private static final String ATTR_PROJECT_KEY = "projectKey";
    private static final String ATTR_PROJECT_ID = "projectId";
    private static final String ATTR_USER_ID = "userId";
    private static final String ATTR_ACCOUNT_ID = "accountId";

    public void saveSelectedIssueKey(String issueKey, Long issueId) {
        if (issueKey == null || issueKey.isBlank()) {
            throw new ResourceNotFoundException("Cannot select blank issue key");
        }
        session.setAttribute(ATTR_ISSUE_KEY, issueKey);
        session.setAttribute(ATTR_ISSUE_ID, issueId);
    }

    public String getSelectedIssueKey() {
        String key = (String) session.getAttribute(ATTR_ISSUE_KEY);
        if (key == null || key.isBlank()) {
            throw new ResourceNotFoundException("No Jira issue has been selected");
        }
        return key;
    }

    /**
     * Reads all supported session attributes into local variables,
     * then returns them in a SessionAttributes object (any may be null).
     */
    public SessionAttributes getSessionAttributes() {
        // 1) pull each attribute into a local variable
        String issueKey = (String) session.getAttribute(ATTR_ISSUE_KEY);
        Long issueId = (Long) session.getAttribute(ATTR_ISSUE_ID);
        String username = (String) session.getAttribute(ATTR_USERNAME);
        String loginName = (String) session.getAttribute(ATTR_LOGIN_NAME);
        String token = (String) session.getAttribute(ATTR_TOKEN);
        String hostUrl = (String) session.getAttribute(ATTR_HOST_URL);
        Long instanceId = (Long) session.getAttribute(ATTR_INSTANCE_ID);
        String projectKey = (String) session.getAttribute(ATTR_PROJECT_KEY);
        Long projectId = (Long) session.getAttribute(ATTR_PROJECT_ID);
        Long userId = (Long) session.getAttribute(ATTR_USER_ID);
        String accountId = (String) session.getAttribute(ATTR_ACCOUNT_ID);


        // 2) pack into your POJO
        SessionAttributes attributes = new SessionAttributes();

        attributes.setIssueKey(issueKey);
        attributes.setIssueId(issueId);
        attributes.setUsername(username);
        attributes.setLoginName(loginName);
        attributes.setToken(token);
        attributes.setHostUrl(hostUrl);
        attributes.setInstanceId(instanceId);
        attributes.setProjectKey(projectKey);
        attributes.setProjectId(projectId);
        attributes.setUserId(userId);
        attributes.setAccountId(accountId);

        return attributes;
    }


    public SessionContext loadSessionContext() {
        // 1) pull each attribute into its own local
        String issueKey = (String) session.getAttribute(ATTR_ISSUE_KEY);
        Long issueId = (Long) session.getAttribute(ATTR_ISSUE_ID);
        String username = (String) session.getAttribute(ATTR_USERNAME);
        String loginName = (String) session.getAttribute(ATTR_LOGIN_NAME);
        String token = (String) session.getAttribute(ATTR_TOKEN);
        String hostUrl = (String) session.getAttribute(ATTR_HOST_URL);
        Long instanceId = (Long) session.getAttribute(ATTR_INSTANCE_ID);
        String projectKey = (String) session.getAttribute(ATTR_PROJECT_KEY);
        Long projectId = (Long) session.getAttribute(ATTR_PROJECT_ID);
        Long userId = (Long) session.getAttribute(ATTR_USER_ID);
        String accountId = (String) session.getAttribute(ATTR_ACCOUNT_ID);

        // 2) normalize the host URL if present
        String base = (hostUrl == null ? null : normalizeHostUrl(hostUrl));

        // 3) return them all in your SessionContext
        return new SessionContext(issueKey, issueId, username, loginName, token, base,
                instanceId, projectKey, projectId, userId, accountId);
    }

    public void clearSelectedIssueKey() {
        session.removeAttribute(ATTR_ISSUE_KEY);
        session.removeAttribute(ATTR_ISSUE_ID);
    }

    private HttpHeaders createHeaders(String username, String token) {
        String auth = username + ":" + token;
        String encoded = Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encoded);
        return headers;
    }

    private String[] getSessionCredentials() {
        String username = (String) session.getAttribute(ATTR_USERNAME);
        String token = (String) session.getAttribute(ATTR_TOKEN);
        if (username == null || username.isBlank() || token == null || token.isBlank()) {
            throw new UserNotAuthorizedException("Missing Jira credentials in session");
        }
        return new String[]{username, token};
    }

    public String getSessionBaseUrl() {
        String url = (String) session.getAttribute(ATTR_HOST_URL);
        if (url == null || url.isBlank()) {
            throw new ResourceNotFoundException("Missing Jira host URL in session");
        }
        return normalizeHostUrl(url);
    }

    private String normalizeHostUrl(String url) {
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    public HttpHeaders getHttpHeaders() {
        String[] credentials = getSessionCredentials();
        return createHeaders(credentials[0], credentials[1]);
    }

    public String getApiBase() {
        return getSessionBaseUrl() + "/rest/api/" + jiraApiVersion;
    }

    public <T> T get(String path, Class<T> type) {
        String url = getApiBase() + path;
        HttpEntity<Void> req = new HttpEntity<>(getHttpHeaders());
        return restTemplate.exchange(url, HttpMethod.GET, req, type).getBody();
    }

    public <T> T post(String path, Object payload, Class<T> type) {
        String url = getApiBase() + path;
        HttpEntity<Object> req = new HttpEntity<>(payload, getHttpHeaders());
        return restTemplate.exchange(url, HttpMethod.POST, req, type).getBody();
    }

    public <T> T put(String path, Object payload, Class<T> type) {
        String url = getApiBase() + path;
        HttpEntity<Object> req = new HttpEntity<>(payload, getHttpHeaders());
        return restTemplate.exchange(url, HttpMethod.PUT, req, type).getBody();
    }

    public <T> T delete(String path, Class<T> type) {
        String url = getApiBase() + path;
        HttpEntity<Void> req = new HttpEntity<>(getHttpHeaders());
        return restTemplate.exchange(url, HttpMethod.DELETE, req, type).getBody();
    }


}
