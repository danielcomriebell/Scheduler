import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;


public class Schedule extends Process{
    
    public Schedule(int A, int B, int C, int IO) {
        super(A, B, C, IO);
        // TODO Auto-generated constructor stub
    }
    
    
    static boolean verbose = true;
    static int cycle = 0;
    public static int numProcesses = 0;
    static java.util.List<Process> jobList = new ArrayList<Process>(); //list to hold all the jobs
    static Scanner randomnumb = newScanner("random-numbers");
    static Stack<Process> lifo = new Stack();
    
    
    //FCFS algorithm
    public static void FCFS(java.util.List<Process> jobList2){
        ArrayList<Process> q = new ArrayList<Process>(); //Queue to keep track of Ready Processes
        int avgturnaround = 0;
        int avgreadytime = 0;
        int Jobblocked = 0;
        int Jobrunning = 0;
        int Jobready = 0 ;
        int numterminate = 0;
        Process processRunning = null;
        int newvar3 = 0;
        int newvar4 = 0;
        
        while (numterminate < jobList2.size()){
            boolean accountedfor = false;
            
            if (verbose){
                System.out.print("Before Cycle    " + cycle + ":" );
                for(Process p: jobList2){
                    
                    if (p.getProcessState() == "unstarted"){
                        System.out.print("   " + p.getProcessState() +  "  " + 0 + "   ");
                    }
                    else if (p.getProcessState() == "terminated"){
                        System.out.print("   " + p.getProcessState() +  "  " + 0 + "   ");
                    }
                    else if (p.getProcessState() == "ready"){
                        System.out.print("   " + p.getProcessState() +  "  " + 0 + "   ");
                        int var = p.getReadyNum();
                        var++;
                        p.setReadyNum(var);
                    }
                    else if (p.getProcessState() == "blocked"){
                        System.out.print("   " + p.getProcessState() +  "  " + p.getIOBurstRemaining() + "   ");
                    }
                    else if (p.getProcessState() == "running"){
                        System.out.print("   " + p.getProcessState() +  "  " + p.getCPUBurstRemaining() + "   ");
                        Jobrunning++;
                    }
                }
                System.out.println();
            }
            //if not verbose
            else{
                
                for(Process p: jobList2){
                    
                    if (p.getProcessState() == "running"){
                        Jobrunning++;
                    }
                    
                    else if (p.getProcessState() == "ready"){
                        int var = p.getReadyNum();
                        var++;
                        p.setReadyNum(var);
                        Jobready++;
                    }
                    
                }
                
            }
            
            
            //do blocked
            
            
            for (Process p: jobList2){
                if (p.getProcessState() == "blocked"){
                    
                    int var = p.getBlockedNum();
                    var++;
                    p.setBlockedNum(var);
                    
                    if (accountedfor == false){
                        Jobblocked++;
                        accountedfor = true;
                    }
                    
                    int newvar = p.getIOBurstRemaining();
                    newvar--;
                    p.setIOBurstRemaining(newvar);
                    
                    if (p.getIOBurstRemaining() == 0){
                        p.setProcessState("ready");
                        q.add(p);
                    }
                    
                }
            }
            
            
            //do running
            for (Process p: jobList2){
                
                if (p.getProcessState() == "running" && processRunning != null){
                    int newvar2 = p.getCPUTimeRemaining();
                    newvar2--;
                    p.setCPUTimeRemaining(newvar2);
                    
                    
                    
                    
                    int newvar = p.getCPUBurstRemaining();
                    newvar--;
                    p.setCPUBurstRemaining(newvar);
                    
                    
                    if (p.getCPUBurstRemaining() == 0 && p.getCPUTimeRemaining() > 0){
                        p.setProcessState("blocked");
                        processRunning = null;
                        p.setIOBurstRemaining(RandomOS(p.getIO()));
                        
                    }
                    if (p.getCPUTimeRemaining() == 0){
                        p.setProcessState("terminated");
                        numterminate++;
                        processRunning = null;
                        p.setEndTime(cycle);
                    }
                    
                    
                    
                }
            }
            
            //do arriving
            for(Process p: jobList2){
                
                
                if (p.getA() == cycle){
                    p.setProcessState("ready");
                    q.add(p);
                }
                
            }
            
            
            //do_ready
            for(Process p: jobList2){
                if (p.getProcessState() == "ready" && processRunning == null){
                    p = q.get(0);
                    p.setProcessState("running");
                    processRunning = p;
                    p.setCPUBurstRemaining(RandomOS(p.getB()));
                    q.remove(0);
                }
                
                
            }
            
            
            
            cycle++;
            
            
        }
        System.out.println(" ");
        System.out.println("The scheduling algorithm used was First Come First Served");
        System.out.println(" ");
        
        for(Process p: jobList2){
            
            System.out.println("Process Count: " + numProcesses);
            System.out.println("     " + "(A,B,C,IO) = " + "(" + p.getA() + " " +  p.getB() + " " + p.getC2() + " " +  p.getIO() + ")");
            System.out.println("     " + "Finishing Time = " +  (p.getEndTime()));
            System.out.println("     " + "Turnaround Time = " + ((p.getEndTime()) - p.getA()));
            System.out.println("     " + "I/O Time = " + p.getBlockedNum());
            System.out.println("     " + "Waiting Time = " + p.getReadyNum());
            
            System.out.println( " ");
            numProcesses++;
            avgturnaround = avgturnaround + (p.getEndTime() - p.getA());
            avgreadytime = avgreadytime + (p.getReadyNum());
            
        }
        
        System.out.println("Summary Data: ");
        System.out.println("     " + "Finishing Time = " + (cycle-1));
        System.out.println("     " + "CPU Utilization = " + CPUUtilization(Jobrunning, cycle));
        System.out.println("     " + "I/O Utilization = " + IOUtliziation(Jobblocked, cycle));
        System.out.println("     " + "Throughput = " + Throughput(numProcesses, cycle) + " processes per hundred cycles");
        System.out.println("     " + "Average turnaround time = " + TurnAroundTime(avgturnaround, numProcesses) );
        System.out.println("     " + "Average waiting time = " + WaitingTime(avgreadytime, numProcesses));
        
        
    }
    
    
    public static void RR(java.util.List<Process> jobList2){
        
        ArrayList<Process> q = new ArrayList<Process>(); //Queue to keep track of Ready Processes
        java.util.List<Process> readyq = new ArrayList<Process>(); //Queue to keep track of Ready Processes
        java.util.List<Process> tiebreaker = new ArrayList<Process>();
        int avgturnaround = 0;
        int avgreadytime = 0;
        int Jobblocked = 0;
        int Jobrunning = 0;
        int Jobready = 0 ;
        int numterminate = 0;
        Process processRunning = null;
        int newvar3 = 0;
        int newvar4 = 0;
        int readyCounter = 0;
        int quantum = 2;
        int temp = 2;
        
        while (numterminate < jobList2.size()){
//        	int quantum = 2;
            boolean accountedfor = false;
//            int quantum = 2; //setting the quantum
            
            if (verbose = true){
                System.out.print("Before Cycle    " + cycle + ":" );
                for(Process p: jobList2){
                    
                    if (p.getProcessState() == "unstarted"){
                        System.out.print("   " + p.getProcessState() +  "  " + 0 + "   ");
                    }
                    else if (p.getProcessState() == "terminated"){
                        System.out.print("   " + p.getProcessState() +  "  " + 0 + "   ");
                    }
                    else if (p.getProcessState() == "ready"){
                        System.out.print("   " + p.getProcessState() +  "  " + 0 + "   ");
                        int var = p.getReadyNum();
                        var++;
                        p.setReadyNum(var);
                    }
                    else if (p.getProcessState() == "blocked"){
                        System.out.print("   " + p.getProcessState() +  "  " + p.getIOBurstRemaining() + "   ");
                    }
                    else if (p.getProcessState() == "running"){
                        System.out.print("   " + p.getProcessState() +  "  " + p.getCPUBurstRemaining() + "   ");
                        Jobrunning++;
                    }
                }
                System.out.println();
            }
            //if not verbose
            else{
                
                for(Process p: jobList2){
                    
                    if (p.getProcessState() == "running"){
                        Jobrunning++;
                    }
                    
                    else if (p.getProcessState() == "ready"){
                        int var = p.getReadyNum();
                        var++;
                        p.setReadyNum(var);
                        Jobready++;
                    }
                    
                }
                
            }
            
            //do blocked
            
            for (Process p: jobList2){
                if (p.getProcessState() == "blocked"){
                    
                    int var = p.getBlockedNum();
                    var++;
                    p.setBlockedNum(var);
                    
                    if (accountedfor == false){
                        Jobblocked++;
                        accountedfor = true;
                    }
                    
                    int newvar = p.getIOBurstRemaining();
                    newvar--;
                    p.setIOBurstRemaining(newvar);
                    
                    if (p.getIOBurstRemaining() == 0){
                    	readyq.add(p);
                        p.setProcessState("ready");
                        readyCounter++;
                    }
                    
                }
            }
            
            
            
            //do running
            for (Process p: jobList2){
                
                
                if (p.getProcessState() == "running" && processRunning != null){
                    
                    quantum--; //decrement the quantum
                    
                    int newvar2 = p.getCPUTimeRemaining();
                    newvar2--;
                    p.setCPUTimeRemaining(newvar2);
                    
                    
                    int newvar = p.getCPUBurstRemaining();
                    newvar--;
                    p.setCPUBurstRemaining(newvar);
                    
                    
                                      
                    if (p.getCPUBurstRemaining() == 0 && p.getCPUTimeRemaining() > 0){
                        p.setProcessState("blocked");
                        processRunning = null;
                        p.setIOBurstRemaining(RandomOS(p.getIO()));
                        
                    }
                    if (p.getCPUTimeRemaining() == 0){
                        p.setProcessState("terminated");
                        numterminate++;
                        processRunning = null;
                        p.setEndTime(cycle);
                    }
                                      
                }
            }
            
            if (readyq.size() >= 1){
                insertionSort(readyq);
                for (Process p: readyq){
              	  q.add(p);
                }
                readyq.clear();
            }
            
            
            
            
            //do arriving
            for(Process p: jobList2){
                
                
                if (p.getA() == cycle && p.getProcessState() == "unstarted"){
                    p.setProcessState("ready");
                    readyCounter++;
                    q.add(p);
                }
                
            }
            
            
            //do_ready
            for(Process p: jobList2){
                if (p.getProcessState() == "ready" && processRunning == null){
                	if (q.size() > 0){
                		 p = q.get(0);
                		 p.setProcessState("running");
                		 processRunning = p;
                         if (p.getCPUBurstRemaining() == 0 && p.getFlag() == false){
                         	p.setCPUBurstRemaining(RandomOS(p.getB()));
                         }
                         q.remove(0);
                	}
                }
                
                
            }
            
            
            
            
            
            
            cycle++;
            
            
        }
        System.out.println(" ");
        System.out.println("The scheduling algorithm used was Round Robin");
        System.out.println(" ");
        
        for(Process p: jobList2){
            
            System.out.println("Process Count: " + numProcesses);
            System.out.println("     " + "(A,B,C,IO) = " + "(" + p.getA() + " " +  p.getB() + " " + p.getC2() + " " +  p.getIO() + ")");
            System.out.println("     " + "Finishing Time = " +  (p.getEndTime()));
            System.out.println("     " + "Turnaround Time = " + ((p.getEndTime()) - p.getA()));
            System.out.println("     " + "I/O Time = " + p.getBlockedNum());
            System.out.println("     " + "Waiting Time = " + p.getReadyNum());
            
            System.out.println( " ");
            numProcesses++;
            avgturnaround = avgturnaround + (p.getEndTime() - p.getA());
            avgreadytime = avgreadytime + (p.getReadyNum());
            
        }
        
        System.out.println("Summary Data: ");
        System.out.println("     " + "Finishing Time = " + (cycle-1));
        System.out.println("     " + "CPU Utilization = " + CPUUtilization(Jobrunning, cycle));
        System.out.println("     " + "I/O Utilization = " + IOUtliziation(Jobblocked, cycle));
        System.out.println("     " + "Throughput = " + Throughput(numProcesses, cycle) + " processes per hundred cycles");
        System.out.println("     " + "Average turnaround time = " + TurnAroundTime(avgturnaround, numProcesses) );
        System.out.println("     " + "Average waiting time = " + WaitingTime(avgreadytime, numProcesses));
        
        
    }

    
    public static void LCFS(java.util.List<Process> jobList2){
        
        ArrayList<Process> q = new ArrayList<Process>(); //Queue to keep track of Ready Processes
        java.util.List<Process> readyq = new ArrayList<Process>(); //Queue to keep track of Ready Processes
        java.util.List<Process> tiebreaker = new ArrayList<Process>();
        int avgturnaround = 0;
        int avgreadytime = 0;
        int Jobblocked = 0;
        int Jobrunning = 0;
        int Jobready = 0 ;
        int numterminate = 0;
        Process processRunning = null;
        int newvar3 = 0;
        int newvar4 = 0;
        int readyCounter = 0;
        int quantum = 2;
        int temp = 2;
        
        while (numterminate < jobList2.size()){
        	
        	
            boolean accountedfor = false;
//            int quantum = 2; //setting the quantum
            
            if (verbose = true){
                System.out.print("Before Cycle    " + cycle + ":" );
                for(Process p: jobList2){
                    
                    if (p.getProcessState() == "unstarted"){
                        System.out.print("   " + p.getProcessState() +  "  " + 0 + "   ");
                    }
                    else if (p.getProcessState() == "terminated"){
                        System.out.print("   " + p.getProcessState() +  "  " + 0 + "   ");
                    }
                    else if (p.getProcessState() == "ready"){
                        System.out.print("   " + p.getProcessState() +  "  " + 0 + "   ");
                        int var = p.getReadyNum();
                        var++;
                        p.setReadyNum(var);
                    }
                    else if (p.getProcessState() == "blocked"){
                        System.out.print("   " + p.getProcessState() +  "  " + p.getIOBurstRemaining() + "   ");
                    }
                    else if (p.getProcessState() == "running"){
                        System.out.print("   " + p.getProcessState() +  "  " + p.getCPUBurstRemaining() + "   ");
                        Jobrunning++;
                    }
                }
                System.out.println();
            }
            //if not verbose
            else{
                
                for(Process p: jobList2){
                    
                    if (p.getProcessState() == "running"){
                        Jobrunning++;
                    }
                    
                    else if (p.getProcessState() == "ready"){
                        int var = p.getReadyNum();
                        var++;
                        p.setReadyNum(var);
                        Jobready++;
                    }
                    
                }
                
            }
           
            
            //do blocked
            
            for (Process p: jobList2){
                if (p.getProcessState() == "blocked"){
                    
                    int var = p.getBlockedNum();
                    var++;
                    p.setBlockedNum(var);
                    
                    if (accountedfor == false){
                        Jobblocked++;
                        accountedfor = true;
                    }
                    
                    int newvar = p.getIOBurstRemaining();
                    newvar--;
                    p.setIOBurstRemaining(newvar);
                    
                    if (p.getIOBurstRemaining() == 0){
                        readyq.add(p);
                        p.setProcessState("ready");
                        readyCounter++;
                    }
                    
                }
            }
            
            if (readyq.size() >= 1){
                insertionSort(readyq);
                for (Process p: readyq){
              	  q.add(p);
                }
                readyq.clear();
            }
           
            
            
            //do running
            for (Process p: jobList2){
                
                
                if (p.getProcessState() == "running" && processRunning != null){
                   
                    
                    
                    int newvar2 = p.getCPUTimeRemaining();
                    newvar2--;
                    p.setCPUTimeRemaining(newvar2);
                    
                    
                    int newvar = p.getCPUBurstRemaining();
                    newvar--;
                    p.setCPUBurstRemaining(newvar);
                    
                    
                    
                    if (p.getCPUBurstRemaining() == 0 && p.getCPUTimeRemaining() > 0){
                        p.setProcessState("blocked");
                        processRunning = null;
                        p.setIOBurstRemaining(RandomOS(p.getIO()));
                        
                    }
                    if (p.getCPUTimeRemaining() == 0){
                        p.setProcessState("terminated");
                        numterminate++;
                        processRunning = null;
                        p.setEndTime(cycle);
                    }
                    
                    
                }
            }
            
            
            
            
            //do arriving
            for(Process p: jobList2){
                
                
                if (p.getA() == cycle && p.getProcessState() == "unstarted"){
                    p.setProcessState("ready");
                    q.add(p);
                }
                
            }
            
            
            //do_ready
            for(Process p: jobList2){
                if (p.getProcessState() == "ready" && processRunning == null){
                	if (q.size() > 0){
                		 p = q.get(q.size()-1);
                		 p.setProcessState("running");
                		 processRunning = p;
                         if (p.getCPUBurstRemaining() == 0){
                         	p.setCPUBurstRemaining(RandomOS(p.getB()));
                         }
                         q.remove(q.size()-1);
                	}
                }
                
                
            }
            
            
            
            
            
            
            cycle++;
            
            
        }
        System.out.println(" ");
        System.out.println("The scheduling algorithm used was Last Come First Serve");
        System.out.println(" ");
        
        for(Process p: jobList2){
            
            System.out.println("Process Count: " + numProcesses);
            System.out.println("     " + "(A,B,C,IO) = " + "(" + p.getA() + " " +  p.getB() + " " + p.getC2() + " " +  p.getIO() + ")");
            System.out.println("     " + "Finishing Time = " +  (p.getEndTime()));
            System.out.println("     " + "Turnaround Time = " + ((p.getEndTime()) - p.getA()));
            System.out.println("     " + "I/O Time = " + p.getBlockedNum());
            System.out.println("     " + "Waiting Time = " + p.getReadyNum());
            
            System.out.println( " ");
            numProcesses++;
            avgturnaround = avgturnaround + (p.getEndTime() - p.getA());
            avgreadytime = avgreadytime + (p.getReadyNum());
            
        }
        
        System.out.println("Summary Data: ");
        System.out.println("     " + "Finishing Time = " + (cycle-1));
        System.out.println("     " + "CPU Utilization = " + CPUUtilization(Jobrunning, cycle));
        System.out.println("     " + "I/O Utilization = " + IOUtliziation(Jobblocked, cycle));
        System.out.println("     " + "Throughput = " + Throughput(numProcesses, cycle) + " processes per hundred cycles");
        System.out.println("     " + "Average turnaround time = " + TurnAroundTime(avgturnaround, numProcesses) );
        System.out.println("     " + "Average waiting time = " + WaitingTime(avgreadytime, numProcesses));
        
        
    }
    
