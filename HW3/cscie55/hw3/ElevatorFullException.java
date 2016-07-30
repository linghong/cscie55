package cscie55.hw3;

/**
 * ElevatorFullException,
 * ElevatorFullException can cause throwing an ElevatorFullException when:
 *1)passengers board in the first floor but exceed the elevator capacity
 *2)passengers waiting on above 2nd floor and try to board on the elevator but exceed the elevator capacity
 *@author:Linghong Chen, assignment work for CSCIE55 HW3
 */
public class ElevatorFullException extends Exception{
    public ElevatorFullException(){
        super();
    } 
  public ElevatorFullException(String msg){
      super(msg);
  }
}
