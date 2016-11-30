package ozden.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TableItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column
	private Integer itemID;
	@Column
	private Integer voteTableID;
	@Column
	private String itemName;
	@Column
	private Integer itemScore;
	
	public TableItem(){}
	
	public TableItem(Integer itemID, Integer voteTableID, String itemName, Integer itemScore) {
		super();
		this.itemID = itemID;
		this.voteTableID = voteTableID;
		this.itemName = itemName;
		this.itemScore = itemScore;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	public Integer getVoteTableID() {
		return voteTableID;
	}
	public void setVoteTableID(Integer voteTableID) {
		this.voteTableID = voteTableID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getItemScore() {
		return itemScore;
	}
	public void setItemScore(Integer itemScore) {
		this.itemScore = itemScore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemID == null) ? 0 : itemID.hashCode());
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((itemScore == null) ? 0 : itemScore.hashCode());
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
		TableItem other = (TableItem) obj;
		if (itemID == null) {
			if (other.itemID != null)
				return false;
		} else if (!itemID.equals(other.itemID))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (itemScore == null) {
			if (other.itemScore != null)
				return false;
		} else if (!itemScore.equals(other.itemScore))
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
		return "TableItem [itemID=" + itemID + ", voteTableID=" + voteTableID + ", itemName=" + itemName
				+ ", itemScore=" + itemScore + "]";
	}
	
	
}
