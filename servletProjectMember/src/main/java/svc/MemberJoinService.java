package svc;

import static db.jdbcUtil.*;

import java.sql.Connection;

import dao.MemberDAO;
import vo.MemberBean;

public class MemberJoinService {
	public boolean joinMember(MemberBean member) {
		boolean joinSuccess=false;
		MemberDAO memberDAO=MemberDAO.getInstance();
		Connection con=getConnection();
		memberDAO.setConnection(con);
		int insertCount=memberDAO.insertMember(member);
		
		if(insertCount>0) {
			joinSuccess=true;
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return joinSuccess;
	}
}
