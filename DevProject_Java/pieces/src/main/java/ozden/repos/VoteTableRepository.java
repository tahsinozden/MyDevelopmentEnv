package ozden.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.entities.VoteTable;

@Transactional
public interface VoteTableRepository extends JpaRepository<VoteTable, Integer>{
	public List<VoteTable> findByVoteTableName(String name);
	public List<VoteTable> findByTableID(Integer tableID);
}
