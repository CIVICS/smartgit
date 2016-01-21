package com.ethercamp.rest;

import com.ethercamp.model.GitIssue;
import com.ethercamp.service.GitService;
import com.ethercamp.timer.GitWathcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class SmartRestController {

    @Autowired
    GitService gitService;

    @Autowired
    GitWathcer gitWathcer;


    @RequestMapping(value = "/issue/save/{org}/{user}/{index}" , method = GET)
    @ResponseBody
    public String recordIssue(@PathVariable String org,
                              @PathVariable String user,
                              @PathVariable Integer index){
        String log =
                String.format("Saving for org: %s, user: %s, index: %s", org , user, index);

        System.out.println(log);

        boolean issueExist = gitService.isOpenIssueFor(org, user, index);

        if (issueExist){

            GitIssue gitIssue = new GitIssue(org, user, index);
            gitWathcer.addIssue(gitIssue);

            return "Watching now: " + gitIssue.toString();
        }
        else
            return "No such issue to watch";
    }

    @RequestMapping(value = "/issues" , produces= APPLICATION_JSON_VALUE, method = GET)
    @ResponseBody
    public List<GitIssue> hashes(){
        return gitWathcer.getIssues();
    }


}
