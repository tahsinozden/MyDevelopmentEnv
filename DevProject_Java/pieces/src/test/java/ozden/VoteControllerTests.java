package ozden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.ast.ITestVisitor;
import org.assertj.core.api.AssertDelegateTarget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ozden.controllers.VoteController;
import ozden.controllers.VoteService;
import ozden.entities.TableItem;
import ozden.entities.VoteTable;
import ozden.repos.TableItemRepository;
import ozden.repos.VoteTableRepository;
import ozden.utils.DateWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class VoteControllerTests {

	final Integer TABLE_ID = 78787;
	final Integer itemID = 34;
	final String TABLE_NAME = "my new table";
	
	private final Date NOW = DateWrapper.getDate(LocalDateTime.now());
	private final Date TOMORROW = DateWrapper.getDate(LocalDateTime.now().plusDays(1));
	
	final VoteTable SAMPLE_VOTETABLE = new VoteTable(TABLE_NAME, NOW, TOMORROW, "");
			
	@InjectMocks
	private VoteController voteController;
	
	@Mock
	private VoteService voteService;
	
	@Mock
	private VoteTableRepository voteTableRepository;
	
	@Mock
	private TableItemRepository tableItemRepository;
	
	@Before
	public void setUp() throws Exception{
		
	}

	
	@Test
	public void shouldCreateVoteTable() throws Exception{
		// set up necessary mocks for create vote table service
		VoteTable tbl = new VoteTable(SAMPLE_VOTETABLE);
		tbl.setTableID(TABLE_ID);
		tbl.setTableURL("/all-votes/" + TABLE_ID);
		
		when(voteService.createVoteTable(TABLE_NAME, TOMORROW))
			.thenReturn(tbl);
		
		assertEquals(tbl, voteController.createVoteTable(SAMPLE_VOTETABLE, TABLE_NAME).getBody());
		
	}
	
	@Test
	public void shouldGetVoteTableWithTableID() throws Exception{
		
		when(voteService.getVoteTable(TABLE_ID))
		.thenReturn(new ArrayList<VoteTable>(Arrays.asList(SAMPLE_VOTETABLE)));
		
		assertEquals(SAMPLE_VOTETABLE, voteController.getVoteTable(TABLE_ID).getBody());
	}
	
	@Test
	public void shouldDeleteVoteTableWithExistingTableIDandAuthKey() throws Exception{
		
		when(voteService.deleteVoteTable(TABLE_ID, "SecretKey"))
		.thenReturn(true);
		
		when(voteService.getVoteTable(TABLE_ID))
		.thenReturn(null);
		
		VoteTable toBeDeleted = new VoteTable(SAMPLE_VOTETABLE);
		toBeDeleted.setAuthKey("SecretKey");
		
		assertTrue(voteController.deleteVoteTable(TABLE_ID, toBeDeleted));
	}
	
	@Test
	public void shouldNotDeleteVoteTableWithExistingTableIDandNoAuthKey() throws Exception{
		
		when(voteService.deleteVoteTable(TABLE_ID, "SecretKey"))
		.thenReturn(true);
		
		when(voteService.getVoteTable(TABLE_ID))
		.thenReturn(null);
		
		VoteTable toBeDeleted = new VoteTable(SAMPLE_VOTETABLE);
		toBeDeleted.setAuthKey("SecretKey1");
		
		assertFalse(voteController.deleteVoteTable(TABLE_ID, toBeDeleted));
	}
	
	@Test
	public void shouldNotDeleteVoteTableWithInvalidTableIDandNoAuthKey() throws Exception{
		
		when(voteService.deleteVoteTable(TABLE_ID, "SecretKey"))
		.thenReturn(true);
		
		when(voteService.getVoteTable(TABLE_ID))
		.thenReturn(null);
		
		VoteTable toBeDeleted = new VoteTable(SAMPLE_VOTETABLE);
		
		assertFalse(voteController.deleteVoteTable(2 * TABLE_ID, toBeDeleted));
	}
	
	@Test
	public void shouldAddVotesToVoteTableWithValidTableAndNonNullTableItems() throws Exception{
		List<TableItem> tableItems = 
				Arrays.asList(
						new TableItem(-1, TABLE_ID, TABLE_NAME, 0),
						new TableItem(-1, TABLE_ID, TABLE_NAME, 0),
						new TableItem(-1, TABLE_ID, TABLE_NAME, 0),
						new TableItem(-1, TABLE_ID, TABLE_NAME, 0));
		
		List<TableItem> expectedTableItems = 
				Arrays.asList(
						new TableItem(1, TABLE_ID, TABLE_NAME, 0),
						new TableItem(2, TABLE_ID, TABLE_NAME, 0),
						new TableItem(3, TABLE_ID, TABLE_NAME, 0),
						new TableItem(4, TABLE_ID, TABLE_NAME, 0));
		
		// mock getting table 
		when(voteService.getVoteTable(TABLE_ID))
			.thenReturn(Arrays.asList(SAMPLE_VOTETABLE));
		
		// mock adding table items
		when(voteService.addVoteItemsToVoteTable(tableItems))
			.thenReturn(true);
		
		// mock getting table items
		when(voteService.getVoteTableItems(TABLE_ID))
			.thenReturn(expectedTableItems);
		
		assertEquals(expectedTableItems.toArray(), voteController.addVotesToVoteTable(TABLE_ID, tableItems).getBody().toArray());
		
	}
	
	@Test(expected=Exception.class)
	public void shouldNotAddVotesToVoteTableWithInvalidTableID() throws Exception{
		List<TableItem> tableItems = 
				Arrays.asList(
						new TableItem(-1, TABLE_ID, TABLE_NAME, 0),
						new TableItem(-1, TABLE_ID, TABLE_NAME, 0),
						new TableItem(-1, TABLE_ID, TABLE_NAME, 0),
						new TableItem(-1, TABLE_ID, TABLE_NAME, 0));
		
		List<TableItem> expectedTableItems = 
				Arrays.asList(
						new TableItem(1, TABLE_ID, TABLE_NAME, 0),
						new TableItem(2, TABLE_ID, TABLE_NAME, 0),
						new TableItem(3, TABLE_ID, TABLE_NAME, 0),
						new TableItem(4, TABLE_ID, TABLE_NAME, 0));
		
		
		// mock adding table items
		when(voteService.addVoteItemsToVoteTable(tableItems))
			.thenReturn(true);
		
		// mock getting table items
		when(voteService.getVoteTableItems(TABLE_ID))
			.thenReturn(expectedTableItems);
		
		voteController.addVotesToVoteTable(TABLE_ID, tableItems).getBody().toArray();
		
	}
	
	@Test
	public void shouldNotAddVotesToVoteTableWithDifferentdTableIDsInsideTableItems() throws Exception{
		List<TableItem> tableItems = 
				Arrays.asList(
						new TableItem(-1, 2 * TABLE_ID, TABLE_NAME, 0),
						new TableItem(-1, 10 * TABLE_ID, TABLE_NAME, 0),
						new TableItem(-1, TABLE_ID, TABLE_NAME, 0),
						new TableItem(-1, 4 * TABLE_ID, TABLE_NAME, 0));
		
//		List<TableItem> expectedTableItems = 
//				Arrays.asList(
//						new TableItem(1, 2 * TABLE_ID, TABLE_NAME, 0),
//						new TableItem(2, 10 * TABLE_ID, TABLE_NAME, 0),
//						new TableItem(3, TABLE_ID, TABLE_NAME, 0),
//						new TableItem(4, 4 * TABLE_ID, TABLE_NAME, 0));
		
		// mock getting table 
		when(voteService.getVoteTable(TABLE_ID))
			.thenReturn(Arrays.asList(SAMPLE_VOTETABLE));
		
		// mock adding table items
		when(voteService.addVoteItemsToVoteTable(tableItems))
			.thenThrow(Exception.class);
		
		
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldNotAddVotesToVoteTableWithValidTableIDButNullTableItems() throws Exception{
		// mock getting table 
		when(voteService.getVoteTable(TABLE_ID))
			.thenReturn(Arrays.asList(SAMPLE_VOTETABLE));
		
		// mock adding table items
		when(voteService.addVoteItemsToVoteTable(null))
			.thenThrow(NullPointerException.class);
		
		voteController.addVotesToVoteTable(TABLE_ID, null).getBody();
		
	}
	
	@Test
	public void shouldDoVoteForanItemWithValidTableItemIDandValidTableItemID() throws Exception{
		
		List<TableItem> tableItems = 
				Arrays.asList(
						new TableItem(itemID, TABLE_ID, TABLE_NAME, 0),
						new TableItem(2, TABLE_ID, TABLE_NAME, 0),
						new TableItem(3, TABLE_ID, TABLE_NAME, 0),
						new TableItem(4, TABLE_ID, TABLE_NAME, 0));
		
		List<TableItem> expectedTableItems = 
				Arrays.asList(
						new TableItem(itemID, TABLE_ID, TABLE_NAME, 1),
						new TableItem(2, TABLE_ID, TABLE_NAME, 0),
						new TableItem(3, TABLE_ID, TABLE_NAME, 0),
						new TableItem(4, TABLE_ID, TABLE_NAME, 0));
		
		// mock add vote for an item
		when(voteService.doVote(TABLE_ID, itemID))
			.thenReturn(true);
		
		// mock getting table 
		when(voteService.getVoteTable(TABLE_ID))
			.thenReturn(Arrays.asList(SAMPLE_VOTETABLE));
		
		// mock getting table items
		when(voteService.getVoteTableItems(TABLE_ID))
			.thenReturn(expectedTableItems);
		
		// check if the vote increased by 1
		assertEquals(
			voteController.doVoteForAnItem(TABLE_ID, itemID)
			.getBody()
			.stream()
			.filter(item -> item.getItemID() == itemID)
			.findFirst()
			.get()
			.getItemScore()
			.intValue(), 1);
		
	}
	
	@Test
	public void shouldNotVoteForAnItemWithInvalidTableID() throws Exception{
		// since there are no mock for tableid and itemid, queries should return null
		assertTrue(voteController.doVoteForAnItem(TABLE_ID, itemID).getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@Test
	public void shouldNotVoteForAnItemWithInvalidItemID() throws Exception{
		// since there are no mock for tableid and itemid, queries should return null
		assertTrue(voteController.doVoteForAnItem(TABLE_ID, itemID).getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@Test
	public void shouldGetAllVotesFromValidTable() throws Exception{
		List<TableItem> expectedTableItems = 
				Arrays.asList(
						new TableItem(itemID, TABLE_ID, TABLE_NAME, 1),
						new TableItem(2, TABLE_ID, TABLE_NAME, 0),
						new TableItem(3, TABLE_ID, TABLE_NAME, 0),
						new TableItem(4, TABLE_ID, TABLE_NAME, 0));
		
		// mock getting table items, -1 means all items
		when(voteService.getVoteTableItemsById(TABLE_ID, -1))
			.thenReturn(expectedTableItems);
		
		ResponseEntity<List<TableItem>> res = voteController.getVotesFromTable(TABLE_ID, -1);
		assertTrue(res.getStatusCode().equals(HttpStatus.OK));
		assertEquals(expectedTableItems.toArray(), res.getBody().toArray());
	}
	
	@Test
	public void shouldGetSpecificVoteFromValidTable() throws Exception{
		List<TableItem> expectedTableItems = 
				Arrays.asList(
						new TableItem(itemID, TABLE_ID, TABLE_NAME, 1),
						new TableItem(2, TABLE_ID, TABLE_NAME, 0),
						new TableItem(3, TABLE_ID, TABLE_NAME, 0),
						new TableItem(4, TABLE_ID, TABLE_NAME, 0));
		
		// mock getting table items, -1 means all items
		when(voteService.getVoteTableItemsById(TABLE_ID, itemID))
			.thenReturn(expectedTableItems);
		
		ResponseEntity<List<TableItem>> res = voteController.getVotesFromTable(TABLE_ID, itemID);
		
		assertTrue(res.getStatusCode().equals(HttpStatus.OK));
		// check if specified item is picked or not
		assertEquals(
				res
				.getBody()
				.stream()
				.filter(item -> item.getItemID() == itemID)
				.findFirst()
				.get()
				.getItemScore()
				.intValue(), 1);
		
	}
}
