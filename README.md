# Distributed Object System / Master Project

It is a question of filling a database of statistics of the daily evolution of the realization of the tasks indicated, of each Assignee, in the Jira Software project.


### SCHEMA OF DEVICE COMMUNICATION

![](images/SOR-Page-2.jpg)

And the data must transit via different system, the objective to bring out the notion of the distributed object system.
- Retrieve information from Atlassian JIRA using Jira Rest Client API.
- RMI Server is responsible for retrieving its information and making it available for the client to retrieve.
- The data is sent back to Spark Web API, by the RMI client, to be saved in the MySQL database.
- The CORBA server fetches the database information from Spark and makes it available to the client. The CORBA client to retrieve its information displays them as long as it is available.