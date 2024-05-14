package mk.ukim.finki.wp.lostfound.model;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.wp.lostfound.model.enums.Status;

import javax.faces.context.FacesContext;
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
    private String image;
    @Transient
    private Location location;
    private String loc;
//    @ManyToOne
//    private Location meetingLocation;
    @Enumerated(EnumType.STRING)
    private Status status;
//    @ManyToOne
//    private User user;

    public Item(String name, String description, boolean isLost, Category category, String image, Location location) {
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
        //this.meetingLocation = null;

    }

    public Item(String name, String description, boolean isLost, Category category, String image, String location) {
        this.name = name;
        this.description = description;
        this.isLost = isLost;
        this.category = category;
        this.image = image;
        this.loc = location;
        this.status = Status.OPEN;
        this.dateRegistered = LocalDateTime.now();
        this.matchingFound = false;
        this.dateResolved = null;
        //this.meetingLocation = null;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }

}
