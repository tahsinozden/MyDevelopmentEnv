package models;
import java.sql.ResultSet;
import java.sql.SQLException;

import apps.general.DBAgent;
import interfacepkg.*;
import java.sql.Date;

public class Customer extends DBAgent implements GeneralModel{
	
	public int id;
	private String name;
	public int acct_id;
        public Date eff_time;
        public Date exp_time;
                
	public Customer(int id, 
                        String name,
                        int acct_id,
                        Date eff_time,
                        Date exp_time) {
		this.id = id;
		this.name = name;
                this.acct_id = acct_id;
                this.eff_time = eff_time;
                this.exp_time = exp_time;
	}
	public Customer(){
		
	}
	@Override
	public void addResultSet(ResultSet rs) {
		try {
                    this.setId((int)rs.getObject("ID"));
                        this.name = (String)rs.getObject("NAME");
                        this.setAcct_id((int)rs.getObject("ACCT_ID"));
                        this.setEff_time(Date.valueOf((String)rs.getObject("EFF_TIME")));
                        this.setExp_time(Date.valueOf((String)rs.getObject("EXP_TIME")));
                        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Object getObj() {
		return this;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    /**
     * @return the acct_id
     */
    public int getAcct_id() {
        return acct_id;
    }

    /**
     * @param acct_id the acct_id to set
     */
    public void setAcct_id(int acct_id) {
        this.acct_id = acct_id;
    }

    /**
     * @return the eff_time
     */
    public Date getEff_time() {
        return eff_time;
    }

    /**
     * @param eff_time the eff_time to set
     */
    public void setEff_time(Date eff_time) {
        this.eff_time = eff_time;
    }

    /**
     * @return the exp_time
     */
    public Date getExp_time() {
        return exp_time;
    }

    /**
     * @param exp_time the exp_time to set
     */
    public void setExp_time(Date exp_time) {
        this.exp_time = exp_time;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getInsertText() {
        // date must be placed between  '' like '%s'
        String text = String.format("INSERT INTO CUSTOMER VALUES (%d, '%s', %d, '%s', '%s')", 
                                    this.id, this.name, this.acct_id, this.eff_time.toString(), this.exp_time.toString());
        return text;
    }


}
