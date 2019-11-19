package distributedsystems;

import java.util.LinkedList;

public class Node{
    private final int myID; //ID of this node
    private int idBefore; //ID of next node in Clockwise direction
    private int idAfter; //ID of next node in Anticlockwise direction
    private int leaderID; // Elected leader
    private int inID; //ID recieved last round from neighbour
    private int sendID; //ID sent next round to neighbour
    private int nodes; //number of nodes
    private String status = "uknown"; //Status of current node, leader or unkown.
    private boolean notLeader = false; //number of nodes
     
    Node(int myID, int nodes){
        this.myID = myID;
        this.nodes = nodes;
    }
    public void setIDBefore(int idBefore){
        this.idBefore = idBefore;
    }    
    public void setIDAfter(int idAfter){
        this.idAfter = idAfter;
    }
    public int getIDBefore(){
        return idBefore;
    }
    public int getMyID(){
        return myID;
    }
    public int getIDAfter(){
        return idAfter;
    }
    public void isNotLeader(boolean notLeader){
        this.notLeader = notLeader;
    }
    public boolean isNotLeader(){
        return notLeader;
    }
    
   
    public void runLCR(PassLCR pass){
        inID = pass.getID();
        pass.setID(sendID);

        if(!pass.isIDSent()){
            sendID = myID;
            status = "unknown";
        } else if(pass.isLeaderSelected()) {
            leaderID = pass.getLeader();
            sendID = leaderID;
        } else {
            if(inID > myID){
                sendID = inID;
                status = "not leader";
            } else if (inID < myID){
                sendID = myID;
                status = "possible leader";
            } else if (inID == myID){
                sendID = myID;
                pass.setLeader(myID);
                pass.isLeaderSelected(true);
                status = "leader";
            }
        }

    }
    public int runHS(PassHS pass, int i, int round, LinkedList<Node> graph){
        i = incrementI(i,pass);
        while(!pass.isSent()){
            round = graph.get(i).loopHS(pass, round);
            i = incrementI(i,pass);
        }
        return round;
    } 
    public int loopHS (PassHS pass, int round) {
        round++;
//        System.out.print("id="+myID+" ");
        if(pass.getID() > myID){
            if(pass.getHopCount()>1){
                pass.setHopCount(pass.getHopCount()-1);
            } else if (pass.getHopCount()==1){
                pass.setHopCount(pass.getHopCount()-1);
                pass.isCW(!pass.isCW());
                pass.isReturning(true);
            }
        } else if(pass.getID() == myID){
            if(!pass.isReturnig()){
                pass.isLeaderSelected(true);
                pass.isSent(true);
            }else{
                pass.isSent(true);
            }
        } else if(pass.getID() < myID){
//            System.out.println("here");
            pass.isSent(true);
            pass.isNotLeader(true);
        }
        return round;
    }
    public int incrementI(int i, PassHS pass){
        if (pass.isCW() == true){
            i++;
        } else if (pass.isCW() == false){
            i--;
        }
        if (i==-1) {
            i=nodes-1;
        }else if(i==nodes){
            i=0;
        }
        return i;
    }
}   
    

//
////        
////        while(!pass.isIDSent()){ // not back at start
//            System.out.println("while i="+i);
////            i = graph.get(i).loopHS(pass, i);
////            if (i==-1) {
////                i = nodes-1;
////            }
////        }
//
//
//
//
//
//
//
//
//
//
//
//
//
////        
////System.out.println("getID= " + pass.getID()+" myID=" + myID);
//System.out.println("1 i=" + i + " myID=" + myID + " getID= " + pass.getID());
//////        if (pass.getID() > myID){
//System.out.println("2 i=" + i + " myID=" + myID);
//////            if(pass.getHopCount() > 1){//A
//System.out.println("3 i=" + i + " myID=" + myID);
//////                pass.setHopCount(pass.getHopCount()-1);
//////            } else if (pass.getHopCount() == 1){//B
//System.out.println("4 i=" + i + " myID=" + myID);
//////                pass.isOut(false);
//////                pass.isCW(!pass.isCW());
//////                pass.setHopCount(pass.getHopCount()-1);
//////                status = "unkown";
//////            }
//////        }else if (pass.getID() == myID){
////            System.out.println("id");
//////            pass.isIDSent(true);
//////            if(pass.isOut()) {
////                System.out.println("leader");
//////                status = "leader";
//////                pass.isLeaderSelected(true);
//////            }
//////        } else if (pass.getID() < myID){
//System.out.println("7 i=" + i + " myID=" + myID);
//////            status = "not leader";
//////            notLeader = true;
//////            pass.isIDSent(true);
//////        } 
//////        
//////        if (pass.isCW() == true){
//System.out.println("8 i=" + i + " myID=" + myID);
//////            i++;
//System.out.println("8.2 i=" + i + " myID=" + myID);
//////        } else if (pass.isCW() == false){
//System.out.println("9 i=" + i + " myID=" + myID);
//////            i--;
//System.out.println("9.2 i=" + i + " myID=" + myID);
//////        }
//////        return i;
//cf