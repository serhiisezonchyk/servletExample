package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "seller", schema = "public", catalog = "ShowroomTest")
public class SellerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="seller_seq")
    @SequenceGenerator(name="seller_seq",
            sequenceName="seller_id_seq", allocationSize=1)
    @Column(name = "seller_id")
    private int sellerId;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "showroom_id", referencedColumnName = "showroom_id")
    private ShowroomEntity showroom;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Basic
    @Column(name = "position", length = 30)
    private String position;

    public ShowroomEntity getShowrooms() {
        return showroom;
    }
    public void setShowrooms(ShowroomEntity showroom) {
        this.showroom = showroom;
    }

    @Basic
    @Column(name = "phone", length = 13)
    private String phone;

    public int getSellerId() {
        return sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerEntity that = (SellerEntity) o;
        return sellerId == that.sellerId && Objects.equals(name, that.name) && Objects.equals(position, that.position) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, name, position, phone);
    }
}
