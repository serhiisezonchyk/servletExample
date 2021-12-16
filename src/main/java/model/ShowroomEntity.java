package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "showroom", schema = "public", catalog = "ShowroomTest")
public class ShowroomEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="showroom_seq")
    @SequenceGenerator(name="showroom_seq",
            sequenceName="showroom_id_seq", allocationSize=1)
    @Column(name = "showroom_id")
    private int showroomId;

    @Basic
    @Column(name = "city", length = 40)
    private String city;

    @Basic
    @Column(name = "phone", length = 13)
    private String phone;

    @Basic
    @Column(name = "address", length = 60)
    private String address;

    public int getShowroomId() {
        return showroomId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowroomEntity that = (ShowroomEntity) o;
        return showroomId == that.showroomId && Objects.equals(city, that.city) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showroomId, city, phone, address);
    }

}
