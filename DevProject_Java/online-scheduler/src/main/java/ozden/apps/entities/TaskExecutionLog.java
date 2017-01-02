package ozden.apps.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TaskExecutionLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column
	private Integer recId;
	
	@Column
	private Integer taskId;
	
	@Column
	private String taskName;
	
	@Column
	private Date executionDate;
	
	@Column
	private String executionStatus;
	
	@Column
	private String creatorUserName;
	
	public TaskExecutionLog(){}

	public TaskExecutionLog(Integer taskId, String taskName, Date executionDate, String executionStatus,
			String creatorUserName) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.executionDate = executionDate;
		this.executionStatus = executionStatus;
		this.creatorUserName = creatorUserName;
	}

	public Integer getRecId() {
		return recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public String getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}

	public String getCreatorUserName() {
		return creatorUserName;
	}

	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creatorUserName == null) ? 0 : creatorUserName.hashCode());
		result = prime * result + ((executionDate == null) ? 0 : executionDate.hashCode());
		result = prime * result + ((executionStatus == null) ? 0 : executionStatus.hashCode());
		result = prime * result + ((recId == null) ? 0 : recId.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
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
		TaskExecutionLog other = (TaskExecutionLog) obj;
		if (creatorUserName == null) {
			if (other.creatorUserName != null)
				return false;
		} else if (!creatorUserName.equals(other.creatorUserName))
			return false;
		if (executionDate == null) {
			if (other.executionDate != null)
				return false;
		} else if (!executionDate.equals(other.executionDate))
			return false;
		if (executionStatus == null) {
			if (other.executionStatus != null)
				return false;
		} else if (!executionStatus.equals(other.executionStatus))
			return false;
		if (recId == null) {
			if (other.recId != null)
				return false;
		} else if (!recId.equals(other.recId))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskExecutionLog [recId=" + recId + ", taskId=" + taskId + ", taskName=" + taskName + ", executionDate="
				+ executionDate + ", executionStatus=" + executionStatus + ", creatorUserName=" + creatorUserName + "]";
	}
	
	
	
}
