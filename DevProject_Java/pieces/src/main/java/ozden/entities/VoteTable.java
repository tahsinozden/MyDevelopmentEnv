package ozden.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VoteTable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer tableID;
	
	@Column
	private String voteTableName;
	
	@Column
//	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate; 
	
	@Column
//	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;
	
	@Column
//	@GeneratedValue
	private String authKey;
	
	@Column
	private String tableURL;
	
	public VoteTable() {
	}

	public VoteTable(VoteTable vt) {
		this.voteTableName = vt.getVoteTableName();
		this.creationDate = vt.getCreationDate();
		this.expiryDate = vt.getExpiryDate();
		this.authKey = vt.getAuthKey();
	}
	
//	public VoteTable(String voteTableName, LocalDateTime creationDate, LocalDateTime expiryDate) {
	public VoteTable(String voteTableName, Date creationDate, Date expiryDate, String tableURL) {
		super();
		this.voteTableName = voteTableName;
		this.creationDate = creationDate;
		this.expiryDate = expiryDate;
		this.authKey = String.valueOf(Instant.now().getEpochSecond());
//		this.tableURL = "/all-votes/" + this.tableID;
	}

	public Integer getTableID() {
		return tableID;
	}

	public void setTableID(Integer tableID) {
		this.tableID = tableID;
	}

	public String getVoteTableName() {
		return voteTableName;
	}

	public void setVoteTableName(String voteTableName) {
		this.voteTableName = voteTableName;
	}


	public String getAuthKey() {
		return authKey;
	}


	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate =  creationDate ;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	public String getTableURL() {
		return tableURL;
	}

	public void setTableURL(String tableURL) {
		this.tableURL = tableURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authKey == null) ? 0 : authKey.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((tableID == null) ? 0 : tableID.hashCode());
		result = prime * result + ((voteTableName == null) ? 0 : voteTableName.hashCode());
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
		VoteTable other = (VoteTable) obj;
		if (authKey == null) {
			if (other.authKey != null)
				return false;
		} else if (!authKey.equals(other.authKey))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (tableID == null) {
			if (other.tableID != null)
				return false;
		} else if (!tableID.equals(other.tableID))
			return false;
		if (voteTableName == null) {
			if (other.voteTableName != null)
				return false;
		} else if (!voteTableName.equals(other.voteTableName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VoteTable [tableID=" + tableID + ", voteTableName=" + voteTableName + ", creationDate=" + creationDate
				+ ", expiryDate=" + expiryDate + ", authKey=" + authKey + ", tableURL=" + tableURL + "]";
	}







	
}
