package ozden.controllers;

import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import ozden.entities.TableItem;
import ozden.entities.VoteTable;

//@CrossOrigin(origins = "http://localhost:9000")
@CrossOrigin(origins = "*")
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
	
	@RequestMapping(value="{tableID}", method=RequestMethod.DELETE)
	public Boolean deleteVoteTable(@PathVariable("tableID") Integer tableID,
				   				   @RequestBody VoteTable voteTable) throws Exception{		
		return voteService.deleteVoteTable(tableID, voteTable.getAuthKey());

	}
	
	@RequestMapping(value="{tableID}", method=RequestMethod.GET)
	public ResponseEntity<VoteTable> getVoteTable(@PathVariable("tableID") Integer tableID) throws Exception{
		
		List<VoteTable> voteTables = voteService.getVoteTable(tableID);
		VoteTable voteTable = !voteTables.isEmpty() ? voteTables.get(0) : new VoteTable();
		return new ResponseEntity<VoteTable>(voteTable, HttpStatus.OK);
	}
	
	@RequestMapping(value="{tableID}", method=RequestMethod.PUT)
	public ResponseEntity<List<TableItem>> addVotesToVoteTable(@PathVariable("tableID") Integer tableID,
														 @RequestBody List<TableItem> tableItems ) throws Exception{
		if (tableItems == null || tableItems.isEmpty()){
			throw new NullPointerException("no table items!");
		}
		
		List<VoteTable> tables = voteService.getVoteTable(tableID);
		if (tables.isEmpty()){
			throw new Exception("table not exist!");
		}
		
		// check if there is any table item whose tableID doesn't match with the request
		if (tableItems
				.stream()
				.filter(item -> item != null)
// == sign only works for numbers between -128 and 127				
//				.filter(item -> item.getVoteTableID() != tableID)
				.filter(item -> !item.getVoteTableID().equals(tableID))
				.count() > 0){
				throw new Exception("table id is different than one of the items table id");
		}
		
		boolean res = voteService.addVoteItemsToVoteTable(tableItems);
		return (res ? new ResponseEntity<List<TableItem>>(voteService.getVoteTableItems(tableID), HttpStatus.OK) :
				new ResponseEntity<List<TableItem>>(HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@RequestMapping(value="{tableID}/{tableItemID}", method=RequestMethod.POST)
	public ResponseEntity<List<TableItem>> doVoteForAnItem(@PathVariable("tableID") Integer tableID,
														   @PathVariable("tableItemID") Integer tableItemID) 
																   throws Exception{
		boolean res = voteService.doVote(tableID, tableItemID);
		return (res ? new ResponseEntity<List<TableItem>>(voteService.getVoteTableItems(tableID), HttpStatus.OK) :
			new ResponseEntity<List<TableItem>>(HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@RequestMapping(value="{tableID}/{tableItemID}", method=RequestMethod.GET)
	public ResponseEntity<List<TableItem>> getVotesFromTable(@PathVariable("tableID") Integer tableID,
														     @PathVariable("tableItemID") Integer tableItemID) 
																   throws Exception{
		
		return new ResponseEntity<List<TableItem>>(voteService.getVoteTableItemsById(tableID, tableItemID), HttpStatus.OK);
	}
	
}
