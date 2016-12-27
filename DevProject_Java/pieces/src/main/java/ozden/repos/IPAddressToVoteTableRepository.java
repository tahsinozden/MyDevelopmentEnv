package ozden.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.entities.IPAddressToVoteTable;

public interface IPAddressToVoteTableRepository extends JpaRepository<IPAddressToVoteTable, Integer>{
	public List<IPAddressToVoteTable> findByVoteTableID(Integer tableID);
	public List<IPAddressToVoteTable> findByIpAddress(String ipAddr);
	public List<IPAddressToVoteTable> findByIpAddressAndVoteTableID(String ipAddr, Integer tableID);

}
