package ozden.apps.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "job_schedule")
public class JobSchedule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private Integer jobId;
	
	@Column
	private String creatorUserName;
	
	@Column
	private Date startDate;
	
	@Column
	private Date endDate;
	
	@Column
	private Boolean isRecurring;
	
	@Column
	private String recurringPeriod;
	
	@Column
	private Boolean isActive;
	
	@Column
	private String cronExpression;
	
	public JobSchedule(){}


	public JobSchedule(String name, Integer jobId, String creatorUserName, Date startDate, Date endDate,
			Boolean isRecurring, String recurringPeriod, Boolean isActive, String cronExpression) {
		super();
		this.name = name;
		this.jobId = jobId;
		this.creatorUserName = creatorUserName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isRecurring = isRecurring;
		this.recurringPeriod = recurringPeriod;
		this.isActive = isActive;
		this.cronExpression = cronExpression;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getCreatorUserName() {
		return creatorUserName;
	}

	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean isRecurring() {
		return isRecurring;
	}

	public void setRecurring(Boolean isRecurring) {
		this.isRecurring = isRecurring;
	}

	public String getRecurringPeriod() {
		return recurringPeriod;
	}

	public void setRecurringPeriod(String recurringPeriod) {
		this.recurringPeriod = recurringPeriod;
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getCronExpression() {
		return cronExpression;
	}


	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creatorUserName == null) ? 0 : creatorUserName.hashCode());
		result = prime * result + ((cronExpression == null) ? 0 : cronExpression.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((isRecurring == null) ? 0 : isRecurring.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((recurringPeriod == null) ? 0 : recurringPeriod.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		JobSchedule other = (JobSchedule) obj;
		if (creatorUserName == null) {
			if (other.creatorUserName != null)
				return false;
		} else if (!creatorUserName.equals(other.creatorUserName))
			return false;
		if (cronExpression == null) {
			if (other.cronExpression != null)
				return false;
		} else if (!cronExpression.equals(other.cronExpression))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (isRecurring == null) {
			if (other.isRecurring != null)
				return false;
		} else if (!isRecurring.equals(other.isRecurring))
			return false;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (recurringPeriod == null) {
			if (other.recurringPeriod != null)
				return false;
		} else if (!recurringPeriod.equals(other.recurringPeriod))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "JobSchedule [id=" + id + ", name=" + name + ", jobId=" + jobId + ", creatorUserName=" + creatorUserName
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", isRecurring=" + isRecurring
				+ ", recurringPeriod=" + recurringPeriod + ", isActive=" + isActive + ", cronExpression="
				+ cronExpression + "]";
	}

	
}
