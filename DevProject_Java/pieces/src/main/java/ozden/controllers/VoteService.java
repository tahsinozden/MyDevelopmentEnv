package ozden.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ozden.entities.TableItem;
import ozden.entities.VoteTable;
import ozden.repos.TableItemRepository;
import ozden.repos.VoteTableRepository;


@Component
public class VoteService {
	@Autowired
	private VoteTableRepository voteTableRepository;
	
	@Autowired
	private TableItemRepository tableItemRepository;
	
	public VoteTable createVoteTable(String tableName, LocalDateTime expireDate) throws Exception{
		if (tableName == null || "".equals(tableName)){
			throw new Exception("invalid table name!");
		}
		
		if (LocalDateTime.now().compareTo(expireDate) >= 0){
			throw new Exception("expire date cannot be smaller than current time");
		}
		
		VoteTable table = new VoteTable(tableName, LocalDateTime.now(), expireDate);
		voteTableRepository.save(table);
		return table;
	}
	
	public boolean deleteVoteTable(Integer tableID, String authKey) throws Exception{
		if (tableID < 0){
			throw new Exception("inavlid table ID!");
		}
		
		VoteTable table = voteTableRepository.findOne(tableID);
		if (table == null){
			throw new Exception("Table doesnt exist in DB!");
		}
		if (!table.getAuthKey().equals(authKey)){
			throw new Exception("invalid authkey!");
		}
		voteTableRepository.delete(table);
		return true;
	}
	
	public boolean addVoteItemToVoteTable(TableItem item) throws Exception{
		if (item == null){
			throw new NullPointerException("null item!");
		}
		if (item.getVoteTableID() == null){
			throw new NullPointerException("item doesnt contain table id. Invalid item!!!");
		}
		
		VoteTable table = voteTableRepository.findOne(item.getVoteTableID());
		if (table == null){
			throw new Exception("Table doesn't exist in DB!");
		}
		
		tableItemRepository.save(item);
		return true;
	}
	/**
	 * It adds a score for voted item in vote table
	 * @param tableID
	 * @param itemID
	 * @return
	 * @throws Exception 
	 */
	public boolean doVote(Integer tableID, Integer itemID) throws Exception{
		if (tableID < 0){
			throw new Exception("invalid table ID!");
		}
		
		if (itemID < 0){
			throw new Exception("invalid item ID!");
		}
		
		VoteTable table = voteTableRepository.findOne(tableID);
		TableItem item = tableItemRepository.findOne(itemID);
		if (table == null){
			throw new NullPointerException("Table id not exist!");
		}
		
		if (item == null){
			throw new NullPointerException("item id not exist!");
		}
		
		// increase the score of the item
		item.setItemScore(item.getItemScore());
		tableItemRepository.save(item);
		return true;
	}
	
	public List<TableItem> getVoteTableItems(Integer tableID) throws Exception{
		// check id here
		if (tableID < 0){
			throw new Exception("invalid table ID!");
		}

		return tableItemRepository.findByVoteTableID(tableID);
	}
	

	
}
