
package apps.webservices.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CreateCustomerReq", namespace = "http://webservices.apps/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateCustomerReq", namespace = "http://webservices.apps/", propOrder = {
    "custID",
    "name",
    "acctID",
    "effTime",
    "expTime"
})
public class CreateCustomerReq {

    @XmlElement(name = "custID", namespace = "")
    private int custID;
    @XmlElement(name = "name", namespace = "")
    private String name;
    @XmlElement(name = "acctID", namespace = "")
    private int acctID;
    @XmlElement(name = "effTime", namespace = "")
    private String effTime;
    @XmlElement(name = "expTime", namespace = "")
    private String expTime;

    /**
     * 
     * @return
     *     returns int
     */
    public int getCustID() {
        return this.custID;
    }

    /**
     * 
     * @param custID
     *     the value for the custID property
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @param name
     *     the value for the name property
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     returns int
     */
    public int getAcctID() {
        return this.acctID;
    }

    /**
     * 
     * @param acctID
     *     the value for the acctID property
     */
    public void setAcctID(int acctID) {
        this.acctID = acctID;
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
