package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static db.jdbcUtil.*;
import vo.MemberBean;

public class MemberDAO {
	public static MemberDAO instance;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	private MemberDAO() {}
	
	public static MemberDAO getInstance() {
		if(instance==null) {
			instance=new MemberDAO();
		}
		return instance;
	}
	public void setConnection(Connection con) {
		this.con=con;
	}
	
	// 로그인
	public String selectLoginId(MemberBean member) {
		String loginId=null;
		String sql="select member_id from memberbean where member_id=? and member_pw=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, member.getMEMBER_ID());
			pstmt.setString(2, member.getMEMBER_PW());
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				loginId=rs.getString("MEMBER_ID");
			}
		}catch(Exception ex) {
			System.out.println("에러 : "+ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		return loginId;
	}
	
	// 회원가입
	public int insertMember(MemberBean member) {
		String sql="insert into memberbean values (?,?,?,?,?,?)";
		int insertCount=0;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, member.getMEMBER_ID());
			pstmt.setString(2, member.getMEMBER_PW());
			pstmt.setString(3, member.getMEMBER_NAME());
			pstmt.setInt(4, member.getMEMBER_AGE());
			pstmt.setString(5,member.getMEMBER_GENDER());
			pstmt.setString(6, member.getMEMBER_EMAIL());
			insertCount=pstmt.executeUpdate();
			
		}catch(Exception ex) {
			System.out.println("joinMember 에러 : "+ex);
		}finally {
			close(pstmt);
		}
		return insertCount;
	}
	
	// 회원 전체 목록 조회
	public ArrayList<MemberBean> selectMemberList(){
		String sql="select * from memberbean";
		ArrayList<MemberBean> memberList=new ArrayList<MemberBean>();
		MemberBean mb=null;
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					mb=new MemberBean();
					mb.setMEMBER_ID(rs.getString("MEMBER_ID"));
					mb.setMEMBER_PW(rs.getString("MEMBER_PW"));
					mb.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
					mb.setMEMBER_AGE(rs.getInt("MEMBER_AGE"));
					mb.setMEMBER_GENDER(rs.getString("MEMBER_GENDER"));
					mb.setMEMBER_EMAIL(rs.getString("MEMBER_EMAIL"));
					memberList.add(mb);
				}while(rs.next());
			}
		}catch(Exception ex) {
			System.out.println("getDeatilMember 에러 : "+ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		return memberList;
	}
	
	// 특정 회원 조회
	public MemberBean selectMember(String id) {
		String sql="select * from memberbean where member_id=?";
		MemberBean mb=null;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				mb=new MemberBean();
				mb.setMEMBER_ID(rs.getString("MEMBER_ID"));
				mb.setMEMBER_PW(rs.getString("MEMBER_PW"));
				mb.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
				mb.setMEMBER_AGE(rs.getInt("MEMBER_AGE"));
				mb.setMEMBER_GENDER(rs.getString("MEMBER_GENDER"));
				mb.setMEMBER_EMAIL(rs.getString("MEMBER_EMAIL"));
			}
			
		}catch(Exception ex) {
			System.out.println("getDetailMember 에러 : "+ex);
		}finally {
			close(rs);
			close(pstmt);
		}
		return mb;
	}
	
	// 회원탈퇴
	public int deleteMember(String id) {
		String sql="delete memberbean where member_id=?";
		int deleteCount=0;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			deleteCount=pstmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("deleteMember 에러 : "+ex);
		}finally {
			close(pstmt);
		}
		return deleteCount;
	}
}