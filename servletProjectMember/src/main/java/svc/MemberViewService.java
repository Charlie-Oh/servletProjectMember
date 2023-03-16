package svc;

import java.sql.Connection;
import static db.jdbcUtil.*;
import dao.MemberDAO;
import vo.MemberBean;

public class MemberViewService {
	public MemberBean getMember(String viewId) {
		Connection con=getConnection();
		MemberDAO memberDAO=MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		MemberBean member=memberDAO.selectMember(viewId);
		close(con);
		return member;
	}
}