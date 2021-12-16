package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "ShowroomTest")
public class UsersEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="users_seq")
    @SequenceGenerator(name="users_seq",
            sequenceName="users_id_seq", allocationSize=1)
    @Column(name = "users_id")
    private int usersId;

    @Basic
    @Column(name = "login")
    private String login;

    @Basic
    @Column(name = "password")
    private String password;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "roles_id", referencedColumnName = "roles_id")
    private RolesEntity roles;

    @Basic
    @Column(name = "pass_help")
    private String passHelp;

    @Basic
    @Column(name = "name")
    private String name;

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolesEntity getRoles() {
        return roles;
    }

    public void setRoles(RolesEntity roles) {
        this.roles = roles;
    }

    public String getPassHelp() {
        return passHelp;
    }

    public void setPassHelp(String passHelp) {
        this.passHelp = passHelp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return usersId == that.usersId && roles == that.roles && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(passHelp, that.passHelp) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersId, login, password, roles, passHelp, name);
    }
}
