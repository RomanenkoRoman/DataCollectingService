package controllers;

import models.entity.roles.Admin;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.loginPage;

import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * AuthenticationController
 */
public class AuthenticationController extends Controller {


    private FormFactory formFactory;
    private JPAApi jpaApi;

    @Inject
    public AuthenticationController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }


    public Result login() {
        return ok(loginPage.render(formFactory.form(Admin.class)));
    }

    public Result logout() {
        session().clear();
        return redirect(routes.AuthenticationController.login());
    }

    @Transactional(readOnly = true)
    public Result authenticate() {
        Form<Admin> adminForm = formFactory.form(Admin.class).bindFromRequest();


        Admin admin = adminForm.get();

        try {
            admin = (Admin) jpaApi.em().createQuery("select p from Admin p WHERE p.password = :password AND p.login = :login")
                    .setParameter("password", admin.getPassword())
                    .setParameter("login", admin.getLogin()).getSingleResult();
        } catch (NoResultException e) {

            return badRequest("You put wrong login or password");
        }


        if (adminForm.hasErrors()) {
            return redirect(routes.AuthenticationController.login());
        } else {

            session().clear();
            session("login", adminForm.get().getLogin());
            return redirect(routes.FieldController.getResponses());
        }
    }
}
