package org.example.jira;

import com.atlassian.jira.rest.client.api.domain.*;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

public class IssueRemotable extends Issue implements Serializable {
    public IssueRemotable(String summary, URI self, String key, Long id, BasicProject project, IssueType issueType, Status status,
          String description, @Nullable BasicPriority priority, @Nullable Resolution resolution, Collection<Attachment> attachments,
          @Nullable User reporter, @Nullable User assignee, DateTime creationDate, DateTime updateDate, DateTime dueDate,
          Collection<Version> affectedVersions, Collection<Version> fixVersions, Collection<BasicComponent> components,
          @Nullable TimeTracking timeTracking, Collection<IssueField> issueFields, Collection<Comment> comments,
          @Nullable URI transitionsUri,
          @Nullable Collection<IssueLink> issueLinks,
          BasicVotes votes, Collection<Worklog> worklogs, BasicWatchers watchers, Iterable<String> expandos,
          @Nullable Collection<Subtask> subtasks, @Nullable Collection<ChangelogGroup> changelog, @Nullable Operations operations,
          Set<String> labels){
        super(summary, self, key, id, project, issueType, status,
                description, priority,  resolution, attachments,
                reporter, assignee, creationDate, updateDate, dueDate,
                affectedVersions, fixVersions, components,
                timeTracking, issueFields, comments,
                transitionsUri, issueLinks, votes, worklogs, watchers, expandos,
                subtasks, changelog,  operations, labels);
    }
}
