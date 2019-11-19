package distributedsystems;
import java.util.*;
import java.io.*;
import java.lang.StringBuilder;

public class Main {
    static int ALPHA = 5; //Number of nodes in system
    static int CLOCKWISE = 1; //Clockwise 
    static int ANTICLOCKWISE = 2; //Anti-Clockwise 
    static int RANDOM = 3; //Random
    static int MESSAGES = 1; //Clockwise 
    static int ROUND = 2; //Anti-Clockwise 
    static int randNum = 0; //Anti-Clockwise 

    public static void main(String[] args) {

        Results results = new Results();
        
        for (int i = 2; i < 1000; i++) {
            if(i%10==0) System.out.println("i="+i);
            runAll(i, CLOCKWISE, results);
            record(results, i, CLOCKWISE);
        }
        System.out.println("          Clockwise Done");
//        
        for (int i = 2; i < 1000; i++) {
            if(i%10==0) System.out.println("i="+i);
            runAll(i, ANTICLOCKWISE, results);
            record(results, i, ANTICLOCKWISE);
        }
        System.out.println("          Anti-Clockwise Done");
        for (randNum = 0; randNum < 10; randNum++) {
            for (int i = 2; i < 1000; i++) {
                if(i%10==0) System.out.println("i="+i);
                runAll(i, RANDOM, results);
                record(results, i, RANDOM);
            }
            System.out.println("        Random No. "+randNum+" done");
        }
        System.out.println("          All Random done");
        
    }
    
    public static void record(Results results, int nodes, int type){
        String typeName = new String();
        String nodesString = Integer.toString(nodes);
        if(type == CLOCKWISE){
            typeName = "cw";
        } else if (type == ANTICLOCKWISE) {
            typeName = "acw";
        } else if (type == RANDOM) {
            String num = Integer.toString(randNum);
            typeName = num + "rand";
        }
        
        for (int i = 0; i < 4; i++) {
            if(i==0){
                String round = Integer.toString(results.getRoundLCR());
                String name = typeName + "RoundLCR";
                recordEach(nodesString, round, name);
            } else if(i==1){
                String round = Integer.toString(results.getRoundHS());
                String name = typeName + "RoundHS";
                recordEach(nodesString, round, name);
            } else if(i==2){
                String messages = Integer.toString(results.getMessagesLCR());
                String name = typeName + "MessagesLCR";
                recordEach(nodesString, messages, name);
            } else if(i==3){
                String messages = Integer.toString(results.getMessagesHS());
                String name = typeName + "MessagesHS";
                recordEach(nodesString, messages, name);
            }
        }
    }
        
    public static void recordEach(String nodes, String second, String name){
        try{    
            FileWriter fw = new FileWriter("C:\\Users\\ASUS\\Documents\\Comp212\\gnuplot\\"+name+".dat", true);
            fw.write(nodes+" "+second);
            fw.write(System.getProperty("line.separator"));
        fw.close();
        }catch(Exception IOException){
            System.out.println(IOException);
        }
    }
    
    public static void runAll(int nodes, int type, Results results){
        LinkedList<Integer> idList = new LinkedList<>();
        Map<Integer, Boolean> notLeader = new HashMap<>();
        createIDs(nodes, type, idList);
        LinkedList<Node> forwardGraph = new LinkedList<>();
        LinkedList<Node> backwardGraph = new LinkedList<>();
        
        createNodes(nodes, idList, forwardGraph, backwardGraph, notLeader);
        runLCR(nodes, forwardGraph, results);
//        System.out.println("    nodes = "+nodes+" round = "+roundLCR);
        runHS(nodes, forwardGraph, notLeader, results);
//        System.out.println("    nodes = "+nodes+" round = "+roundHS);
//        System.out.println("");
//        for (int i = 0; i < nodes*ALPHA; i++) {
//            System.out.println(idList.poll());
//        }
        
    }
    public static void runLCR(int nodes, LinkedList<Node> forwardGraph, Results results){
        PassLCR pass = new PassLCR();
        int messages=0;
        int round=0;
        while(!pass.isLeaderSelected()){
            for(int i=0; i<nodes; i++){
                forwardGraph.get(i).runLCR(pass);
                messages++;
            }
            round++;
            pass.isIDSent(true);
        }
//        System.out.print("The Leader's ID is " + pass.getLeader());
        results.setMessagesLCR(messages);
        results.setRoundLCR(round);

    }
    public static void runHS(int nodes, LinkedList<Node> graph, Map<Integer, Boolean> notLeader, Results results){
        int phase = 0;
        int messages = 0;
        boolean isLeaderSelected = false;
        while(!isLeaderSelected){
        for (int i = 0; i < nodes; i++) {
            int dist = (int)Math.pow(2,phase);
            int id = graph.get(i).getMyID();
            if(!notLeader.get(i)){
                PassHS passC = new PassHS(true,id,dist);
                messages = graph.get(i).runHS(passC,i,messages,graph);
                if (passC.isLeaderSelected()){
                    isLeaderSelected = true;
//                    System.out.print("The Leader's ID is "+id);
                    break;
                }
                if(passC.isNotLeader()){
                    notLeader.replace(i, true);
                }
            }
            if(!notLeader.get(i)){
                PassHS passA = new PassHS(false,id,dist);
                messages = graph.get(i).runHS(passA,i,messages,graph);
                if (passA.isLeaderSelected()){
                    isLeaderSelected = true;
//                    System.out.print("The Leader's ID is "+id);
                    int leader = id;
                    break;
                }
                if(passA.isNotLeader()){
                    notLeader.replace(i, true);
                }
            }
        }
        phase++;
        }
        results.setMessagesHS(messages);
        results.setRoundHS((int)Math.pow(2,phase));
    }
    public static void createIDs(int nodes, int type, LinkedList<Integer> idList){
        if(type == CLOCKWISE){
            for(int i=1; i<(nodes*ALPHA+1); i++){
                idList.add(i);
            }
        } else if (type == ANTICLOCKWISE){
            for(int i=(nodes*ALPHA); i>0; i--){
                idList.add(i);
            }
        } else if (type == RANDOM){
            for(int i=1; i<(nodes*ALPHA+1); i++){
                idList.add(i);
            }
            Collections.shuffle(idList);
        }
        
    }
    public static void createNodes(int nodes, LinkedList<Integer> idList, LinkedList<Node> 
            forwardGraph, LinkedList<Node> backwardGraph, Map<Integer, Boolean> notLeader){
        for(int i=0; i<nodes; i++){
            notLeader.put(i, false);
            Node object = new Node(idList.poll(), nodes);
            forwardGraph.addLast(object);
            backwardGraph.addFirst(object);
        } for(int i=0; i<nodes; i++){
            if(i==0){
                forwardGraph.get(i).setIDBefore(forwardGraph.get(nodes-1).getMyID());
                forwardGraph.get(i).setIDAfter(forwardGraph.get(i+1).getMyID());
            }else if (i == nodes-1){
                forwardGraph.get(i).setIDBefore(forwardGraph.get(i-1).getMyID());
                forwardGraph.get(i).setIDAfter(forwardGraph.get(0).getMyID());
            } else {
                forwardGraph.get(i).setIDBefore(forwardGraph.get(i-1).getMyID());
                forwardGraph.get(i).setIDAfter(forwardGraph.get(i+1).getMyID());
            }
        }
//        for(int i=0; i<NODES; i++){
//            System.out.println(forwardGraph.get(i).getIDBefore());
//            System.out.println(forwardGraph.get(i).getMyID());
//            System.out.println(forwardGraph.get(i).getIDAfter());
//            System.out.println("");
//        }
    }
}




