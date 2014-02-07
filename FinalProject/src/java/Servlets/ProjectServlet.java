package Servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Data.Demo;
import ADRreport.ADR;
/**
 * @author: Team Go-Go-Gadget-Girls
*/

public class ProjectServlet extends HttpServlet {
    /**
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // get parameters from the request

        String area1 = request.getParameter("area1");
        String area2 = request.getParameter("area2");
        String area3 = request.getParameter("area3");
        String drug1 = request.getParameter("drug1");
        String drug2 = request.getParameter("drug2");
        String pt = request.getParameter("pt");
        Boolean errorOnInput = false;
        String message1 = "";
        String message2 = "";
        String message3 = "";
        String message4 = "";

        //checking of valid input
        if("on".equals(area2)){
            if("".equals(drug1)){
                errorOnInput = true;
                message3 += "Must enter a drug name. ";
            }
            if("".equals(pt)){
                errorOnInput = true;
                message2 += " Must enter a reaction. ";
            }
        }
        if("on".equals(area3)){
            if("".equals(drug2)){
                errorOnInput = true;
                message4 += "Must enter a drug name. ";
            }
        }

        //make sure the user selected an option
        if( !"on".equals(area1)&& !"on".equals(area2) && !"on".equals(area3) ){
            errorOnInput = true;
            message1 += "Must select an option";
        }

        // only declare objects if methods are selected, also re-route
        // url depending on whether or not corresponding selection was chosen
        String url;
        if(errorOnInput)
            url = "/user_input.jsp";
        else
        {
            if("on".equals(area1)){
                Demo demo = new Demo();
                request.setAttribute("demo", demo);
            }
            if("on".equals(area2)){
                ADR ADRIC = new ADR(drug1,pt);
                request.setAttribute("adrIC", ADRIC);
            }
            if("on".equals(area3)){
                ADR ADRT10 = new ADR(drug2);
                request.setAttribute("adrT10", ADRT10);
            }
            url = "/project_output.jsp";
        }

        request.setAttribute("message1", message1);
        request.setAttribute("message2", message2);
        request.setAttribute("message3", message3);
        request.setAttribute("message4", message4);

        RequestDispatcher dispatcher =
        getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}