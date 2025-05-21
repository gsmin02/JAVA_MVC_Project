package idusw.jw202112401.pims.repository;

import idusw.jw202112401.pims.model.Blog;
import idusw.jw202112401.pims.model.Member;

import java.util.List;

public interface BlogDAO {
    int create(Blog blog);
    Blog read(Blog blog);
    List<Blog> readList();
    int update(Blog blog);
    int delete(Blog blog);
}
