package models.entity;


import javax.persistence.*;

/**
 * Created by Роман on 12.12.2016.
 * The answer from concrete field
 */
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "label")
    private String label;

    @Column(name = "value")
    private String value;

    public Answer() {
    }

    public Answer(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return id == answer.id && (label != null ? label.equals(answer.label)
                : answer.label == null && (value != null ? value.equals(answer.value)
                : answer.value == null));

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
