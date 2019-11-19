package distributedsystems;

public class PassHS {
    private int id;
    private int hopCount;
    private boolean CW;
    private boolean returning = false;
    private boolean isSent = false;
    private boolean leaderSelected = false;
    private boolean notLeader = false;
//    
    PassHS(boolean CW, int id, int hopCount){
        this.CW = CW;
        this.id = id;
        this.hopCount = hopCount;
    }
//
    public void isCW(boolean CW){
        this.CW = CW;
    }
    public boolean isCW(){
        return CW;
    }
    public void setHopCount(int hopCount){
        this.hopCount = hopCount;
    }
    public int getHopCount(){
        return hopCount;
    }
    public void setID(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
    public void isReturning(boolean returning){
        this.returning = returning;
    }
    public boolean isReturnig(){
        return returning;
    }      
    public void isSent(boolean isSent){
        this.isSent = isSent;
    }
    public boolean isSent(){
        return isSent;
    }
    public void isLeaderSelected(boolean leaderSelected){
        this.leaderSelected = leaderSelected;
    }
    public boolean isLeaderSelected(){
        return leaderSelected;
    }
    public void isNotLeader(boolean notLeader){
        this.notLeader = notLeader;
    }
    public boolean isNotLeader(){
        return notLeader;
    }
}