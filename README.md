Package Delivery System Simulation Framework

Create a java application and a background simulation framework to display tracking information for the package delivery system of FedEx (as shown in Figure 1 below).
Requirements: 
Implement a method to accept new packets (along with its attributes as shown in the figure below).
A user should also be able to query the system with a unique tracking number and get results similar to what is shown in the snapshot attached below (Figure 1).
All packet data, from the birth of a packet till it reaches its destination, should be stored in a MySQL database.
Each thread should compute shortest path for delivery of a package (using Dijkstra’s shortest path algorithm or similar) through the list of Fedex distribution centers shown below. Assume all point to point distance is 1, and every center is connected to its nearest 2 neighbors based on geographical distance).
List of Fedex Distribution Centers
Northborough, MA
Edison, NJ
Pittsburgh, PA
Allentown, PABD Strap Endlinksth
Martinsburg, WV
Charlotte, NC
Atlanta, GA
Orlando, FL
Memphis, TN
Grove City, OH
Indianapolis, IN
Detroit, MI
New Berlin, WI
Minneapolis, MN
St. Louis, MO
Kansas, KS
Dallas, TX
Houston, TX
Denver, CO
Salt Lake City, UT
Phoenix, AZ
Los Angeles, CA
Chino, CA
Sacramento, CA
Seattle, WA

The application should accept new packets with their properties (Figure 1). This information should be stored in the MySQL database.
The user should also be able to query with an input tracking number and print the tracking details of the package as shown in Figure 1. This data will be read out of the MySQL database.
Every active packet will create its own thread as it travels from source to destination in the system. In each step of the simulation, a packet moves from one center (or node in the graph) to another. The MySQL database is also updated for every packet at the end of a simulation step.
The simulation step can be generated in the main thread using the system clock (or its multiple).
Any query should spawn a new thread which will project all the information about a certain packet onto the screen or the GUI interface.
Graduate students will have to implement the GUI interface. UG students can dump output to screen.
Sample Output:

Figure 1
 
[Ref:www.fedex.com]# Fedex-tracking
