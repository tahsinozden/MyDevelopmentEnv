package ozden.apps.entities;

import java.io.Serializable;

/**
 * 
 * @author tahsin
 * This class can be used as a composite primary key for NotificationRegistry
 */
public class NotificationRegistryPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String userName;
	
	protected String srcCurrencyCode;
	
	protected String dstCurrencyCode;
	
	public NotificationRegistryPK(){
		
	}

	public NotificationRegistryPK(String userName, String srcCurrencyCode, String dstCurrencyCode) {
		super();
		this.userName = userName;
		this.srcCurrencyCode = srcCurrencyCode;
		this.dstCurrencyCode = dstCurrencyCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSrcCurrencyCode() {
		return srcCurrencyCode;
	}

	public void setSrcCurrencyCode(String srcCurrencyCode) {
		this.srcCurrencyCode = srcCurrencyCode;
	}

	public String getDstCurrencyCode() {
		return dstCurrencyCode;
	}

	public void setDstCurrencyCode(String dstCurrencyCode) {
		this.dstCurrencyCode = dstCurrencyCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dstCurrencyCode == null) ? 0 : dstCurrencyCode.hashCode());
		result = prime * result + ((srcCurrencyCode == null) ? 0 : srcCurrencyCode.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		NotificationRegistryPK other = (NotificationRegistryPK) obj;
		if (dstCurrencyCode == null) {
			if (other.dstCurrencyCode != null)
				return false;
		} else if (!dstCurrencyCode.equals(other.dstCurrencyCode))
			return false;
		if (srcCurrencyCode == null) {
			if (other.srcCurrencyCode != null)
				return false;
		} else if (!srcCurrencyCode.equals(other.srcCurrencyCode))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
}