package ozden.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ozden.entities.IPAddressToVoteTable;
import ozden.entities.TableItem;
import ozden.entities.VoteTable;
import ozden.repos.IPAddressToVoteTableRepository;
import ozden.repos.TableItemRepository;
import ozden.repos.VoteTableRepository;


@Component
public class VoteService {
	@Autowired
	private VoteTableRepository voteTableRepository;
	
	@Autowired
	private TableItemRepository tableItemRepository;
	
	@Autowired
	IPAddressToVoteTableRepository ipAddressToVoteTableRepository;
	
	public VoteTable createVoteTable(String tableName, Date expire) throws Exception{
//		expire.get
//		LocalDateTime expireDate = LocalDateTime.ofEpochSecond(expire.getTime(), 0, 0);
		Date now = Calendar.getInstance().getTime();
		if (tableName == null || "".equals(tableName)){
			throw new Exception("invalid table name!");
		}
		
		if (now.compareTo(expire) >= 0){
			throw new Exception("expire date cannot be smaller than current time");
		}
		
		Calendar cal = Calendar.getInstance();
		VoteTable table = new VoteTable(tableName, cal.getTime(), expire, "");
		// save table to get a uniq id
		table = voteTableRepository.save(table);
		table.setTableURL("/all-votes/" + table.getTableID());
		// save again after changing table url
		table = voteTableRepository.save(table);
		return table;
	}
	
	public boolean deleteVoteTable(Integer tableID, String authKey) throws Exception{
		if (tableID < 0){
			throw new Exception("inavlid table ID!");
		}
		
		VoteTable table = voteTableRepository.findOne(tableID);
		if (table == null){
			throw new Exception("Table doesn't exist in DB!");
		}
		if (!table.getAuthKey().equals(authKey)){
			throw new Exception("invalid authkey!");
		}
		voteTableRepository.delete(table);
		
		// delete all items corresponding vote table
		List<TableItem> allItems = tableItemRepository.findByVoteTableID(tableID);
		if (allItems != null && !allItems.isEmpty()){
			for (TableItem tableItem : allItems) {
				tableItemRepository.delete(tableItem);
			}
		}
		return true;
	}
	
	public List<VoteTable> getVoteTable(String tableName){
		return voteTableRepository.findByVoteTableName(tableName);
	}
	
	public List<VoteTable> getVoteTable(Integer tableID){
		return voteTableRepository.findByTableID(tableID);
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
	
	public boolean addVoteItemsToVoteTable(List<TableItem> items) throws Exception{
		for (TableItem item : items) {
			if (item == null){
				throw new NullPointerException("null item!");
			}
			if (item.getVoteTableID() == null){
				throw new NullPointerException("item doesnt contain table id. Invalid item!!!");
			}
			
			VoteTable table = voteTableRepository.findOne(item.getVoteTableID());
			if (table == null){
				throw new Exception("Table doesn't exist in DB!" + item.getVoteTableID());
			}
			
			tableItemRepository.save(item);
		}
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
		
		if (!item.getVoteTableID().equals(table.getTableID())){
			throw new Exception("table id does not match with item table id!!!");
		}
		// increase the score of the item
		item.setItemScore(item.getItemScore()+1);
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
	
	public List<TableItem> getVoteTableItemsById(Integer tableID, Integer itemID) throws Exception{
		// check id here
		if (tableID < 0){
			throw new Exception("invalid table ID!");
		}
		if (itemID == -1){
			return tableItemRepository.findByVoteTableID(tableID);
		}
		else {
			return tableItemRepository.findByVoteTableIDAndItemID(tableID, itemID);
		}
			
	}
	
	public Boolean isIPaddrUsedForVotingBefore(String ipAddr, Integer voteTableID){
		if (ipAddr == null || voteTableID == null){
			throw new NullPointerException("one of the item is null!");
		}
		
		if (!ipAddressToVoteTableRepository.findByIpAddressAndVoteTableID(ipAddr, voteTableID).isEmpty()){
			return true;
		}
		return false;
	}
	
	public void saveIP2Table(IPAddressToVoteTable ip2table){
		IPAddressToVoteTable savedData = ipAddressToVoteTableRepository.save(ip2table);
		System.out.println("Saved data: " + savedData);
	}
	

	
}
