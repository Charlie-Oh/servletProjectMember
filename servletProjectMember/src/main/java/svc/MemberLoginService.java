package svc;

import java.sql.Connection;
import static db.jdbcUtil.*;
import dao.MemberDAO;
import vo.MemberBean;

public class MemberLoginService {
	public boolean login(MemberBean member) {
		Connection con=getConnection();
		MemberDAO memberDAO=MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		boolean loginResult=false;
		String loginId=memberDAO.selectLoginId(member);
		
		if(loginId!=null) {
			loginResult=true;
		}
		close(con);
		return loginResult;
	}
}