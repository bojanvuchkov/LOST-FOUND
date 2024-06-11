package mk.ukim.finki.wp.lostfound.model;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.wp.lostfound.model.enums.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private boolean isLost;
    private LocalDateTime dateRegistered;
    @ManyToOne
    private Category category;
    private LocalDateTime dateResolved;
    private boolean matchingFound;
    private byte[] image;
    private String location;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private User user;
//    @ManyToOne
//    private Location meetingLocation;

    public Item(String name, String description, boolean isLost, Category category, byte[] image, String location, User user) {
        this.name = name;
        this.description = description;
        this.isLost = isLost;
        this.category = category;
        this.image = image;
        this.location = location;
        this.status = Status.OPEN;
        this.dateRegistered = LocalDateTime.now();
        this.matchingFound = false;
        this.dateResolved = null;
        this.user = user;
    }
}
