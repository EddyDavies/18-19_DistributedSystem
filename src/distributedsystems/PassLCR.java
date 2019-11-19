package distributedsystems;

public class PassLCR {
    private int id;
    private boolean isIDSent = false;
    private int leader;
    private boolean leaderSelected = false;
    
    public void setID(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
    public void isIDSent(boolean isIDSent){
        this.isIDSent = isIDSent;
    }
    public boolean isIDSent(){
        return isIDSent;
    }
    
    public void setLeader(int leader){
        this.leader = leader;
    }
    public int getLeader(){
        return leader;
    }
    public void isLeaderSelected(boolean leaderSelected){
        this.leaderSelected = leaderSelected;
    }
    public boolean isLeaderSelected(){
        return leaderSelected;
    }    
}
