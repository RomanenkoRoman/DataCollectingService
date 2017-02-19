package models.entity;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Роман on 27.11.2016.
 * Contains all the answers from fields from user
 */
@javax.persistence.Entity
public class Response implements Comparable<Response> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToMany(targetEntity = Answer.class, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Answer> answerList;

    public Response() {
        answerList = new ArrayList<>();
    }


    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> responseList) {
        this.answerList = responseList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addField(Answer type) {
        answerList.add(type);
    }

    @Override
    public int compareTo(Response o) {
        return Integer.compare(id, o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        return answerList != null ? answerList.equals(response.answerList)
                : response.answerList == null;

    }

    @Override
    public int hashCode() {
        return answerList != null ? answerList.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "Response{" +
                "responseList=" + answerList +
                '}';
    }

}
