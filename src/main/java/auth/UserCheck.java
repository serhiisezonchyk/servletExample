package auth;

import GUI.EntityList;
import model.CustomerEntity;
import model.SellerEntity;
import model.UsersEntity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserCheck {
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    public String getRoleByLoginPassword(final String login, final String password) {
        String result = "unknown";
        UsersEntity[] usersEntities;
        EntityList el = new EntityList();
        usersEntities = el.readEntities(emf, "UsersEntity").toArray(new UsersEntity[0]);
        for (UsersEntity user : usersEntities) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = user.getRoles().getRole();
            }
        }
        return result;
    }

    public UsersEntity getUserByLoginPassword(final String login, final String password) {
        UsersEntity result = null;
        UsersEntity[] usersEntities;
        EntityList el = new EntityList();
        usersEntities = el.readEntities(emf, "UsersEntity").toArray(new UsersEntity[0]);
        for (UsersEntity user : usersEntities) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = user;
            }
        }
        return result;
    }

    public boolean userIsExist(final String login, final String password) {
        boolean result = false;
        UsersEntity[] usersEntities;
        EntityList el = new EntityList();
        usersEntities = el.readEntities(emf, "UsersEntity").toArray(new UsersEntity[0]);
        for (UsersEntity user : usersEntities) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean usernameIsExist(final String login) {
        boolean result = false;
        UsersEntity[] usersEntities;
        EntityList el = new EntityList();
        usersEntities = el.readEntities(emf, "UsersEntity").toArray(new UsersEntity[0]);
        for (UsersEntity user : usersEntities) {
            if (user.getLogin().equals(login)) {
                result = true;
                break;
            }
        }
        return result;
    }
    public CustomerEntity getCustomerByLogin(final String login) {
        CustomerEntity result = null;
        CustomerEntity[] customerEntities;
        EntityList el = new EntityList();
        customerEntities = el.readEntities(emf,"CustomerEntity").toArray(new CustomerEntity[0]);
        for (CustomerEntity customer : customerEntities) {
            if (customer.getPhone().equals(login)) {
                result = customer;
            }
        }
        return result;
    }

    public SellerEntity getSellerByLogin(final String login) {
        SellerEntity result = null;
        SellerEntity[] sellerEntities;
        EntityList el = new EntityList();
        sellerEntities = el.readEntities(emf,"SellerEntity").toArray(new SellerEntity[0]);
        for (SellerEntity seller : sellerEntities) {
            if (seller.getPhone().equals(login)) {
                result = seller;
            }
        }
        return result;
    }
}
