# CS2030 Project: Discrete Event Simulator
Quoting from the question paper:
> A discrete event simulator is a software that simulates the changes in the state of a system across time, with each transition from one state of the system to another triggered via an event. Such a system can be used to study many complex real-world systems such as queuing to order food at a fast-food restaurant.

This is a CS2030 project completed in AY21/22 Sem 2. <br>
The question paper is in a PDF titled "Discrete Event Simulator" that can be accessed [here](https://github.com/inas0ng/discreteeventsimulator/blob/main/Discrete%20Event%20Simulator.pdf).

## How to Use
User Input
1. User input starts with five integer values representing:
   - the number of servers,
   - the number of self-check counters
   - the maximum queue length
   - the number of customers
   - the probability of a server resting
3. Enter the arrival times of the customers
4. Enter the service times for the customers

Output
1. The entire simulation run will result in lines of output in the form of <time_event_occured, customer_id, event_details>
2. Statistics of the system will then be provided in square brackets
   - average waiting time of customers who have been served
   - number of customers served
   - number of customers who left without being served

## Example Usage
After compiling the code, enter `java Main8 << 8.1_in` to simulate an example of using the simulator at the highest level, Level 8.
