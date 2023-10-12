package backend.entity;

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
@Table(name = "rooms", schema = "sandalsDB")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;


    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "room_floor")
    private String roomFloor;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "price_per_night")
    private float pricePerNight;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
    private Hotel hotel;




    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "room")
    private List<ReservationAvailability> reservationAvailabilities;

}
