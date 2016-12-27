package ozden.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ozden.entities.TableItem;
import ozden.entities.VoteTable;
import ozden.entities.VoteTableForm;

@CrossOrigin(origins = "http://localhost:9000")
@Controller
@RequestMapping("all-votes")
public class VoteServingController {
	@Autowired
	private VoteService voteService;
	
	@RequestMapping(value="{tableID}", method=RequestMethod.GET)
	public String getVotesFromTable(@PathVariable("tableID") Integer tableID,  Model model) 
																   throws Exception{
		VoteTable destTable = null;
		Optional opt = 
				voteService.getVoteTable(tableID)
				.stream()
				.findAny();
		if (opt.isPresent()){
			destTable = (VoteTable) opt.get();
		}
		else {
			destTable = new VoteTable();
		}

        model.addAttribute("name", destTable.getVoteTableName());
		// itemId is sent as -1 to get all items in the table
        List<TableItem> itemsFromTable = voteService.getVoteTableItemsById(tableID, -1);
        model.addAttribute("item", itemsFromTable.get(0).getItemName());
        
        VoteTableForm tableForm = new VoteTableForm(destTable, itemsFromTable);
        model.addAttribute("voteTableForm", tableForm);
        
        // VoteTablePresenter is VoteTablePresenter.html inside 'templates' folder.
        return "VoteTablePresenter";
	}
}
