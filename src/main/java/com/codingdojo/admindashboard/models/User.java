package com.codingdojo.admindashboard.models;
// imports removed for brevity

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
 
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @Size(min=1)
 private String firstName;
 @Size(min=1)
 private String lastName;
 private String email;

 @Size(min=5, message="Password must be greater than 5 characters")
 private String password;
 
 @Transient
 @Size(min=10, message="Password must be at least 10 characters!")
 private String passwordConfirmation;
 
 private String role;
 
 @Column(updatable=false)
 private Date createdAt;
 
 private String signInDate;
 
 private Date updatedAt;
 
 public User() {}
 
 public String getRole() {
	 return role;
 }
 
 public void setRole(String role) {
	 this.role = role;
 }
 
 public String getSignInDate() {
	 return signInDate;
 }
 
 public void setSignInDate(String signInDate) {
	 this.signInDate = signInDate;
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

public String getFullName() {
	return this.firstName + " " + this.lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}
 
 public Long getId() {
     return id;
 }
 public void setId(Long id) {
     this.id = id;
 }
 public String getPassword() {
     return password;
 }
 public void setPassword(String password) {
     this.password = password;
 }
 public String getPasswordConfirmation() {
     return passwordConfirmation;
 }
 public void setPasswordConfirmation(String passwordConfirmation) {
     this.passwordConfirmation = passwordConfirmation;
 }
 public Date getCreatedAt() {
     return createdAt;
 }
 public void setCreatedAt(Date createdAt) {
     this.createdAt = createdAt;
 }
 public Date getUpdatedAt() {
     return updatedAt;
 }
 public void setUpdatedAt(Date updatedAt) {
     this.updatedAt = updatedAt;
 }
 
 public boolean equals(User other) {
	 return this.getId() == other.getId();
 }
 
 @PrePersist
 protected void onCreate(){
     this.createdAt = new Date();
 }
 @PreUpdate
 protected void onUpdate(){
     this.updatedAt = new Date();
 }
}
