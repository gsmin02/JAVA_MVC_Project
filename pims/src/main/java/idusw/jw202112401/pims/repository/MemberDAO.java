package idusw.jw202112401.pims.repository;

import idusw.jw202112401.pims.model.Member; // 학번 바꿔서 쓰셈

import java.util.List;

public interface MemberDAO {

    int create(Member member);
    Member read(Member member);
    List<Member> readList();
    int update(Member member);
    int delete(Member member);

}
