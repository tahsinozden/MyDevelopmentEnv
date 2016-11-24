package ozden.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import ozden.entities.VoteTable;

@RestController
@RequestMapping("vote-tables")
public class VoteController {
	@Autowired
	private VoteService voteService;
	
	@RequestMapping(value="{tableName}", method=RequestMethod.POST)
	public ResponseEntity<VoteTable> createVoteTable(@RequestBody VoteTable voteTable, @PathVariable("tableName") String tableName) throws Exception{
		if (voteTable == null){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		if (!tableName.equals(voteTable.getVoteTableName())){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "table names dont match!!!");
		}
		
		if (voteTable.getExpiryDate() == null){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "expire time not exists!!!");
		}
		
		voteTable = voteService.createVoteTable(tableName, voteTable.getExpiryDate());
		return new ResponseEntity<VoteTable>(voteTable, HttpStatus.OK);
	}
	
	@RequestMapping(value="{tableName}", method=RequestMethod.GET)
	public ResponseEntity<VoteTable> getVoteTable(@PathVariable("tableName") String tableName) throws Exception{
		
		List<VoteTable> voteTables = voteService.getVoteTable(tableName);
		VoteTable voteTable = !voteTables.isEmpty() ? voteTables.get(0) : new VoteTable();
		return new ResponseEntity<VoteTable>(voteTable, HttpStatus.OK);
	}
	
	
	// TODO: Implement
	public String addaNewVote(Integer voteTableID, Integer itemIDToBeVoted){
		return "";
	}
	
	// TODO: Implement
	public Map<String, String> getAllVotes(Integer voteTableID){
		return new HashMap<String, String>();
	}
}
