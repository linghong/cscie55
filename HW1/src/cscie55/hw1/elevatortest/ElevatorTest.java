package cscie55.hw1.elevatortest;

import cscie55.hw1.elevator.Elevator;

/**
 * test class for Elevator.java
 * @author -Linghong Chen , for assignment 1 
 */

public class ElevatorTest {
    public static void main(String argv[]){
        
        //create an Elevator object elevator
        Elevator elevator= new Elevator();
        
        /**
         * Boarding Passenger
         * two passenger for the 3rd floor
         * one passenger for the 5th floor
         */  
       
        elevator.boardPassenger(3);
        elevator.boardPassenger(3);
        elevator.boardPassenger(5);

        //print the state of elevator before first move
       System.out.println(elevator); 
              
      /**
       * MAX_FLOOR_NUMBER is 7, 
       * from first floor to top floor, then back to first floor, it has 12 stops
       * Each time after elevator moves one floor, it prints the elevator state 
       */
       elevator.getMaxFloorNumber();
       for (int i=0; i<2*(elevator.getMaxFloorNumber()-1); i++){
           elevator.move();
           System.out.println(elevator);           
       }
    }        
}
