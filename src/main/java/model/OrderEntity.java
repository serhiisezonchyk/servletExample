package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "order", schema = "public", catalog = "ShowroomTest")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="order_seq")
    @SequenceGenerator(name="order_seq",
            sequenceName="order_id_seq", allocationSize=1)
    @Column(name = "order_id")
    private int orderId;

    @Basic
    @Column(name = "date_ord", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar dateOrd;

    @Basic
    @Column(name = "quantity")
    private int quantity;

    @Basic
    @Column(name = "price")
    private float price;

    @Basic
    @Column(name = "payment", length = 3)
    private String payment;

    @Basic
    @Column(name = "availability")
    private Boolean availability;

    @Basic
    @Column(name = "confirmed")
    private Boolean confirmed;


    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private CarEntity cars;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id")
    private SellerEntity sellers;


    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customers;

    public int getOrderId() {
        return orderId;
    }

    public CarEntity getCars() {
        return cars;
    }

    public void setCars(CarEntity cars) {
        this.cars = cars;
    }

    public SellerEntity getSellers() {
        return sellers;
    }

    public void setSellers(SellerEntity sellers) {
        this.sellers = sellers;
    }

    public CustomerEntity getCustomers() {
        return customers;
    }

    public void setCustomers(CustomerEntity customers) {
        this.customers = customers;
    }

    public Calendar getDateOrd() {
        return dateOrd;
    }

    public void setDateOrd(Calendar dateOrd) {
        this.dateOrd = dateOrd;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return orderId == that.orderId && quantity == that.quantity && Float.compare(that.price, price) == 0 && Objects.equals(dateOrd, that.dateOrd) && Objects.equals(payment, that.payment) && Objects.equals(availability, that.availability) && Objects.equals(confirmed, that.confirmed) && Objects.equals(cars, that.cars) && Objects.equals(sellers, that.sellers) && Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, dateOrd, quantity, price, payment, availability, confirmed, cars, sellers, customers);
    }
}
