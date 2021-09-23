package com.protobuf.test.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.Vector;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "id"}, name = "users_idx")})
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "proto_data", nullable = false)
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] protoData;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "role"}, name = "user_roles_idx")})
    @Column(name = "role", nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Transient
    private String firstName;
    @Transient
    private String lastName;
    @Transient
    private int age;
    @Transient
    private long telNumber;
    @Transient
    private float weight; // вес
    @Transient
    private float height; // рост
    @Transient
    private String password;

    public User(long id, String email, byte[] protoData){
        this.id = id;
        this.email = email;
        this.protoData = protoData;
    }

    public Collection<? extends GrantedAuthority> getRoleSet() {
        return roles;
    }

    public void setRoleSet(Vector roleSet) {
        this.roles = CollectionUtils.isEmpty(roleSet) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roleSet);
    }

    public boolean hasId(){
        return id != null;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", protoData='" + protoData + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (!(obj instanceof User)){
            return false;
        }

        User user = (User) obj;

        if (this.email.equals(user.getEmail())
        && this.firstName.equals(user.getFirstName())
        && this.lastName.equals(user.getLastName())
        && this.height == user.height
        && this.weight == user.weight
        && this.age == user.age
        && this.telNumber == user.telNumber
        && this.password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
