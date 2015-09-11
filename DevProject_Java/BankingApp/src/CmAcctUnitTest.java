import java.sql.Date;

import models.*;

public class CmAcctUnitTest {

	public static void main(String[] args) {
		CmAcct cm = new CmAcct(101,101, 987654, 
				Date.valueOf("2011-01-01"),
				Date.valueOf("2016-01-01"), 16000, "TRY");
		// check the record exists
		if(CmAcct.getByBalanceID(cm.getBalanceId()) == null){
			if(CmAcct.addRecord(cm)){
				System.out.println("SUCCESS!");
			}
			else{
				System.out.println("FAILED!");
			}
		}
		else{
			System.out.println("Record exists!");
		}
	}

}
