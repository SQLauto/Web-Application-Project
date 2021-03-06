package servlets;

import beans.Client;
import beans.Payment;
import beans.Transaction;
import com.google.gson.Gson;
import dao.daoImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class fetch_pendingpayments extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
       try{
           HttpSession session = request.getSession(true);
           daoImpl pd= new daoImpl();
           String clientid,tid;
           clientid=session.getAttribute("User").toString();
           tid = session.getAttribute("Tid").toString();
           
           float oilpurchased;
           double price,pendingpayment=0.0;
           Transaction trans = pd.GetTransactionForId(tid);
           oilpurchased = trans.getquantity();
           price = (trans.getquantity()* trans.getrate())+ trans.getcommission();
           List<Payment> lp = pd.getTransactionPayments(tid);
           for (Payment Paym : lp) {
             pendingpayment += Paym.getamount();
            }
           pendingpayment = price - pendingpayment;
           out.println("{\"orderid\":\""+tid+"\",\"oilpurchased\":\""+oilpurchased+"\",\"price\":\""+price+"\",\"pendingpayment\":\""+pendingpayment+"\"}");
          
           
            } catch (Exception e) {
                    response.sendRedirect("design/main.jsp");
                }finally {
                    out.close();
                }

}


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
