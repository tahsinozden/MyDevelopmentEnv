
package apps.webservices.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CheckCustomerExist", namespace = "http://webservices.apps/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckCustomerExist", namespace = "http://webservices.apps/", propOrder = {
    "custId",
    "effTime",
    "expTime"
})
public class CheckCustomerExist {

    @XmlElement(name = "custId", namespace = "")
    private int custId;
    @XmlElement(name = "effTime", namespace = "")
    private String effTime;
    @XmlElement(name = "expTime", namespace = "")
    private String expTime;

    /**
     * 
     * @return
     *     returns int
     */
    public int getCustId() {
        return this.custId;
    }

    /**
     * 
     * @param custId
     *     the value for the custId property
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getEffTime() {
        return this.effTime;
    }

    /**
     * 
     * @param effTime
     *     the value for the effTime property
     */
    public void setEffTime(String effTime) {
        this.effTime = effTime;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getExpTime() {
        return this.expTime;
    }

    /**
     * 
     * @param expTime
     *     the value for the expTime property
     */
    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

}
