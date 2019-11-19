package distributedsystems;

public class Results {
    private int roundLCR = 1;
    private int messagesLCR = 2;
    private int roundHS = 3;
    private int messagesHS = 4;
    
    public void setRoundHS(int roundHS){
        this.roundHS=roundHS;
    }
    public int getRoundHS(){
        return roundHS;
    }
    public void setMessagesHS(int messagesHS){
        this.messagesHS=messagesHS;
    }
    public int getMessagesHS(){
        return messagesHS;
    }
    public void setRoundLCR(int roundLCR){
        this.roundLCR=roundLCR;
    }
    public int getRoundLCR(){
        return roundLCR;
    }
    public void setMessagesLCR(int messagesLCR){
        this.messagesLCR=messagesLCR;
    }
    public int getMessagesLCR(){
        return messagesLCR;
    }
}
