package models.entity;


import javax.persistence.*;
import java.util.*;

/**
 * Created by Роман on 08.01.2017.
 *
 * Field class contains the information about created field
 * and selected type of it
 */
@Entity
public class Field implements Comparable<Field> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "required")
    private boolean required;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "label")
    private String label;
    @Column(name = "typeEnum")
    private TypeEnum typeEnum;

    @Column(name = "options")
    private String options;

    /**
     * Types of field
     */
    public enum TypeEnum {

        CHECK_BOX("Check Box"),
        COMBO_BOX("Combo box"),
        DATE("Date"),
        MULTI_LINE_TEXT("Multi line text"),
        RADIO_BUTTON("Radio button"),
        SINGLE_LINE_TEXT("Single line text"),
        SLIDER("Slider"),
        EMAIL("Email");

        private String name;

        TypeEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static TypeEnum valueOfString(String str) {
            String result = str.replace(' ', '_');
            return TypeEnum.valueOf(result.toUpperCase());
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    public Field() {
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }


    @Override
    public int compareTo(Field o) {
        return Integer.compare(id, o.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    /**
     * Options will split from string
     * @return List of options
     */
    public List<String> getListOfOptions() {
        if (options != null) {
            return Arrays.asList(options.split(System.lineSeparator()));
        }

        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Field{" +
                "typeEnum=" + typeEnum +
                ",options=" + options + '}' +
                super.toString();
    }
}