    //function RandomOS(U)
    
    //reads a negative non-negative integer X from a file named random-numbers
    //returns the value 1 + (X mod U)
    public static int RandomOS(int U){
        int result = randomnumb.nextInt();
        return 1 + (result % U);
    }
    
    public static String Throughput(int U, int V){
        double calculation =  ((double)(U)/(V-1) * 100);
        DecimalFormat decimalFormat = new DecimalFormat("0.000000");
        
        return decimalFormat.format(calculation);
        
    }
    
    public static String IOUtliziation(int U, int V){
        double calculation =  ((double)(U)/(V-1));
        DecimalFormat decimalFormat = new DecimalFormat("0.000000");
        
        return decimalFormat.format(calculation);
        
    }
    
    public static String CPUUtilization(int U, int V){
        double calculation =  ((double)(U)/(V-1));
        DecimalFormat decimalFormat = new DecimalFormat("0.000000");
        return decimalFormat.format(calculation);
    }
    
    public static String TurnAroundTime(int U, int V){
        double calculation =  ((double)(U)/(V));
        DecimalFormat decimalFormat = new DecimalFormat("0.000000");
        return decimalFormat.format(calculation);
    }
    
    public static String WaitingTime(int U, int V){
        double calculation =  ((double)(U)/(V));
        DecimalFormat decimalFormat = new DecimalFormat("0.000000");
        return decimalFormat.format(calculation);
    }
    
    
    //driver statement
    public static void main(String[]args){
        int counter = 0;
        int count = -1; //keeps track of count ignoring the first int
        ArrayList<Integer> tempList = new ArrayList<Integer>(); //arraylist to hold a temp list of A, B, C, IO
        java.util.List<Integer> subList = new ArrayList<Integer>(); //splits arraylist into intervals of 4 for each process
        
        Scanner sc = newScanner(args[0]);
        
        
        //sets the boolean flag for verbose
        if (args.length > 1){
            if (args[0] == ("--verbose")){
                verbose = true;
                sc = newScanner(args[1]);
            }
        }
        else{
            verbose = false;
        }
        
        
        //reads the file into an arrayList
        while(sc.hasNextInt()){
            count++;
            tempList.add(sc.nextInt());
        }
        
        //splits the data up into objects
        //adds the objects to the Processlist
        for(int start = 1; start < tempList.size(); start += 4){
            int end = Math.min(start + 4, tempList.size());
            subList = tempList.subList(start, end);
            
            if (!(subList.isEmpty())){
                Process p = new Process(subList.get(0), subList.get(1), subList.get(2), subList.get(3));
                jobList.add(p);
                p.setPriority(counter);
                counter++;
            }
            
            else{
                break;
            }
        }
        
        
        
        System.out.println("Before Sort");
        System.out.println(jobList.toString());
        
        
        jobList = insertionSort(jobList);
        
        System.out.println("After Sort");
        System.out.println(jobList.toString());
        
        
        System.out.println("");
        
        //FCFS algo
        //                 FCFS(jobList);
        RR(jobList);
//                LCFS(jobList);
        sc.close();
        
        
    }
    
    
    
    
    
    
    
    public static java.util.List<Process> insertionSort(java.util.List<Process> jobList2){
        Process temp;
        
        for(int i = 1; i < jobList2.size(); i++){
            for (int j = i; j > 0; j--){
                if (jobList2.get(j).getA() < jobList2.get(j-1).getA()){
                    temp = jobList2.get(j);
                    jobList2.set(j, jobList2.get(j-1));
                    jobList2.set(j-1, temp);
                    
                }
                
                else if (jobList2.get(j).getA() == jobList2.get(j-1).getA()){
                    if (jobList2.get(j).getPriority() < jobList2.get(j-1).getPriority()){
                        temp = jobList2.get(j);
                        jobList2.set(j, jobList2.get(j-1));
                        jobList2.set(j-1, temp);
                    }
                }
            }
        }
        return jobList2;
    }
    
    
    //Read Text
    public static Scanner newScanner(String fileName){
        try{
            Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
            return sc;
        }
        catch(Exception e){
            System.out.printf("Error reading %s\n", fileName);
            System.exit(0);
        }
        return null;
    }
    
    
}