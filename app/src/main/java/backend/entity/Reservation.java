package backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations", schema = "sandalsDB")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "user_id")
    private Long userId;


    @Column(name = "check_in_date")
    private Date checkInDate;

    @Column(name = "check_out_date")
    private Date checkOutDate;

    @Column(name = "tracker_number")
    private String trackerNumber;

    @Column(name = "adult")
    private int adult;


    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Room room;



}
