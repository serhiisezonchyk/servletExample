package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles", schema = "public", catalog = "ShowroomTest")
public class RolesEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="roles_seq")
    @SequenceGenerator(name="roles_seq",
            sequenceName="roles_id_seq", allocationSize=1)
    @Column(name = "roles_id")
    private int rolesId;

    @Basic
    @Column(name = "role")
    private String role;

    public int getRolesId() {
        return rolesId;
    }

    public void setRolesId(int rolesId) {
        this.rolesId = rolesId;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesEntity that = (RolesEntity) o;
        return rolesId == that.rolesId && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolesId, role);
    }
}
