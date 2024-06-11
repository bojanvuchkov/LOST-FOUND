package mk.ukim.finki.wp.lostfound.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    private String subject;
    private String message;
    private LocalDateTime dateTime;

    public Email(User sender, User receiver, String subject, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }
}
