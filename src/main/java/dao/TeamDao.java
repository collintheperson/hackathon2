package dao;

import models.Team;

import java.util.List;

/**
 * Created by Guest on 8/18/17.
 */
public interface TeamDao {

    //create
    void add (Team team);

    //read
    List<Team> getAll();

    Team findById(int id);

    //    //update
    void update(int id, String name, String location);

    //    //delete
    void deleteById(int id);

    void clearAllTeams();


}

