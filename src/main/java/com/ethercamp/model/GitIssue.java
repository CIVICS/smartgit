package com.ethercamp.model;

public class GitIssue {

    String  organisation;
    String  user;
    Integer index;

    public GitIssue() {
    }

    public GitIssue(String organisation, String user, Integer index) {
        this.organisation = organisation;
        this.user = user;
        this.index = index;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitIssue gitIssue = (GitIssue) o;

        if (organisation != null ? !organisation.equals(gitIssue.organisation) : gitIssue.organisation != null)
            return false;
        if (user != null ? !user.equals(gitIssue.user) : gitIssue.user != null) return false;
        return !(index != null ? !index.equals(gitIssue.index) : gitIssue.index != null);

    }

    @Override
    public int hashCode() {
        int result = organisation != null ? organisation.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (index != null ? index.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GitIssue{" +
                "organisation='" + organisation + '\'' +
                ", user='" + user + '\'' +
                ", index=" + index +
                '}';
    }
}
