package ozden.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {
	
	public String addaNewVote(Integer voteTableID, Integer itemIDToBeVoted){
		return "";
	}
	
	public Map<String, String> getAllVotes(Integer voteTableID){
		return new HashMap<String, String>();
	}
}
