package dao;

import models.Member;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 8/18/17.
 */
public class Sql2oMemberDao implements MemberDao {

    private final Sql2o sql2o;

    public Sql2oMemberDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void add(Member member) {
        String sql = "INSERT INTO members (memberName, badge, teamId) VALUES (:memberName,:badge, :teamId)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("memberName", member.getMemberName())
                    .addParameter("badge", member.getBadgeNumber())
                    .addParameter("teamId", member.getTeamId())
                    .addColumnMapping("NAME", "memberName")
                    .addColumnMapping("BADGE", "badge")
                    .addColumnMapping("TEAMID","teamId")
                    .executeUpdate()
                    .getKey();
            member.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }
    public List<Member> getAll()    {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM members")
                    .executeAndFetch(Member.class);
        }
    }
    public Member findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM members WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Member.class);
        }
    }
    public void update(int id, String newMemberName, int newBadge){
        String sql = "UPDATE members SET (memberName, badge) = (:memberName, :badge) WHERE id=:id"; //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("memberName", newMemberName)
                    .addParameter("badge", newBadge)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    public void deleteById(int id) {
        String sql = "DELETE from members WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    public void clearAllMembers() {
        String sql = "DELETE from members";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Member> getAllMembersByTeam(int teamId) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM members WHERE teamId = :teamId")
                    .addParameter("teamId", teamId)
                    .executeAndFetch(Member.class);
        }

    }

}
