package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.slugify.Slugify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

   private int id;
   private String username;
   @JsonIgnore
   private String password;
   @JsonIgnore
   private boolean activated;
   private Set<Authority> authorities = new HashSet<>();

   private String email;
   private String location;
   private int skillLevel;
   private LocalDateTime created;
   private String urlSlug;

   public User() { }

   public User(int id, String username, String password, String authorities, String email, String location, int skillLevel, LocalDateTime created) {
      this.id = id;
      this.username = username;
      this.password = password;
      if (authorities != null) this.setAuthorities(authorities);
      this.activated = true;
      this.email = email;
      if (location != null) this.location = location;
      this.skillLevel = skillLevel;
      setUrlSlug();
   }

   public User(int id, String username, String location, int skillLevel, LocalDateTime created) {
      this.id = id;
      this.username = username;
      if (location != null) this.location = location;
      this.skillLevel = skillLevel;
      this.created = created;
      setUrlSlug();
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for (String role : roles) {
         String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
         this.authorities.add(new Authority(authority));
      }
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public int getSkillLevel() {
      return skillLevel;
   }

   public void setSkillLevel(int skillLevel) {
      this.skillLevel = skillLevel;
   }

   public LocalDateTime getCreated() {
      return created;
   }

   public void setCreated(LocalDateTime created) {
      this.created = created;
   }

   public String getUrlSlug() {
      return urlSlug;
   }

   public void setUrlSlug() {
      Slugify slg = Slugify.builder().build();
      this.urlSlug = slg.slugify(username);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(authorities, user.authorities) &&
              Objects.equals(email, user.email) &&
              Objects.equals(location, user.location) &&
              Objects.equals(skillLevel, user.skillLevel) &&
              Objects.equals(created, user.created);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities, email, location, skillLevel, created);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", password='" + password + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              ", email='" + email + '\'' +
              ", location='" + location + '\'' +
              ", skillLevel=" + skillLevel +
              ", created=" + created +
              '}';
   }

}
