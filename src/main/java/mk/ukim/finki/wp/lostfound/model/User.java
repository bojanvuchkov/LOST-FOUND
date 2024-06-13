package mk.ukim.finki.wp.lostfound.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_user")
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToMany()
    private List<Email> receivedEmails;

    public User(String name, String email, UserRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
        receivedEmails = new ArrayList<>();
    }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
