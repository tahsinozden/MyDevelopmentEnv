package ozden.apps.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notification_registry")
public class NotificationRegistry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static enum ThresholdType{
		LESS_THAN,
		EQUAL,
		GREATER_THAN,
		NO_THRESHOLD
	}
	
	// id is used to identify the record in DB,
	// i.e. if save() function is called and if there is a record having the same id,
	// then the record is updated instead of creating a new one.
	@Id
	@Column(name="rec_id")
	@GeneratedValue
	private Integer recId;
	
	@Column(name="user_name")
	private String userName;
	
	//!!!! ATTENTION srcCurrencyCode is automatically parsed as src_currency_code
	@Column(name="src_currency_code")
	private String srcCurrencyCode;
	
	@Column(name="dst_currency_code")
	private String dstCurrencyCode;
	
	@Column(name="status")
	private String status;
	
	@Column(name="notic_period")
	private String noticPeriod;
	
	@Column(name="threshold_value")
	private Double thresholdValue;

	@Column(name="threshold_type")
	private String thresholdType;
	
	@Column(name="last_notic_send_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastNoticSendDate;
	
	// PENDING, FAILED, SUCCESS
	@Column(name="email_send_status")
	private String emailSendStatus;
	
	@Column(name="notification_email")
	private String notificationEmail;
	
	@Column(name="current_rate")
	private Double currentRate;
	
	public NotificationRegistry(){
		
	}
	
	
	public NotificationRegistry(String userName, String srcCurrencyCode, String dstCurrencyCode,
			String status, String noticPeriod, Double thresholdValue, ThresholdType thresholdType, String notificationEmail) {
		super();
		this.userName = userName;
		this.srcCurrencyCode = srcCurrencyCode;
		this.dstCurrencyCode = dstCurrencyCode;
		this.status = status;
		this.noticPeriod = noticPeriod;
		this.thresholdValue = thresholdValue;
		this.thresholdType = thresholdType.name();
		this.lastNoticSendDate = new Calendar.Builder().setDate(1990, 1, 1).build().getTime();
		this.emailSendStatus = "PENDING";
		this.notificationEmail = notificationEmail;
		this.currentRate = -1.0;
	}


	public Integer getRecId() {
		return recId;
	}


	public void setRecId(Integer recId) {
		this.recId = recId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNoticPeriod() {
		return noticPeriod;
	}

	public void setNoticPeriod(String noticPeriod) {
		this.noticPeriod = noticPeriod;
	}

	public Double getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(Double thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	
	public String getThresholdType() {
		return thresholdType;
	}


	public void setThresholdType(String thresholdType) {
		this.thresholdType = thresholdType;
	}

	
	public Date getLastNoticSendDate() {
		return lastNoticSendDate;
	}


	public void setLastNoticSendDate(Date lastNoticSendDate) {
		this.lastNoticSendDate = lastNoticSendDate;
	}


	public String getEmailSendStatus() {
		return emailSendStatus;
	}


	public void setEmailSendStatus(String emailSendStatus) {
		this.emailSendStatus = emailSendStatus;
	}


	public String getNotificationEmail() {
		return notificationEmail;
	}


	public void setNotificationEmail(String notificationEmail) {
		this.notificationEmail = notificationEmail;
	}


	public Double getCurrentRate() {
		return currentRate;
	}


	public void setCurrentRate(Double currentRate) {
		this.currentRate = currentRate;
	}


	@Override
	public String toString() {
		return "NotificationRegistry [recId=" + recId + ", userName=" + userName + ", srcCurrencyCode="
				+ srcCurrencyCode + ", dstCurrencyCode=" + dstCurrencyCode + ", status=" + status + ", noticPeriod="
				+ noticPeriod + ", thresholdValue=" + thresholdValue + ", thresholdType=" + thresholdType
				+ ", lastNoticSendDate=" + lastNoticSendDate + ", emailSendStatus=" + emailSendStatus
				+ ", notificationEmail=" + notificationEmail + ", currentRate=" + currentRate + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dstCurrencyCode == null) ? 0 : dstCurrencyCode.hashCode());
		result = prime * result + ((noticPeriod == null) ? 0 : noticPeriod.hashCode());
		result = prime * result + ((srcCurrencyCode == null) ? 0 : srcCurrencyCode.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((thresholdValue == null) ? 0 : thresholdValue.hashCode());
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
		NotificationRegistry other = (NotificationRegistry) obj;
		if (dstCurrencyCode == null) {
			if (other.dstCurrencyCode != null)
				return false;
		} else if (!dstCurrencyCode.equals(other.dstCurrencyCode))
			return false;
		if (noticPeriod == null) {
			if (other.noticPeriod != null)
				return false;
		} else if (!noticPeriod.equals(other.noticPeriod))
			return false;
		if (srcCurrencyCode == null) {
			if (other.srcCurrencyCode != null)
				return false;
		} else if (!srcCurrencyCode.equals(other.srcCurrencyCode))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (thresholdValue == null) {
			if (other.thresholdValue != null)
				return false;
		} else if (!thresholdValue.equals(other.thresholdValue))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	
}
