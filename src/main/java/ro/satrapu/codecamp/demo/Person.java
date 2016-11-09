package ro.satrapu.codecamp.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * Represents a human being, happy to be present to a Codecamp event.
 */
@Entity
@Table(name = "persons")
public class Person implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @NotNull
  @Column(name = "firstname", nullable = false)
  private String firstName;

  @NotNull
  @Column(name = "lastname", nullable = false)
  private String lastName;

  public Integer getId() {
    return id;
  }

  private void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
