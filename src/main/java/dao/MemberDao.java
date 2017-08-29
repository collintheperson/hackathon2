package dao;

import models.Member;

import java.util.List;


public interface MemberDao {

    void add (Member member);

    List<Member> getAll();

    List<Member> getAllMembersByTeam(int teamId);

    Member findById(int id);

    void update(int id, String memberName, int badge);

    void deleteById(int id);
    void clearAllMembers();


}
