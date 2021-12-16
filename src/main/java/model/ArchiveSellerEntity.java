package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "archive_seller", schema = "public", catalog = "ShowroomTest")
public class ArchiveSellerEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="archive_seller_seq")
    @SequenceGenerator(name="archive_seller_seq",
            sequenceName="archive_seller_id_seq", allocationSize=1)
    @Column(name = "aseller_id")
    private int asellerId;

    @Basic
    @Column(name = "prev_id")
    private int prevId;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "position")
    private String position;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "showroom_id", referencedColumnName = "showroom_id")
    private ShowroomEntity showroom;

    public int getAsellerId() {
        return asellerId;
    }

    public void setAsellerId(int asellerId) {
        this.asellerId = asellerId;
    }

    public int getPrevId() {
        return prevId;
    }

    public void setPrevId(int prevId) {
        this.prevId = prevId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ShowroomEntity getShowrooms() {
        return showroom;
    }
    public void setShowrooms(ShowroomEntity showroom) {
        this.showroom = showroom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArchiveSellerEntity that = (ArchiveSellerEntity) o;
        return asellerId == that.asellerId && prevId == that.prevId && Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(position, that.position) && Objects.equals(showroom, that.showroom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asellerId, prevId, name, phone, position, showroom);
    }
}
