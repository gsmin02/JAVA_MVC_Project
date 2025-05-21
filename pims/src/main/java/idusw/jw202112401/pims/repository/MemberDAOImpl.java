package idusw.jw202112401.pims.repository;

import idusw.jw202112401.pims.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl extends DAOImpl implements MemberDAO {

    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public MemberDAOImpl() {conn = getConnection();}

    @Override
    public int create(Member member) {
        int ret = 0;
        String sql = "insert into tbl_pims(email, pw, username, phone, address, admin) values(?,?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getPw());
            pstmt.setString(3, member.getUsername());
            pstmt.setString(4, member.getPhone());
            pstmt.setString(5, member.getAddress());
            pstmt.setInt(6, member.getAdmin());
            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private Member setMemberRs(ResultSet rs) throws SQLException {
        Member retmember = new Member();
        retmember.setSeq(rs.getInt("seq"));
        retmember.setEmail(rs.getString("email"));
        retmember.setPw(rs.getString("pw"));
        retmember.setUsername(rs.getString("username"));
        retmember.setPhone(rs.getString("phone"));
        retmember.setAddress(rs.getString("address"));
        retmember.setAdmin(rs.getInt("admin"));

        return retmember;
    }

    @Override
    public Member read(Member member) {
        Member retMember = null;
        // 지난주 email 조건 -> id 조건으로 조회
        String sql = "select * from tbl_pims where email=? and pw=?"; // 유일키로(unique key)로 조회
        try {
            pstmt = conn.prepareStatement(sql); // prepared Statement 객체, 미리 컴파일
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getPw());
            rs = pstmt.executeQuery();
            if(rs.next()) { // rs.next()는 반환된 객체에 속한 요소가 있는지를 반환하고, 다름 요소로 접근
                retMember = setMemberRs(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retMember;
    }

    @Override
    public List<Member> readList() {
        String sql = "select * from tbl_pims where admin=0";
        List<Member> retList = new ArrayList<Member>();
        try {
            pstmt = conn.prepareStatement(sql); // prepared Statement 객체, 미리 컴파일
            rs = pstmt.executeQuery();
            while(rs.next()) { // rs.next()는 반환된 객체에 속한 요소가 있는지를 반환하고, 다름 요소로 접근
                retList.add(setMemberRs(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public int update(Member member) {
        return 0;
    }

    @Override
    public int delete(Member member) {
        /*
        int ret = 0;
        // 지난주 email 조건 -> id 조건으로 조회
        String sql = "delete from member where email=? and pw=?"; // 유일키로(unique key)로 조회
        try {
            pstmt = conn.prepareStatement(sql); // prepared Statement 객체, 미리 컴파일
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getPw());

            ret = pstmt.executeUpdate(); // 실행 후 영향을 받은 항의 수를 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
         */
        return 0;
    }
}
