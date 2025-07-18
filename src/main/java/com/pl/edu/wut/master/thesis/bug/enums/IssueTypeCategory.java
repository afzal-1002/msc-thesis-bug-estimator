package com.pl.edu.wut.master.thesis.bug.enums;

public enum IssueTypeCategory {
    BUG,        // Software defect (Jira type: "Bug")
    TASK,       // General work item (Jira type: "Task")
    STORY,      // User story (Jira type: "Story")
    EPIC,
    PROJECT,// Large feature (Jira type: "Epic")
    SUBTASK;    // Child issue (Jira type: "Subtask")

    // Helper method to map Jira's type name to enum
    public static IssueTypeCategory fromJiraName(String jiraTypeName) {
        return switch (jiraTypeName.toUpperCase()) {
            case "BUG" -> BUG;
            case "TASK" -> TASK;
            case "STORY" -> STORY;
            case "EPIC" -> EPIC;
            case "SUBTASK" -> SUBTASK;
            case "PROJECT" -> PROJECT;
            default -> throw new IllegalArgumentException("Unknown issue type: " + jiraTypeName);
        };
    }
}