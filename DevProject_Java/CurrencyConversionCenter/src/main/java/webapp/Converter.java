/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp;

import apps.ConvertHelper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import apps.MailSender;
/**
 *
 * @author tahsin
 */
@WebServlet(name = "Converter", urlPatterns = {"/Converter"})
public class Converter extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.json.JSONException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        // here is temporary
//        String btnMail = (String)request.getParameter("btnSendMail");
//        if(btnMail!= null && btnMail.equals("YES")){
//            MailSender mail = new MailSender("xxx@gmail.com","xxxx@gmail.com","Here I am","new mail","xxxx");
//            mail.sendMail();
//            response.setStatus(200);
//            response.getWriter().write("SUCCESS");
//            return;
//        }
        
        // get passed parameters
        String srcCur = (String) request.getParameter("srcCurrencyName");
        String dstCur = (String) request.getParameter("dstCurrencyName");
        double srcAmt = Double.parseDouble(request.getParameter("srcAmt"));
        JSONObject jsonRates = null;
        // catch execeptions related to currency api website
        try {
            ConvertHelper hlp = new ConvertHelper();
            jsonRates = hlp.getRequest();
        } catch (Exception e) {
            System.err.println("ERROR : " + e.getMessage());
            // send internal server error
            response.sendError(500);
            return;
        }
        
        // check passed parameters
        if (srcCur.equals("") || dstCur.equals("")) {
            response.sendError(404);
            return;
        }
        // check source amount
        if (srcAmt <= 0) {
            response.sendError(400, String.format("%s is invalid!", String.valueOf(srcAmt)));
            return;
        }

        // catch JSON exceptions
        try {
            JSONObject rates = jsonRates.getJSONObject("rates");
            double srcRate = -1.0;
            double dstRate = -1.0;
            // EUR is base currency, that's why it has a rate value 1
            if (srcCur.equals("EUR")) {
                srcRate = 1.0;
            } else {
                srcRate = rates.getDouble(srcCur);
            }

            if (dstCur.equals("EUR")) {
                dstRate = 1.0;
            } else {
                dstRate = rates.getDouble(dstCur);
            }

//              hlp.DBConnect();
            double rate = dstRate / srcRate;
//            String respMsg = String.format("<p>" + srcAmt + " " + srcCur + " = " + (srcAmt * rate) + " " + dstCur + "</p>");
//            respMsg += String.format("<p> 1 {0} = {1} {2} </p>", srcCur, rate, dstCur);

            // send response as a JSON
            Map<String, String> resMap = new HashMap<>();
            resMap.put("srcAmt", String.valueOf(srcAmt));
            resMap.put("dstAmt", String.valueOf(srcAmt * rate));
            JSONObject resObj = new JSONObject(resMap);
            response.getWriter().write(resObj.toString());

        } 
        catch(JSONException e){
            // send internal error 
            response.sendError(500);
        }
        catch (IOException e) {
            // send default not found exception 
            response.sendError(404);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Performs Currency Conversion";
    }// </editor-fold>

}
