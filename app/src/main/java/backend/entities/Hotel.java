// Hotel.java
package backend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotels", schema = "sandalsDB")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private int hotelId;

    @Column(name = "location")
    private String location;

    // One-to-many relationship with Room
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    // One-to-many relationship with ReservationAvailability
    @OneToMany(mappedBy = "hotel")
    private List<ReservationAvailability> reservationAvailabilities;

}
