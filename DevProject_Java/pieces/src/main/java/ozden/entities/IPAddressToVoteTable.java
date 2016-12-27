package ozden.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class IPAddressToVoteTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue
	private Integer recId;
	
	@Column
	private String ipAddress;
	
	@Column
	private Integer voteTableID;
	
	public IPAddressToVoteTable(){}

	public IPAddressToVoteTable(String ipAddress, Integer voteTableID) {
		super();
		this.ipAddress = ipAddress;
		this.voteTableID = voteTableID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getVoteTableID() {
		return voteTableID;
	}

	public void setVoteTableID(Integer voteTableID) {
		this.voteTableID = voteTableID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + ((recId == null) ? 0 : recId.hashCode());
		result = prime * result + ((voteTableID == null) ? 0 : voteTableID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IPAddressToVoteTable other = (IPAddressToVoteTable) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (recId == null) {
			if (other.recId != null)
				return false;
		} else if (!recId.equals(other.recId))
			return false;
		if (voteTableID == null) {
			if (other.voteTableID != null)
				return false;
		} else if (!voteTableID.equals(other.voteTableID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IPAddressToVoteTable [recId=" + recId + ", ipAddress=" + ipAddress + ", voteTableID=" + voteTableID
				+ "]";
	}


	

}
