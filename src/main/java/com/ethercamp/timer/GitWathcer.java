package com.ethercamp.timer;

import com.ethercamp.model.GitIssue;
import com.ethercamp.service.EthereumService;
import com.ethercamp.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class GitWathcer {

    List<GitIssue> issues = new ArrayList<>();

    @Autowired
    GitService gitService;

    @Autowired
    EthereumService ethereumService;

    public void addIssue(GitIssue gitIssue){
        issues.add(gitIssue);
    }

    public List<GitIssue> getIssues() {
        return issues;
    }

    @Scheduled(fixedDelay = 5000)
    public void doSome(){

        Iterator<GitIssue> iterator =  issues.iterator();
        while (iterator.hasNext()){

            GitIssue gitIssue = iterator.next();
            System.out.println("Watching for: " + gitIssue);

            boolean isOpen = gitService.isOpenIssueFor(gitIssue.getOrganisation(),
                    gitIssue.getUser(),
                    gitIssue.getIndex());

            if (!isOpen){
                iterator.remove();

                System.out.println("Closing the issue: " + gitIssue);
                ethereumService.reportIssue(gitIssue);

                return;
            }
        }
    }

}
