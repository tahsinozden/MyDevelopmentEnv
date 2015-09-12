
package apps.webservices.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GetCustomer", namespace = "http://webservices.apps/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCustomer", namespace = "http://webservices.apps/")
public class GetCustomer {

    @XmlElement(name = "custId", namespace = "")
    private int custId;

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

}
