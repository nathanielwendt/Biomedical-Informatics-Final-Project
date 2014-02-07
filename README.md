Biomedical-Informatics-Final-Project
====================================

A Bayesian Neural Network

Description:
This program implements a bayesian network in order to determine associations between drugs and reactions among
many database entries.  The program uses four database tables, reac08q1, drug08q1, adr, and demo08q1 within a database 
called 'medicaldata'.  Users can perform three various queries to find out information about these tables.
The first query is to find general information about patient demographic data.  The second is to find the degree
of association between a drug and a reaction in the database.  The third option will give the top ten reactions to
a given drug and their given associations.

Database Setup:
Make sure that you have all of the appropriate data in demo08q1, reac08q1, and drug08q1.  The fourth table is unique
to our system and must be executed in MySQL workbench or another MySQL tool.  The file is included in the .zip package
and is called runcachetable.sql.  This will create and populate the table adr that allows for faster querries in the
program.


Netbeans File Structure:
FinalProject

	Web Pages
		META-INF
		WEB-INF
		project_output.jsp
		user_input.jsp
	Source Packages
		ADRreport
			ADR.java
			ADR_IO.java
		Data
			Demo.java
			DemoIO.java
		JDBCWrapper
			Database.java
			Row.java
			RowSet.java
			Table.java
		Servlets
			ProjectServlet.java

Program Setup:
Download the .zip file and unzip the contents.  Open the project in Netbeans and make sure to have Tomcat 6.0 server
installed and the file structure as displayed above. Press F6, and Netbeans will run the Tomcat server and the
instance of the program within it.


The Environment:
In the browser environment, make your selections for part 1,2, and 3.  Selecting all three options will compute in roughly
30 seconds.
