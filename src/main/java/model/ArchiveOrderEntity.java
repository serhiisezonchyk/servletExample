package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "archive_order", schema = "public", catalog = "ShowroomTest")
public class ArchiveOrderEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="archive_order_seq")
    @SequenceGenerator(name="archive_order_seq",
            sequenceName="archive_order_id_seq", allocationSize=1)
    @Column(name = "aorder_id")
    private int aorderId;

    @Basic
    @Column(name = "prev_id")
    private int prevId;

    @Basic
    @Column(name = "availability")
    private Boolean availability;

    @Basic
    @Column(name = "confirmed")
    private Boolean confirmed;

    @Basic
    @Column(name = "date_ord", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar dateOrd;

    @Basic
    @Column(name = "payment", length = 3)
    private String payment;

    @Basic
    @Column(name = "price")
    private Float price;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;

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

    public int getAorderId() {
        return aorderId;
    }

    public void setAorderId(int aorderId) {
        this.aorderId = aorderId;
    }

    public int getPrevId() {
        return prevId;
    }

    public void setPrevId(int prevId) {
        this.prevId = prevId;
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

    public Calendar getDateOrd() {
        return dateOrd;
    }

    public void setDateOrd(Calendar dateOrd) {
        this.dateOrd = dateOrd;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArchiveOrderEntity that = (ArchiveOrderEntity) o;
        return aorderId == that.aorderId && prevId == that.prevId && Objects.equals(availability, that.availability) && Objects.equals(confirmed, that.confirmed) && Objects.equals(dateOrd, that.dateOrd) && Objects.equals(payment, that.payment) && Objects.equals(price, that.price) && Objects.equals(quantity, that.quantity) && Objects.equals(cars, that.cars) && Objects.equals(customers, that.customers) && Objects.equals(sellers, that.sellers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aorderId, prevId, availability, confirmed, dateOrd, payment, price, quantity, cars, customers, sellers);
    }
}
