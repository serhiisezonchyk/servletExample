package GUI;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Calendar;
import java.util.function.Supplier;

public class Edit {
    public void editCar(EntityManagerFactory emf, int car_id,String complectation, float engine, String model, String name, float price, int quantity, int year) {
        EntityManager em = emf.createEntityManager();
        Supplier<CarEntity> edit = ()->transform(em,car_id,complectation,engine,model,name,price,quantity,year);
        editing(em, edit);
    }
    private CarEntity transform(EntityManager em, int car_id,String complectation, float engine, String model, String name, float price, int quantity, int year){
        CarEntity carEditable = em.find(CarEntity.class, car_id);
        carEditable.setComplectation(complectation);
        carEditable.setEngine(engine);
        carEditable.setModel(model);
        carEditable.setName(name);
        carEditable.setPrice(price);
        carEditable.setQuantity(quantity);
        carEditable.setYear(year);
        return carEditable;
    }

    public void editCustomer(EntityManagerFactory emf, int customer_id,String name, String phone, boolean legality) {
        EntityManager em = emf.createEntityManager();
        Supplier<CustomerEntity> edit = ()->transform(em,customer_id,name,phone,legality);
        editing(em, edit);
    }
    private CustomerEntity transform(EntityManager em,int customer_id,String name, String phone, boolean legality){
        CustomerEntity customerEditable = em.find(CustomerEntity.class, customer_id);
        customerEditable.setName(name);
        customerEditable.setLegality(legality);
        customerEditable.setPhone(phone);
        return customerEditable;
    }

    public void editDelivery(EntityManagerFactory emf,int delivery_id, String addressfrom, Calendar date, String delivery_type, float price, ShowroomEntity showroom, CarEntity car) {
        EntityManager em = emf.createEntityManager();
        Supplier<DeliveryEntity> edit = ()->transform(em,delivery_id,addressfrom,date,delivery_type,price,showroom,car);
        editing(em, edit);
    }
    private DeliveryEntity transform(EntityManager em,int delivery_id, String addressfrom, Calendar date, String delivery_type, float price, ShowroomEntity showroom, CarEntity car){
        DeliveryEntity deliveryEditable = em.find(DeliveryEntity.class, delivery_id);
        deliveryEditable.setAddressFrom(addressfrom);
        deliveryEditable.setDateDel(date);
        deliveryEditable.setDeliveryType(delivery_type);
        deliveryEditable.setPrice(price);
        deliveryEditable.setShowrooms(showroom);
        deliveryEditable.setCars(car);
        return deliveryEditable;
    }

    public void editOrder(EntityManagerFactory emf, int order_id,boolean availability, boolean confirmed, Calendar date, String payment, float price, int quantity, CarEntity car, CustomerEntity customer, SellerEntity seller) {
        EntityManager em = emf.createEntityManager();
        Supplier<OrderEntity> edit = ()->transform(em,order_id,availability,confirmed,date,payment,price,quantity,car,customer,seller);
        editing(em, edit);
    }
    private OrderEntity transform(EntityManager em, int order_id,boolean availability, boolean confirmed, Calendar date, String payment, float price, int quantity, CarEntity car, CustomerEntity customer, SellerEntity seller){
        OrderEntity orderEditable = em.find(OrderEntity.class, order_id);
        orderEditable.setAvailability(availability);
        orderEditable.setConfirmed(confirmed);
        orderEditable.setDateOrd(date);
        orderEditable.setPayment(payment);
        orderEditable.setQuantity(quantity);
        orderEditable.setPrice(price);
        orderEditable.setCars(car);
        orderEditable.setCustomers(customer);
        orderEditable.setSellers(seller);
        return orderEditable;
    }
    public void editSeller(EntityManagerFactory emf, int seller_id,String name,String phone, String position, ShowroomEntity showroom) {
        EntityManager em = emf.createEntityManager();
        Supplier<SellerEntity> edit = ()->transform(em,seller_id,name,phone,position,showroom);
        editing(em, edit);
    }
    private SellerEntity transform(EntityManager em, int seller_id,String name,String phone, String position, ShowroomEntity showroom){
        SellerEntity sellerEditable = em.find(SellerEntity.class, seller_id);
        sellerEditable.setName(name);
        sellerEditable.setPhone(phone);
        sellerEditable.setPosition(position);
        sellerEditable.setShowrooms(showroom);
        return sellerEditable;
    }
    public void editShowroom(EntityManagerFactory emf, int showroom_id,String city, String address, String phone) {
        EntityManager em = emf.createEntityManager();
        Supplier<ShowroomEntity> edit = ()->transform(em,showroom_id,city,address,phone);
        editing(em, edit);
    }
    private ShowroomEntity transform(EntityManager em, int showroom_id,String city, String address, String phone){
        ShowroomEntity showroomEditable = em.find(ShowroomEntity.class, showroom_id);
        showroomEditable.setCity(city);
        showroomEditable.setAddress(address);
        showroomEditable.setPhone(phone);
        return showroomEditable;
    }


