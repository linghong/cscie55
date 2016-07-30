package cscie55.hw2;

/**
 * ElevatorFullException,
 * ElevatorFullException can cause throwing an ElevatorFullException when:
 *1)passengers board in the first floor but exceed the elevator capacity
 *2)passengers waiting on above 2nd floor and try to board on the elevator but exceed the elevator capacity
 */
public class ElevatorFullException extends Exception{
    
  public ElevatorFullException(String msg){
      super(msg);
  }
}
