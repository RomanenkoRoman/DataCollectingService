package models.entity.roles;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.persistence.*;

/**
 * Created by Роман on 27.11.2016.
 *
 * Admin
 */
@Entity
public class Admin extends Security.Authenticator{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int adminId;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public Admin() {
    }

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("login");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {

        //TODO Текущая реализация данного метода под вопросом

        return redirect("/login");
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        return login != null ? login.equals(admin.login)
                : admin.login == null && (password != null ? password.equals(admin.password)
                : admin.password == null);

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
