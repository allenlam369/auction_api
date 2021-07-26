package allen.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// , insertable = false, updatable = false
	@Column(name = "customer_id")
	private Long customerId;

	@Temporal(TemporalType.DATE)
	private Date expiryDate;

	public Account() {

	}

	public Account(Long customerId, Date expiryDate) {
		this.customerId = customerId;
		this.expiryDate = expiryDate;
	}

	public Account(Long customerId) {
		this.customerId = customerId;

		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, 1);
		Date newDate = c.getTime();
		this.expiryDate = newDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
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
		Account other = (Account) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", customerId=" + customerId + ", expiryDate=" + expiryDate + "]";
	}

}
