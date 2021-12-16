package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "req_order", schema = "public", catalog = "ShowroomTest")
public class ReqOrderEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="req_order_seq")
    @SequenceGenerator(name="req_order_seq",
            sequenceName="req_order_id_seq", allocationSize=1)
    @Column(name = "req_order_id")
    private int reqOrderId;


    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customers;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "question")
    private String question;

    @Basic
    @Column(name = "processed")
    private Boolean processed;

    public CustomerEntity getCustomerId() {
        return customers;
    }

    public void setCustomerId(CustomerEntity customers) {
        this.customers = customers;
    }

    public int getReqOrderId() {
        return reqOrderId;
    }

    public void setReqOrderId(int reqOrderId) {
        this.reqOrderId = reqOrderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReqOrderEntity that = (ReqOrderEntity) o;
        return reqOrderId == that.reqOrderId && customers == that.customers && Objects.equals(phone, that.phone) && Objects.equals(question, that.question) && Objects.equals(processed, that.processed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reqOrderId, customers, phone, question, processed);
    }
}
