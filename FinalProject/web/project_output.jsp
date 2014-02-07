<%--
     * @author: Team Go-Go-Gadget-Girls
--%>
<html>
<body bgcolor="CCFF99">
<%@page contentType="text/html" pageEncoding="UTF-8"%>


        <h1>Thanks for using our website!</h1>
        <p>Here is the information you requested:</p>

        <%@page import="ADRreport.*, Data.*"%>
        <%@page import="java.util.*"%>
        <%@page import="java.math.*"%>

        <%
        Demo demo = (Demo) request.getAttribute("demo");
        ADR adr1 = (ADR) request.getAttribute("adrIC");
        ADR adr2 = (ADR) request.getAttribute("adrT10");
        String drug1 = request.getParameter("drug1");
        String drug2 = request.getParameter("drug2");
        String pt = request.getParameter("pt");
        String area1 = (String) request.getParameter("area1");
        String area2 = (String) request.getParameter("area2");
        String area3 = (String) request.getParameter("area3");
        %>

        <%--Section 1 --%>
        <% if ("on".equals(area1))
            {%>
            <p>Here are the statistics of the FDA's database: </p>
            <table cellspacing="5" cellpadding="5" border="1">
                <tr>
                    <td>Total number of patients: </td>
                    <td><%= Math.round(demo.getNumPatients()) %></td>
                </tr>
                <tr>
                    <td>Average Age: </td>
                    <td><%= Math.round(demo.getAvgAge()) %></td>
                </tr>
                <tr>
                    <td>Standard deviation of age: </td>
                    <td><%= Math.round(demo.getStdevAge()) %></td>
                </tr>
                <tr>
                    <td>Percentage of male patients: </td>
                    <td><%= Math.round(demo.getPercentMale()) %></td>
                </tr>
                <tr>
                    <td>Percentage of female patients: </td>
                    <td><%= Math.round(100-demo.getPercentMale()) %></td>
                </tr>
            </table><%}%>
            <br>

        <%--Section 2 --%>
        <% if ("on".equals(area2))
            {%>
             <table>
                <% double ic = adr1.getIC();
                        if (Double.isNaN(ic)){
                           %> Could not find an association between <b><%= drug1 %></b> or <b><%= pt %></b> in our database.
                      <%} else {%>
                          The degree of association between <b><%= drug1 %></b> and
                          <b><%= pt %></b> is
                        
                       <% BigDecimal bd = new BigDecimal(ic);
                          bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                          out.print(bd);
                        }%>
            </table>
                <table width="500">
                    <tr>
                        <td>To best interpret this result please look at the reference
                    table located on the right side of this page.  Values of red indicate
                        high associations while green values indicate low associations.</td>
                        <td><img src="chart.jpg" /></td>
                    </tr>
                </table>
                    
            <%}%>

        <%--Section 3 --%>
        <% if ("on".equals(area3))
            {
               Vector top10 = adr2.getTop10();
               if(top10.size() == 0)
               {%>
               We were unable to find a match for <b><%= drug2 %></b>
               <%} else {%>

            <p>According to the FDA's database, the top <%= top10.size()/3 %>
                reactions associated with <%= drug2 %> are: </p>

           
            <table cellspacing="5" cellpadding="5" border="1">
            <tr>
                <th>Reaction</th><th>Count</th><th>Association</th>
            </tr>
                <% for (int i=0; i < top10.size(); i=i+3)
                {%>
                <tr>
                    <td align="left"><% out.print(  top10.elementAt(i)  );%></td>
                    <td align="left"><% out.print( top10.elementAt(i+1)    );%></td>
                     <td align="left"><% out.print( top10.elementAt(i+2)    );%></td>
                </tr> <%
                }%>
            </table><%}
            }
                %>

        <p>To request another set of information, click on the Back <br>
        button in your browser or the Return button shown <br>
        below.</p>

        <form action="user_input.jsp" method="post">
            <input type="submit" value="Return">
        </form>
</body>
</html>