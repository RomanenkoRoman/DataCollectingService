package controllers;

import models.entity.*;
import models.entity.roles.Admin;

import play.data.*;
import play.db.jpa.*;
import play.mvc.*;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import java.util.*;
import java.util.Collections;


/**
 * Created by Роман on 02.01.2017.
 *
 * FieldController
 */
public class FieldController extends Controller {

    private FormFactory formFactory;
    private JPAApi jpaApi;

    private Set<WebSocket.Out<String>> socketSet = new HashSet<>();

    @Inject
    public FieldController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }


    public LegacyWebSocket<String> messagesWebSocket() {

        return WebSocket.whenReady((in, out) -> {
            socketSet.add(out);

            in.onMessage((String message) -> {
                socketSet.forEach(s -> s.write(message));
            });

            in.onClose(() -> {
                socketSet.remove(out);
            });
        });
    }


    @Transactional
    @Security.Authenticated(Admin.class)
    public Result goToFieldPage() {

        List<Field> fields =
                (List<Field>) jpaApi.em()
                        .createQuery("select p from Field p")
                        .getResultList();

        Collections.sort(fields);

        return ok(views.html.fields.render(fields));
    }

    @Transactional
    @Security.Authenticated(Admin.class)
    public Result createEditForm() {

        List<String> listOfTypes = getListOfAvailableTypes();

        Form<Field> myField = formFactory.form(Field.class);

        return ok(views.html.createField.render(myField, listOfTypes));
    }

    /**
     * Get the list of all available types from Type enumeration
     *
     * @return result list
     */
    private List<String> getListOfAvailableTypes() {
        List<String> listOfTypes = new ArrayList<>();
        for (int i = 0; i < Field.TypeEnum.values().length; i++) {
            listOfTypes.add(Field.TypeEnum.values()[i].toString());
        }
        return listOfTypes;
    }

    /**
     * Create field and put it in data base
     *
     * @return redirect on fields page
     */
    @Transactional
    public Result createField() {

        Map<String, String> form = Form.form().bindFromRequest().data();

        if (form.get("Label").isEmpty() || form.get("Label") == null) {
            return badRequest("Expecting some data");
        } else {

            Field field = new Field();
            field.setTypeEnum(Field.TypeEnum.valueOfString(form.get("Type")));
            field.setLabel(form.get("Label"));
            field.setActive(Boolean.parseBoolean(form.get("Is Active")));
            field.setRequired(Boolean.parseBoolean(form.get("Required")));
            field.setOptions(form.get("Options"));

            jpaApi.em().persist(field);
            return redirect("/fields");
        }
    }

    /**
     * Render edit field form with current data
     * @return edit field page render
     */
    @Security.Authenticated(Admin.class)
    @Transactional
    public Result editFieldForm() {
        Map<String, String> fieldToEdit = Form.form().bindFromRequest().data();

        Field updateField = getFieldById(Integer.parseInt(fieldToEdit.get("edit id")));
        List<String> listOfTypes = getListOfAvailableTypes();
        Form<Field> myField = formFactory.form(Field.class);

        return ok(views.html.editField.render(myField, listOfTypes, updateField));
    }

    /**
     * Get the field from data base
     *
     * @param id of field
     * @return field from db
     */
    @Transactional(readOnly = true)
    public Field getFieldById(int id) {
        Map<String, String> fieldToEdit = Form.form().bindFromRequest().data();

        Field field = null;
        if (fieldToEdit != null) {

            TypedQuery<Field> typedQuery = jpaApi.em()
                    .createQuery("SELECT c FROM Field c WHERE c.id = " + id, Field.class);
            field = typedQuery.getSingleResult();
        }
        return field;
    }


    /**
     * Edit current field
     *
     * @return redirect on '/fields'
     */
    @Transactional
    @Security.Authenticated(Admin.class)
    public Result editField() {

        Map<String, String[]> values = request().body().asFormUrlEncoded();

        int id = Integer.parseInt(values.get("id")[0]);
        String label;
        String select;


        Field currentField = getFieldById(id);

        if (values.containsKey("label")) {
            label = values.get("label")[0];
            currentField.setLabel(label);
        }

        if (values.containsKey("required")) {
            currentField.setRequired(true);

        } else currentField.setRequired(false);

        if (values.containsKey("select")) {
            select = values.get("select")[0];
            currentField.setTypeEnum(Field.TypeEnum.valueOfString(select));
        }

        if (values.containsKey("isActive")) {
            currentField.setActive(true);
        } else currentField.setActive(false);

        if (values.containsKey("options")) {
            currentField.setOptions(values.get("options")[0]);
        }


        jpaApi.em().createQuery("UPDATE Field p SET p.label = :newLabel WHERE p.id = :oldId")
                .setParameter("oldId", currentField.getId())
                .setParameter("newLabel", currentField.getLabel())
                .executeUpdate();

        return redirect("/fields");
    }


    /**
     * Delete field
     *
     * @return response message
     */
    @Transactional
    public Result deleteField() {

        Map<String, String[]> values = request().body().asFormUrlEncoded();
        if (values != null) {
            Integer id = Integer.parseInt(values.get("id")[0]);

            jpaApi.em().createQuery("DELETE FROM Field c WHERE c.id= :id")
                    .setParameter("id", id)
                    .executeUpdate();

            return ok("Field with id = " + id + " was deleted");
        }
        return badRequest("something wrong with delete field");
    }

    /**
     * Add response to data base
     * @return redirect on congratulations page
     */
    @Transactional
    public Result addResponse() {

        Map<String, String[]> map = request().body().asFormUrlEncoded();
        Response response = new Response();
        map.entrySet()
                .stream()
                .forEach(t ->
                        {
                            Answer answer = new Answer(t.getKey(), t.getValue()[0]);
                            response.addField(answer);
                        }
                );


        jpaApi.em().persist(response);

        return redirect("/congratulations");


    }

    public Result congratulations() {
        return ok(views.html.congratulation.render());
    }


    /**
     * Get all responses from db
     * @return render responses page
     */
    @Security.Authenticated(Admin.class)
    @Transactional(readOnly = true)
    public Result getResponses() {
        List<Response> responses =
                (List<Response>) jpaApi.em().createQuery("select p from Response p").getResultList();
        List<Field> fields =
                (List<Field>) jpaApi.em().createQuery("select  p from Field p").getResultList();

        Collections.sort(fields);
        Collections.sort(responses);

        return ok(views.html.responses.render(responses, fields));
    }

    /**
     * Get the fields from db to render response collecting page
     * @return render start page
     */
    @Transactional
    public Result getFieldsToResponse() {

        List<Field> list =
                (List<Field>) jpaApi.em().createQuery("select p from Field p").getResultList();
        Form<Field> form = formFactory.form(Field.class);

        Collections.sort(list);

        return ok(views.html.responsesCollecting.render(form, list));
    }


}
