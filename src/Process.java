import java.util.List;

public class Process implements Comparable<Process> {
    
    private int A; //arrival time of the process
    private int B; //CPU burst time
    private int C; //total CPU time needed
    private int IO; //A process contains computation alternating with I/O
    
    private int startTime = 0;
    private int endTime = 0;
    private String processState = "unstarted";
    
    private int IOBurstRemaining;
    private int CPUBurstRemaining;
    private int CPUTimeRemaining;
    private int blockednum;
    private int readynum;
    private int C2;
    public boolean accounted_for_io_util = false;
    private int priority;
    private boolean flag = false;
    
    public boolean getFlag(){
    	return flag;
    }
    
    public void setFlag(boolean value){
    	this.flag = value;
    }
    
    public void setPriority (int newInt){
        this.priority = newInt;
    }
    
    public int getPriority(){
        return priority;
    }
    
    
    public boolean getAccounted_for_io_util(){
        return accounted_for_io_util;
    }
    
    public void switchAccounted_for_io_util(boolean value){
        this.accounted_for_io_util = value;
    }
    
    
    
    public int getC2(){
        return C2;
    }
    
    public void setC2(int newInt){
        this.C2 = newInt;
    }
    
    private Process processRunning = null;
    
    
    public int getEndTime(){
        return endTime;
    }
    
    public void setEndTime(int newInt){
        this.endTime = newInt;
    }
    
    public int decrementCPU(){
        //    	this.CPUTimeRemaining--;
        return this.CPUTimeRemaining--;
    }
    
    
    public void setBlockedNum(int newInt){
        this.blockednum = newInt;
    }
    
    public int getBlockedNum(){
        return blockednum;
    }
    
    public void setReadyNum(int newInt){
        this.readynum = newInt;
    }
    
    public int getReadyNum(){
        return readynum;
    }
    
    
    public void setProcessRunning(Process p){
        this.processRunning = p;
    }
    
    public Process getProcessRunning(){
        return processRunning;
    }
    
    
    public void setProcessState (String processState){
        this.processState = processState;
    }
    
    public void setCPUTimeRemaining(int newInt){
        this.CPUTimeRemaining = newInt;
    }
    
    public int getCPUTimeRemaining(){
        return CPUTimeRemaining;
    }
    
    public void setIOBurstRemaining(int newInt){
        this.IOBurstRemaining = newInt;
    }
    
    public int getIOBurstRemaining(){
        return IOBurstRemaining;
    }
    
    public void setCPUBurstRemaining(int newInt){
        this.CPUBurstRemaining = newInt;
    }
    
    public int getCPUBurstRemaining(){
        return CPUBurstRemaining;
    }
    
    
    public void setA(int newInt){
        this.A = newInt;
    }
    
    public void setB(int newInt){
        this.B = newInt;
    }
    
    public void setC(int newInt){
        this.C = newInt;
    }
    
    public void setIO(int newInt){
        this.IO = newInt;
    }
    
    public int getA(){
        return this.A;
    }
    
    public int getB(){
        return this.B;
    }
    
    public int getIO(){
        return this.IO;
    }
    
    public int getC(){
        return this.C;
    }
    
    public String getProcessState(){
        return this.processState;
    }
    
    public Process(int A, int B, int C, int IO){
        setA(A);
        setB(B);
        setC(C);
        setCPUTimeRemaining(C);
        setIO(IO);
        setC2(C);
    }
    
    @Override
    public int compareTo(Process o) {
        if(o.getA() < this.getA()){
            return -1;
        }
        
        else if(o.getA() > this.getA()){
            return 1;
        }
        else{
            return 0;
        }
    }
    
    
    
    
}
