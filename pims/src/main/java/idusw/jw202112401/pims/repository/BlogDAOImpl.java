package idusw.jw202112401.pims.repository;

import idusw.jw202112401.pims.model.Blog;
import idusw.jw202112401.pims.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogDAOImpl  extends DAOImpl implements BlogDAO {

    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public BlogDAOImpl() {conn = getConnection();}

    @Override
    public int create(Blog blog) {
        int ret = 0;
        String sql = "insert into blogs(username, email, title, content) values(?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, blog.getUsername());
            pstmt.setString(2, blog.getEmail());
            pstmt.setString(3, blog.getTitle());
            pstmt.setString(4, blog.getContent());
            ret = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private Blog setBlogRs(ResultSet rs) throws SQLException {
        Blog retblog = new Blog();
        retblog.setSeq(rs.getInt("seq"));
        retblog.setUsername(rs.getString("username"));
        retblog.setEmail(rs.getString("email"));
        retblog.setTitle(rs.getString("title"));
        retblog.setContent(rs.getString("content"));

        return retblog;
    }

    @Override
    public Blog read(Blog blog) {
        Blog retblog = null;

        String sql = "select * from blog where seq = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, blog.getSeq());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                retblog = setBlogRs(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retblog;
    }

    @Override
    public List<Blog> readList() {
        String sql = "select * from blogs";
        List<Blog> retList = new ArrayList<Blog>();
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                retList.add(setBlogRs(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public int update(Blog blog) {
        return 0;
    }

    @Override
    public int delete(Blog blog) {
        return 0;
    }
}
