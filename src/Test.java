import DAO.UserDAO;
import models.User;

import java.sql.*;

public class Test {

	public static void main(String[] args){
		User u = UserDAO.findUserById(8);
		String name = "Alex";
		UserDAO.editName(u, name);
		System.out.println();
	}

}
