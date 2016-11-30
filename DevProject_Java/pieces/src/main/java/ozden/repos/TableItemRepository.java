package ozden.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.entities.TableItem;


public interface TableItemRepository extends JpaRepository<TableItem, Integer>{
	public List<TableItem> findByVoteTableID(Integer tableID);
	public List<TableItem> findByVoteTableIDAndItemID(Integer tableID, Integer itemID);
}
