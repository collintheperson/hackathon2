package dao;

import models.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class Sql2oMemberDaoTest {


    private Sql2oMemberDao memberDao;
    private Connection con;
    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        memberDao = new Sql2oMemberDao(sql2o); //ignore me for now
        memberDao = new Sql2oMemberDao(sql2o);

        //keep connection open through entire test so it does not get erased.
        con = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        con.close();

    }
    @Test
    public void addingMemberSetsId() throws Exception {
        System.out.println();
        Member member = setupNewMember();
        int originalMemberId = member.getId();
        memberDao.add(member);
        assertNotEquals(originalMemberId, member.getId()); //how does this work?
    }

    @Test
    public void getAll_allMembersAreFound () throws Exception {
        Member member = setupNewMember();
        Member anotherMember = new Member ("dudes", 142, 2);
        memberDao.add(member);
        memberDao.add(anotherMember);
        assertEquals(2,memberDao.getAll().size());
    }
    @Test
    public void getAll_MembersByTeam   ()  throws Exception    {
        Member member = setupNewMember();
        Member member2 = new Member ("Boyle",2,1);
        memberDao.add(member);
        memberDao.add(member2);
        assertEquals(2, memberDao.getAll().size());


    }
    @Test
    public void existingMembersCanBeFoundByID()  throws Exception    {
        Member member = setupNewMember();
        memberDao.add(member);
        Member foundMember = memberDao.findById(member.getId());
        assertEquals(member, foundMember);
    }

    @Test
    public void updateChangesMember() throws Exception {
        Member member = new Member("The exiled", 31,1);
        memberDao.add(member);

        memberDao.update(1, "the wondercoders", 21);
        Member updatedMember = memberDao.findById(member.getId()); //why do I need to refind this?
        assertEquals(21, updatedMember.getBadgeNumber());
    }

    @Test
    public void deleteById_deletesVeryWell () {
        Member member = setupNewMember();
        memberDao.add(member);
        memberDao.deleteById(member.getId());
        assertEquals(0,memberDao.getAll().size());
    }

    @Test
    public void clearAllMembers() {
        Member member = setupNewMember();
        Member anotherMember = new Member("teeth people",1,2);
        memberDao.add(member);
        memberDao.add(anotherMember);
        memberDao.clearAllMembers();
        assertEquals(0, memberDao.getAll().size());
    }


    public Member setupNewMember()  {
        return new Member("Frank Ocean", 112,1);
    }

}