    public void editReqOrder(EntityManagerFactory emf, int reqOrderId,CustomerEntity customer, String phone, String question, boolean processed){
        EntityManager em = emf.createEntityManager();
        Supplier<ReqOrderEntity> add = ()->transform(reqOrderId,customer,phone,question,processed);
        editing(em, add);
    }
    private ReqOrderEntity transform( int reqOrderId,CustomerEntity customer, String phone, String question, boolean processed) {
        ReqOrderEntity reqorder = new ReqOrderEntity();
        reqorder.setReqOrderId(reqOrderId);
        reqorder.setCustomerId(customer);
        reqorder.setPhone(phone);
        reqorder.setQuestion(question);
        reqorder.setProcessed(processed);
        return reqorder;
    }

    public void editReqToCallManager(EntityManagerFactory emf, int reqToCallManagerId,SellerEntity seller,String request, boolean processed){
        EntityManager em = emf.createEntityManager();
        Supplier<ReqToCallManagerEntity> add = ()->transform(reqToCallManagerId,seller, request,processed);
        editing(em, add);
    }
    private ReqToCallManagerEntity transform(int reqToCallManagerId,SellerEntity seller,String request, boolean processed) {
        ReqToCallManagerEntity reqcall = new ReqToCallManagerEntity();
        reqcall.setReqToCallManagerId(reqToCallManagerId);
        reqcall.setSellers(seller);
        reqcall.setRequest(request);
        reqcall.setProcessed(processed);
        return reqcall;
    }

    public void editRoles(EntityManagerFactory emf,int rolesId ,String role){
        EntityManager em = emf.createEntityManager();
        Supplier<RolesEntity> add = ()->transform(rolesId,role);
        editing(em, add);
    }
    private RolesEntity transform(int rolesId,String role) {
        RolesEntity roles = new RolesEntity();
        roles.setRolesId(rolesId);
        roles.setRole(role);
        return roles;
    }

    public void editUsers(EntityManagerFactory emf, int usersId,String login, String password, RolesEntity roles, String passhelp, String name){
        EntityManager em = emf.createEntityManager();
        Supplier<UsersEntity> add = ()->transform(usersId,login,password,roles,passhelp,name);
        editing(em, add);
    }
    private UsersEntity transform(int usersId,String login, String password, RolesEntity roles, String passhelp, String name) {
        UsersEntity users = new UsersEntity();
        users.setUsersId(usersId);
        users.setLogin(login);
        users.setPassword(password);
        users.setRoles(roles);
        users.setPassHelp(passhelp);
        users.setName(name);
        return users;
    }

    public void editing(EntityManager em , Supplier<?> function){
        em.getTransaction().begin();
        try{
            em.merge(function.get());
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
