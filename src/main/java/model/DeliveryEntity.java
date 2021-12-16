package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "delivery", schema = "public", catalog = "ShowroomTest")
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="delivery_seq")
    @SequenceGenerator(name="delivery_seq",
            sequenceName="delivery_id_seq", allocationSize=1)
    @Column(name = "delivery_id")
    private int deliveryId;

    @Basic
    @Column(name = "address_from", length = 100)
    private String addressFrom;

    @Basic
    @Column(name = "delivery_type", length = 20)
    private String deliveryType;

    @Basic
    @Column(name = "price")
    private float price;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "showroom_id", referencedColumnName = "showroom_id")
    private ShowroomEntity showrooms;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private CarEntity cars;

    @Basic
    @Column(name = "date_del")
    @Temporal(TemporalType.DATE)
    private Calendar dateDel;

    public int getDeliveryId() {
        return deliveryId;
    }

    public ShowroomEntity getShowrooms() {
        return showrooms;
    }

    public void setShowrooms(ShowroomEntity showrooms) {
        this.showrooms = showrooms;
    }

    public CarEntity getCars() {
        return cars;
    }

    public void setCars(CarEntity cars) {
        this.cars = cars;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Calendar getDateDel() {
        return dateDel;
    }

    public void setDateDel(Calendar dateDel) {
        this.dateDel = dateDel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryEntity that = (DeliveryEntity) o;
        return deliveryId == that.deliveryId && Objects.equals(addressFrom, that.addressFrom) && Objects.equals(deliveryType, that.deliveryType) && Objects.equals(price, that.price) && Objects.equals(dateDel, that.dateDel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId, addressFrom, deliveryType, price, dateDel);
    }
}
