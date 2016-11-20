package ozden.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.entities.VoteTable;

@Transactional
public interface VoteTableRepository extends JpaRepository<VoteTable, Integer>{

}
