package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customer", schema = "public", catalog = "ShowroomTest")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="customer_seq")
    @SequenceGenerator(name="customer_seq",
            sequenceName="customer_id_seq", allocationSize=1)
    @Column(name = "customer_id")
    private int customerId;

    @Basic
    @Column(name = "name",length = 100, nullable = false)
    private String name;

    @Basic
    @Column(name = "phone", length = 13,nullable = false)
    private String phone;

    @Basic
    @Column(name = "legality")
    private Boolean legality;

    public int getCustomerId() {
        return customerId;
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

    public Boolean getLegality() {
        return legality;
    }

    public void setLegality(Boolean legality) {
        this.legality = legality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return customerId == that.customerId && Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(legality, that.legality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, phone, legality);
    }
}
