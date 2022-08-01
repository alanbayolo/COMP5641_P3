package Task2;

import java.util.concurrent.Semaphore;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * ENUM representing each of the possible states.
	 */
	public enum States{thinks,eats,talks,hungry};
	/**
	 * Number of chopsticks available.
	 */
	int chopsticksNum;
	/**
	 * Number of philosophers.
	 */
	int piNumberOfPhilosophers;
	/**
	 * TID of the philosopher talking at the moment.
	 */
	int philosopherTalking = 0;
	/**
	 * Enum array initialization.
	 */
	States[] action;


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		//Set the number of chopsticks
		chopsticksNum = piNumberOfPhilosophers-1;
		//Assigning the number of philosophers to the local data member.
		this.piNumberOfPhilosophers = piNumberOfPhilosophers;
		//Initializing the array of states with the number of philosophers.
		action = new States[piNumberOfPhilosophers];
		//Initialize each of the philosophers state to 'thinks'
		for(int i=0;i<piNumberOfPhilosophers;i++){
			action[i] = States.thinks;
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	void test(final int piTID){
		if((action[(piTID+piNumberOfPhilosophers-1)%piNumberOfPhilosophers] != States.eats)&&
				(action[piTID]==States.hungry)&&
				(action[(piTID+1)%piNumberOfPhilosophers]!=States.eats)){
			action[piTID]=States.eats;
			notify();
		}
	}

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID) throws InterruptedException {
		action[piTID] = States.hungry;
		test(piTID);
		if(action[piTID]!=States.eats)
			wait();
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		action[piTID] = States.thinks;
		//test((i+4)%5);
		//test((i+1)%5);
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
	}
}

// EOF
