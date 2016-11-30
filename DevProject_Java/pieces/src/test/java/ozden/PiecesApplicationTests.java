package ozden;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ozden.controllers.VoteController;
import ozden.controllers.VoteService;
import ozden.entities.TableItem;
import ozden.entities.VoteTable;
import ozden.repos.TableItemRepository;
import ozden.repos.VoteTableRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class PiecesApplicationTests {

	final Integer tableID = 3;
	final Integer itemID = 34;
	
	private Date now = Calendar.getInstance().getTime();
	
//	@Autowired
	private VoteController voteController;
	
//	@Autowired
	@InjectMocks
	private VoteService voteService;
	
	@Autowired
	@Mock
	private VoteTableRepository voteTableRepository;
	
	@Autowired
	@Mock
	private TableItemRepository tableItemRepository;
	
	@Before
	public void setUp(){
		voteController = new VoteController();
	}
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void shouldAddaNewVoteWithValidTableID() throws Exception{
//		when(voteTableRepository.findOne(tableID)).thenReturn(new VoteTable("Test Vote", LocalDateTime.now(), LocalDateTime.now()));
		when(voteTableRepository.findOne(tableID)).thenReturn(new VoteTable("Test Vote", now, now, ""));
		when(tableItemRepository.findOne(itemID)).thenReturn(new TableItem());
		assertTrue(voteService.doVote(tableID, itemID));
	}
	
	@Test(expected=Exception.class)
	public void shouldThowExceptionWhileAddingANewVoteWithInvalidTableID() throws Exception{
		voteService.doVote(-1 * tableID, itemID);
	}
	
	@Test(expected=Exception.class)
	public void shouldThowExceptionWhileAddingANewInvalidVoteWithValidTableID() throws Exception{
		voteService.doVote(tableID, -1 * itemID);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldThrowNullPointerExeptionWhenTableIDNotFoundInDB() throws Exception{
		when(voteTableRepository.findOne(tableID)).thenReturn(null);
		when(tableItemRepository.findOne(itemID)).thenReturn(new TableItem());
		assertTrue(voteService.doVote(tableID, itemID));
	}

	@Test(expected=NullPointerException.class)
	public void shouldThrowNullPointerExeptionWhenItemIDNotFoundInDB() throws Exception{
//		when(voteTableRepository.findOne(tableID)).thenReturn(new VoteTable("Test Vote", LocalDateTime.now(), LocalDateTime.now()));
		when(voteTableRepository.findOne(tableID)).thenReturn(new VoteTable("Test Vote", now, now, ""));
		when(tableItemRepository.findOne(itemID)).thenReturn(null);
		assertTrue(voteService.doVote(tableID, itemID));
	}
	
	@Test
	public void shouldReturnVoteTableWithAValidTableID() throws Exception{
		List<TableItem> items = Arrays.asList(
				new TableItem(1, 3, "item1", 1),
				new TableItem(2, 3, "item2", 12),
				new TableItem(3, 3, "item3", 23421),
				new TableItem(4, 3, "item4", 124)
				);
		
//		when(voteTableRepository.findOne(tableID)).thenReturn(new VoteTable("Test Vote Table", LocalDateTime.now(), LocalDateTime.now()));
		when(voteTableRepository.findOne(tableID)).thenReturn(new VoteTable("Test Vote Table", now, now, ""));
		when(tableItemRepository.findByVoteTableID(3))
			.thenReturn(items);
		
		assertArrayEquals(items.toArray(), (voteService.getVoteTableItems(tableID)).toArray() );
		
	}
	
	@Test
	public void shouldReturnEmptyListWhenThereAreNoRecordsForSpecifiedTableID(){
//		when(voteTableRepository.findOne(tableID)).thenReturn(new VoteTable("Test Vote", LocalDateTime.now(), LocalDateTime.now()));
		when(voteTableRepository.findOne(tableID)).thenReturn(new VoteTable("Test Vote", now, now, ""));
		when(tableItemRepository.findByVoteTableID(tableID)).thenReturn(null);
		
	}
	

}
