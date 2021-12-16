package web;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ReportsServlet",urlPatterns = {"/Reports","/ReportsCarStat","/ReportSimpleOrder", "/ReportSellerStat", "/ReportSimpleCar"})
public class ReportsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/ReportsCarStat":{
                String fileName = "order_cars_stat";
                String query = "SELECT new map(ca.name as name, " +
                        "sum(x.price) as price_sum, " +
                        "sum(x.quantity) as quantity_sum) " +
                        "FROM OrderEntity x JOIN x.cars ca " +
                        "GROUP BY ca.name";
                getReport(emf, fileName, query,response);
                break;
            }
            case "/ReportSimpleOrder":{
                String fileName = "orderSimpleReport";
                String query = "select new map (ca.carId as car_id, cust.customerId as customer_id, " +
                        "s.sellerId as seller_id, x.quantity as quantity ,x.dateOrd as date_ord, " +
                        "x.payment as payment, x.confirmed as confirmed, x.price as price) " +
                        "from OrderEntity x " +
                        "join x.cars ca " +
                        "join x.customers cust " +
                        "join x.sellers s " +
                        "order by date_ord desc";
                getReport(emf, fileName, query,response);
                break;
            }
            case "/ReportSellerStat":{
                String fileName = "seller_stat";
                String query = "select new map (s.name as name, " +
                        "sum(case when o.quantity is null then 0 else o.quantity end) as total_sold_cars, " +
                        "sum(case when o.price is null then 0 else o.price end) as total_price_of_sold_cars) " +
                        "from OrderEntity o left  join o.sellers s " +
                        "group by s.name";
                getReport(emf, fileName, query,response);
                break;
            }
            case "/ReportSimpleCar":{
                String fileName = "Simple_Blue";
                String query = "SELECT x FROM CarEntity x ";
                getReport(emf, fileName, query,response);
                break;
            }
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher("/OtherJSP/reports.jsp");
                dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void getReport ( EntityManagerFactory emf,String nameFile, String stringQuery,HttpServletResponse response){
        List list = getList(emf, stringQuery);
        JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
        try {
            JasperReport jasperReport = null;
            JasperDesign jasperDesign = null;
            Map parameters = new HashMap();

            String path = getServletContext().getRealPath("/");
            jasperDesign = JRXmlLoader.load(path + "/"+nameFile+".jrxml");
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, parameters, beanCollectionDataSource);
            OutputStream outStream = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setContentLength(byteStream.length);
            outStream.write(byteStream, 0, byteStream.length);
        } catch (JRException | IOException e1) {
            e1.printStackTrace();
        }
    }

    public List getList(EntityManagerFactory emf, String stringQuery){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            // Создаем объект запроса
            Query query = em.createQuery(stringQuery);
            List<?> list = query.getResultList();
            return list;
        } catch (Exception e1) {
            e1.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }
}
