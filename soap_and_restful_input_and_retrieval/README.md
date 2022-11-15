Instructions for testing the enterprise programming projects.

Import the EnterpriseProgramming.zip file into eclipse using existing projects into workspace.

---------------------------------------------------------------------------------------------

Local Web application

Run entProgAssignment on tomcat.

The service can be accessed at http://localhost:8080/entProgAssignment/

---------------------------------------------------------------------------------------------

Cloud Web application

The code can be inspected in the entProgAssCloud project.

The service can be accessed at https://augmented-voice-263815.appspot.com/

---------------------------------------------------------------------------------------------

SOAP

Set tomcat timeouts start to 450 seconds

Expand EPassignmentSOAP -> Java Resources -> src -> core

Right click the FilmDAO.java file and select New -> Other -> Web service -> Web service -> Next

Set the client type to Test client

Check the monitor web service and overwrite files without warning boxes

Click Finish and the service will load

-----------------------------------------------------------------------------------------------

RESTful

Run com.epassignment.restful on tomcat

Expand com.epassignment.restful.client -> Java Resources -> src -> com.epassignment.restful.client

Run Tester.java as java application
