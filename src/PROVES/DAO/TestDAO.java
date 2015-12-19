package PROVES.DAO;

import POJO.EXCEPTIONS.ArgumentErroniException;

public class TestDAO {

	public static void main(String[] args) {
		
		try {
			TestTutors testTutors = new TestTutors();
			testTutors.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
