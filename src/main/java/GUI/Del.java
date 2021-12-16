package GUI;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Supplier;

public class Del {
    public void delCustomer(EntityManagerFactory emf, CustomerEntity customer) {
        EntityManager em = emf.createEntityManager();
        Supplier<CustomerEntity> del = ()->transform(em,customer);
        deleting(em, del);
    }
    private CustomerEntity transform(EntityManager em, CustomerEntity customer){
        CustomerEntity customerDeleted = em.find(CustomerEntity.class, customer.getCustomerId());
        return customerDeleted;
    }

    public void delCar(EntityManagerFactory emf, CarEntity car) {
        EntityManager em = emf.createEntityManager();
        Supplier<CarEntity> del = ()->transform(em,car);
        deleting(em, del);
    }
    private CarEntity transform(EntityManager em, CarEntity car){
        CarEntity carDeleted = em.find(CarEntity.class, car.getCarId());
        return carDeleted;
    }

    public void delSeller(EntityManagerFactory emf, SellerEntity seller) {
        EntityManager em = emf.createEntityManager();
        Supplier<SellerEntity> del = ()->transform(em,seller);
        deleting(em, del);
    }
    private SellerEntity transform(EntityManager em, SellerEntity seller){
        SellerEntity sellerDeleted = em.find(SellerEntity.class, seller.getSellerId());
        return sellerDeleted;
    }

    public void delDelivery(EntityManagerFactory emf, DeliveryEntity delivery) {
        EntityManager em = emf.createEntityManager();
        Supplier<DeliveryEntity> del = ()->transform(em,delivery);
        deleting(em, del);
    }
    private DeliveryEntity transform(EntityManager em, DeliveryEntity delivery){
        DeliveryEntity deliveryDeleted = em.find(DeliveryEntity.class, delivery.getDeliveryId());
        return deliveryDeleted;
    }

    public void delOrder(EntityManagerFactory emf, OrderEntity order) {
        EntityManager em = emf.createEntityManager();
        Supplier<OrderEntity> del = ()->transform(em,order);
        deleting(em, del);
    }
    private OrderEntity transform(EntityManager em, OrderEntity order){
        OrderEntity orderDeleted = em.find(OrderEntity.class, order.getOrderId());
        return  orderDeleted;
    }

    public void delShowroom(EntityManagerFactory emf, ShowroomEntity showroom){
        EntityManager em = emf.createEntityManager();
        Supplier<ShowroomEntity> del = ()->transform(em,showroom);
        deleting(em, del);
}
    private ShowroomEntity transform(EntityManager em,ShowroomEntity showroom){
        ShowroomEntity showroomDeleted = em.find(ShowroomEntity.class, showroom.getShowroomId());
        return  showroomDeleted;
    }



    public void delReqOrder(EntityManagerFactory emf, ReqOrderEntity reqOrder){
        EntityManager em = emf.createEntityManager();
        Supplier<ReqOrderEntity> del = ()->transform(em,reqOrder);
        deleting(em,del);
    }
    private ReqOrderEntity transform( EntityManager em, ReqOrderEntity reqOrder) {
        ReqOrderEntity reqorder = em.find(ReqOrderEntity.class, reqOrder.getReqOrderId());
        return reqorder;
    }

    public void delReqToCallManager(EntityManagerFactory emf, ReqToCallManagerEntity reqToCallManager){
        EntityManager em = emf.createEntityManager();
        Supplier<ReqToCallManagerEntity> del = ()->transform(em,reqToCallManager);
        deleting(em,del);
    }
    private ReqToCallManagerEntity transform(EntityManager em, ReqToCallManagerEntity reqToCallManager) {
        ReqToCallManagerEntity reqcall = em.find(ReqToCallManagerEntity.class, reqToCallManager);
        return reqcall;
    }

    public void delRoles(EntityManagerFactory emf, RolesEntity roles){
        EntityManager em = emf.createEntityManager();
        Supplier<RolesEntity> del = ()->transform(em,roles);
        deleting(em,del);
    }
    private RolesEntity transform(EntityManager em, RolesEntity roles) {
        RolesEntity role = em.find(RolesEntity.class, roles.getRolesId());
        return role;
    }

    public void delUsers(EntityManagerFactory emf, UsersEntity users){
        EntityManager em = emf.createEntityManager();
        Supplier<UsersEntity> del = ()->transform(em,users);
        deleting(em,del);
    }
    private UsersEntity transform(EntityManager em, UsersEntity users) {
        UsersEntity user = em.find(UsersEntity.class, users.getUsersId());
        return user;
    }



    public void delArchiveSeller(EntityManagerFactory emf, ArchiveSellerEntity aseller) {
        EntityManager em = emf.createEntityManager();
        Supplier<ArchiveSellerEntity> del = ()->transform(em,aseller);
        deleting(em, del);
    }
    private ArchiveSellerEntity transform(EntityManager em, ArchiveSellerEntity aseller){
        ArchiveSellerEntity asellerDeleted = em.find(ArchiveSellerEntity.class, aseller.getAsellerId());
        return asellerDeleted;
    }

    public void delArchiveOrder(EntityManagerFactory emf, ArchiveOrderEntity aorder) {
        EntityManager em = emf.createEntityManager();
        Supplier<ArchiveOrderEntity> del = ()->transform(em,aorder);
        deleting(em, del);
    }
    private ArchiveOrderEntity transform(EntityManager em, ArchiveOrderEntity aorder){
        ArchiveOrderEntity aorderDeleted = em.find(ArchiveOrderEntity.class, aorder.getAorderId());
        return aorderDeleted;
    }

    public void deleting(EntityManager em , Supplier<?> function){
        em.getTransaction().begin();
        em.remove(function.get());
        em.getTransaction().commit();
        em.close();
    }

}
