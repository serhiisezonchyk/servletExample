package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "req_to_call_manager", schema = "public", catalog = "ShowroomTest")
public class ReqToCallManagerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="req_to_call_manager_seq")
    @SequenceGenerator(name="req_to_call_manager_seq",
            sequenceName="req_to_call_manager_id_seq", allocationSize=1)
    @Column(name = "req_to_call_manager_id")
    private int reqToCallManagerId;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id")
    private SellerEntity sellers;

    @Basic
    @Column(name = "request")
    private String request;

    @Basic
    @Column(name = "processed")
    private Boolean processed;

    public SellerEntity getSellers() {
        return sellers;
    }

    public void setSellers(SellerEntity sellers) {
        this.sellers = sellers;
    }
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }


    public int getReqToCallManagerId() {
        return reqToCallManagerId;
    }

    public void setReqToCallManagerId(int reqToCallManagerId) {
        this.reqToCallManagerId = reqToCallManagerId;
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
        ReqToCallManagerEntity that = (ReqToCallManagerEntity) o;
        return reqToCallManagerId == that.reqToCallManagerId && sellers == that.sellers && Objects.equals(request, that.request) && Objects.equals(processed, that.processed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reqToCallManagerId, sellers, request, processed);
    }
}
