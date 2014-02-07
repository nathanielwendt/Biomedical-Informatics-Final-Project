<%--
     * @author: Team Go-Go-Gadget-Girls
--%>
<html>
<body bgcolor="CCFF99">
<%@page contentType="text/html" pageEncoding="UTF-8"%>

      <%@page import="ADRreport.*"%>
        <%@page import="java.util.*"%>
        <% //get Attribute values from the request
        String message1 = (String) request.getAttribute("message1");
        String message2 = (String) request.getAttribute("message2");
        String message3 = (String) request.getAttribute("message3");
        String message4 = (String) request.getAttribute("message4");
        String drug1 = (String)request.getParameter("drug1");
        String drug2 = (String)request.getParameter("drug2");
        String pt = (String)request.getParameter("pt");

        // handle null values
        if (drug1 == null) drug1 = "";
        if (drug2 == null) drug2 = "";
        if (pt == null) pt = "";
        if (message1 == null) message1 = "";
        if (message2 == null) message2 = "";
        if (message3 == null) message3 = "";
        if (message4 == null) message4 = "";
        %>

    <h1>Welcome!</h1>
    <p>Welcome to our website, please check the checkbox for each of the
        areas you would like information for and enter the appropriate
        information.<br>
    Then, click on the Submit button.</p>

    <font color="red"><i><%= message1 %></i></font>

    <form action="ProjectServlet" method="post">

    <br>
    <input type="checkbox" name="area1"><b> 1. Show me the statistics
        available for the current FDA's database.</b><br>
    <br>

    <input type="checkbox" name="area2"><b> 2. Check the degree of association
        between this drug and this adverse drug reaction (ADR): </b><br>
        <br>
        <table>
	<tr>
		<td>Drug Name: </td>
		<td><input type="text" name="drug1" value="<%= drug1 %>"></td>
                <td>ADR: </td>
		<td><input type="text" name="pt" value="<%= pt %>"</td>
	</tr>
        </table>
         <i><font color="red"><%= message3 %></i></font><font color="red"><i><%= message2 %></i></font></p>
        <br>

    <input type="checkbox" name="area3"><b> 3. List the top 10 ADRs with the highest
        degree of association with this drug: </b><br>

        <br>
        Drug Name: <input type="text" name="drug2" value="<%= drug2 %>">
        <br>
        <font color="red"><i><%= message4 %></i></font></p>
        <br>

     <input type="submit" value="Submit">
    </form>
</body>
</html>