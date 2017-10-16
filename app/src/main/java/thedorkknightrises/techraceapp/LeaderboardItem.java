package thedorkknightrises.techraceapp;

/**
 * Created by Tejas on 28-08-2017.
 */

public class LeaderboardItem {

    public String name,location,state,applied;

    public LeaderboardItem(String name,String location,String state,String applied)
    {
        this.name=name;
        this.location=location;
        this.state=state;
        this.applied=applied;
    }
}
