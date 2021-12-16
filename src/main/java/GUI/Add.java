package GUI;

import model.*;
import org.hibernate.criterion.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Calendar;
import java.util.function.Supplier;

public class Add {
    public void saveCustomer(EntityManagerFactory emf,String name, String phone, boolean legality) {
        EntityManager em = emf.createEntityManager();
        Supplier<CustomerEntity> add = ()->transform(name,phone,legality);
        adding(em, add);
    }
    private CustomerEntity transform(String name, String phone, boolean legality){
        CustomerEntity customer = new CustomerEntity();
        customer.setName(name);
        customer.setLegality(legality);
        customer.setPhone(phone);
        return customer;
    }
    public void saveShowroom(EntityManagerFactory emf,String city, String address, String phone) {
        EntityManager em = emf.createEntityManager();
        Supplier<ShowroomEntity> add = ()->transform(city,address,phone);
        adding(em, add);
    }
    private  ShowroomEntity transform(String city, String address, String phone){
        ShowroomEntity showroom = new ShowroomEntity();
        showroom.setCity(city);
        showroom.setAddress(address);
        showroom.setPhone(phone);
        return  showroom;
    }
    public void saveCar(EntityManagerFactory emf,String complectation, float engine, String model, String name, float price, int quantity, int year) {
        EntityManager em = emf.createEntityManager();
        Supplier<CarEntity> add = ()->transform(complectation,engine,model,name,price,quantity,year);
        adding(em, add);
    }
    private CarEntity transform(String complectation, float engine, String model, String name, float price, int quantity, int year){
        CarEntity car = new CarEntity();
        car.setComplectation(complectation);
        car.setEngine(engine);
        car.setModel(model);
        car.setName(name);
        car.setPrice(price);
        car.setQuantity(quantity);
        car.setYear(year);
        return car;
    }
    public void saveSeller(EntityManagerFactory emf,String name,String phone, String position, ShowroomEntity showroom) {
        EntityManager em = emf.createEntityManager();
        Supplier<SellerEntity> add = ()->transform(name,phone,position,showroom);
        adding(em, add);
    }

    private SellerEntity transform(String name,String phone, String position, ShowroomEntity showroom){
        SellerEntity seller = new SellerEntity();
        seller.setName(name);
        seller.setPhone(phone);
        seller.setPosition(position);
        seller.setShowrooms(showroom);
        return seller;
    }

    public void saveDelivery(EntityManagerFactory emf, String addressfrom, Calendar date, String delivery_type, float price, ShowroomEntity showroom, CarEntity car) {
        EntityManager em = emf.createEntityManager();
        Supplier<DeliveryEntity> add = ()->transform(addressfrom,date,delivery_type,price,showroom,car);
        adding(em, add);
    }
    private DeliveryEntity transform(String addressfrom, Calendar date, String delivery_type, float price, ShowroomEntity showroom, CarEntity car){
        DeliveryEntity delivery = new DeliveryEntity();
        delivery.setAddressFrom(addressfrom);
        delivery.setDateDel(date);
        delivery.setDeliveryType(delivery_type);
        delivery.setPrice(price);
        delivery.setShowrooms(showroom);
        delivery.setCars(car);
        return delivery;
    }

    public void saveOrder(EntityManagerFactory emf,boolean availability, boolean confirmed, Calendar date, String payment, int quantity, CarEntity car, CustomerEntity customer, SellerEntity seller) {
        EntityManager em = emf.createEntityManager();
        Supplier<OrderEntity> add = ()->transform(availability,confirmed,date,payment,quantity,car,customer,seller);
        adding(em, add);
    }

    private OrderEntity transform(boolean availability, boolean confirmed, Calendar date, String payment, int quantity, CarEntity car, CustomerEntity customer, SellerEntity seller) {
            OrderEntity order = new OrderEntity();
            order.setAvailability(availability);
            order.setConfirmed(confirmed);
            order.setDateOrd(date);
            order.setPayment(payment);
            order.setQuantity(quantity);
            order.setCars(car);
            order.setCustomers(customer);
            order.setSellers(seller);
            return order;
    }

    public void saveReqOrder(EntityManagerFactory emf, CustomerEntity customer, String phone, String question, boolean processed){
        EntityManager em = emf.createEntityManager();
        Supplier<ReqOrderEntity> add = ()->transform(customer,phone,question,processed);
        adding(em, add);
    }
    private ReqOrderEntity transform( CustomerEntity customer, String phone, String question, boolean processed) {
        ReqOrderEntity reqorder = new ReqOrderEntity();
        reqorder.setCustomerId(customer);
        reqorder.setPhone(phone);
        reqorder.setQuestion(question);
        reqorder.setProcessed(processed);
        return reqorder;
    }

    public void saveReqToCallManager(EntityManagerFactory emf, SellerEntity seller,String request, boolean processed){
        EntityManager em = emf.createEntityManager();
        Supplier<ReqToCallManagerEntity> add = ()->transform(seller, request,processed);
        adding(em, add);
    }
    private ReqToCallManagerEntity transform(SellerEntity seller,String request, boolean processed) {
        ReqToCallManagerEntity reqcall = new ReqToCallManagerEntity();
        reqcall.setSellers(seller);
        reqcall.setRequest(request);
        reqcall.setProcessed(processed);
        return reqcall;
    }

    public void saveRoles(EntityManagerFactory emf, String role){
        EntityManager em = emf.createEntityManager();
        Supplier<RolesEntity> add = ()->transform(role);
        adding(em, add);
    }
    private RolesEntity transform(String role) {
        RolesEntity roles = new RolesEntity();
        roles.setRole(role);
        return roles;
    }

    public void saveUsers(EntityManagerFactory emf, String login, String password, RolesEntity roles, String passhelp, String name){
        EntityManager em = emf.createEntityManager();
        Supplier<UsersEntity> add = ()->transform(login,password,roles,passhelp,name);
        adding(em, add);
    }
    private UsersEntity transform(String login, String password, RolesEntity roles, String passhelp, String name) {
        UsersEntity users = new UsersEntity();
        users.setLogin(login);
        users.setPassword(password);
        users.setRoles(roles);
        users.setPassHelp(passhelp);
        users.setName(name);

        return users;
    }

    public void adding(EntityManager em , Supplier<?> function){
        em.getTransaction().begin();
        try{
            em.persist(function.get());
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
