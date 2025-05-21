package idusw.jw202112401.pims.repository;

import java.sql.*;

public class DAOImpl implements DAO{
    @Override
    public Connection getConnection() {
        Connection conn = null;
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_pims";
        String dbUser = "user_pims";
        String dbPw = "cometrue";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버 로딩, 메모리 업로드
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPw);
            // 적재된 드라이버 관리자 객체의 getConnection() 정적메소드를 호출하여 Connection 객체 생성
            // Connection -> statement / PreparedStatement 객체 생성 -> query 실행
            // -> ResultSet (read) or 영향받은 row 수(create, update, delete) 반환
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return conn;

    }

    @Override
    public void closeResources(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) throws SQLException {
        if(conn != null){conn.close();}
        if(stmt != null){stmt.close();}
        if(pstmt != null){pstmt.close();}
        if(rs != null){rs.close();}
    }
}
