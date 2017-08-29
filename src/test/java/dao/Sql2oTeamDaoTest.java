package dao;

import models.Member;
import models.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;


import java.util.List;

import static org.junit.Assert.*;


public class Sql2oTeamDaoTest {

    private Sql2oTeamDao teamDao;
    private Sql2oMemberDao memberDao;
    private Connection con;



    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        teamDao = new Sql2oTeamDao(sql2o);
        memberDao = new Sql2oMemberDao(sql2o);

        //keep connection open through entire test so it does not get erased.
        con = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }
    @Test
    public void addingTeamSetsId() throws Exception {
        Team team = setupNewTeam();
        int originalTeamId = team.getId();
        teamDao.add(team);
        assertNotEquals(originalTeamId, team.getId()); //how does this work?
    }

    @Test
    public void existingTeamsCanBeFoundByID()  throws Exception    {
        Team team = setupNewTeam();
        teamDao.add(team);
        Team foundTeam = teamDao.findById(team.getId());
        assertEquals(team, foundTeam);
    }
    @Test
    public void getAll_allTeamsAreFound () throws Exception {
        Team team = setupNewTeam();
        Team anotherTeam = new Team("dudes", "we code");
        teamDao.add(team);
        teamDao.add(anotherTeam);
        int number = teamDao.getAll().size();
        assertEquals(2, number);

    }

    @Test
    public void getAllMembersByTeam_returnsAllMembers() throws Exception {
        Member newMember = new Member("Hi",1,1);
        Member newMember2 = new Member( "none", 42, 1);
        Member newMember3 = new Member( "none", 12521, 2);
        memberDao.add(newMember);
        memberDao.add(newMember2);
        memberDao.add(newMember3);
        List teamSize = memberDao.getAllMembersByTeam(1);
        assertEquals(2, teamSize.size());
    }


    @Test
    public void updateChangesTeam() throws Exception {
        Team team = new Team ("The exiled", "Our code got us exiled from ourselves");
        teamDao.add(team);

        teamDao.update(1, "the wondercoders", "Superthemed coders that also wear capes");
        Team updatedTeam = teamDao.findById(team.getId()); //why do I need to refind this?
        assertEquals("the wondercoders", updatedTeam.getName());
    }

    @Test
    public void deleteById_deletesVeryWell () {
        Team team = setupNewTeam();
        teamDao.add(team);
        teamDao.deleteById(team.getId());
        assertEquals(0,teamDao.getAll().size());
    }

    @Test
    public void clearAllTasks() {
        Team team = setupNewTeam();
        Team anotherTeam = new Team("teeth people","cool");
        teamDao.add(team);
        teamDao.add(anotherTeam);
        teamDao.clearAllTeams();
        assertEquals(0, teamDao.getAll().size());
    }

    //Helper
    public Team setupNewTeam()  {
        return new Team("The water people", "desert");
    }
}