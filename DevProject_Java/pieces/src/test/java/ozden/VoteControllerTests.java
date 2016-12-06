package ozden;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ozden.controllers.VoteController;
import ozden.controllers.VoteService;
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

}
