package ozden.apps.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

@Entity
@Table(name="user_sessions")
public class UserSessions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="session_id")
	private String sessionId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="session_start_time")
	// for full date and time it must be TIMESTAMP
	// DATE is just for YYYY-MM-DD
	@Temporal(TemporalType.TIMESTAMP)
	private Date sessionStartTime;
	
	@Column(name="session_exp_time")
	@Temporal(TemporalType.TIMESTAMP)
	// for full date and time it must be TIMESTAMP
	// DATE is just for YYYY-MM-DD
	private Date sessionExpTime;
	
	protected UserSessions(){
		
	}

	public UserSessions(String sessionId, String userName, Date sessionStartTime, Date sessionExptTime) {
		super();
		this.sessionId = sessionId;
		this.userName = userName;
		this.sessionStartTime = sessionStartTime;
		this.sessionExpTime = sessionExptTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getSessionStartTime() {
		return sessionStartTime;
	}

	public void setSessionStartTime(Date sessionStartTime) {
		this.sessionStartTime = sessionStartTime;
	}

	public Date getSessionExptTime() {
		return sessionExpTime;
	}

	public void setSessionExptTime(Date sessionExptTime) {
		this.sessionExpTime = sessionExptTime;
	}

	@Override
	public String toString() {
		return "UserSessions [sessionId=" + sessionId + ", userName=" + userName + ", sessionStartTime="
				+ sessionStartTime + ", sessionExptTime=" + sessionExpTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sessionExpTime == null) ? 0 : sessionExpTime.hashCode());
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((sessionStartTime == null) ? 0 : sessionStartTime.hashCode());
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
		UserSessions other = (UserSessions) obj;
		if (sessionExpTime == null) {
			if (other.sessionExpTime != null)
				return false;
		} else if (!sessionExpTime.equals(other.sessionExpTime))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (sessionStartTime == null) {
			if (other.sessionStartTime != null)
				return false;
		} else if (!sessionStartTime.equals(other.sessionStartTime))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
}
