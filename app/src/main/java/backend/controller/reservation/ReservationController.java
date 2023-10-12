package backend.controller.reservation;

import backend.dto.ReservationDTO;
import backend.entity.Reservation;
import backend.entity.User;
import backend.repository.reservation.ReservationRepository;
import backend.repository.user.UserRepository;
import backend.service.reservation.ReservationAvailabilityService;
import backend.service.reservation.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationAvailabilityService reservationAvailabilityService;

    @Autowired
    public ReservationController(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/check-availability")
    public ResponseEntity<String> checkAvailability(@RequestBody Reservation reservation) {

        if (reservation == null) {
            logger.warn("Reservation object is null");
            return new ResponseEntity<>("Reservation object is null.", HttpStatus.BAD_REQUEST);
        }
        if (reservation.getRoom() == null) {
            logger.warn("Room object inside reservation is null");
            return new ResponseEntity<>("Room object inside reservation is null.", HttpStatus.BAD_REQUEST);
        }
        if (reservation.getCheckInDate() == null) {
            logger.warn("CheckInDate is null");
            return new ResponseEntity<>("CheckInDate is null.", HttpStatus.BAD_REQUEST);
        }
        if (reservation.getCheckOutDate() == null) {
            logger.warn("CheckOutDate is null");
            return new ResponseEntity<>("CheckOutDate is null.", HttpStatus.BAD_REQUEST);
        }

        boolean isAvailable = reservationAvailabilityService.isAvailable(reservation.getRoom(), reservation.getCheckInDate(), reservation.getCheckOutDate());
        if (isAvailable) {
            return new ResponseEntity<>("Room is available.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Room is not available.", HttpStatus.BAD_REQUEST);
        }
    }




    @PostMapping
    public ResponseEntity<String> createReservation(@Valid @RequestBody ReservationDTO reservationDTO, Principal principal) {
        logger.info("Received reservation: " + reservationDTO);

        try {
            if (principal == null) {
                logger.warn("Unauthorized access attempt");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to login to make a reservation");
            }

            ReservationDTO createdReservation = reservationService.createReservation(reservationDTO, principal);
            return ResponseEntity.ok("Reservation successfully created: " + createdReservation);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating reservation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating reservation: " + e.getMessage());
        }
    }




    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> reservations = reservationRepository.findAll();
            if (reservations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting reservations", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        try {
            Optional<Reservation> reservationOptional = reservationRepository.findById(id);
            if (reservationOptional.isPresent()) {
                return new ResponseEntity<>(reservationOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error getting reservation by id", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id, Principal principal) {
        try {
            Optional<Reservation> reservationOptional = reservationRepository.findById(id);
            if (reservationOptional.isEmpty()) {
                return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
            }

            Reservation reservation = reservationOptional.get();
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            if (user == null || !reservation.getUserId().equals(user.getId())) {
                logger.warn("Unauthorized delete attempt by user: " + email);
                return new ResponseEntity<>("Unauthorized to delete this reservation.", HttpStatus.FORBIDDEN);
            }

            reservationRepository.deleteById(id);
            return new ResponseEntity<>("Reservation successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting reservation", e);
            return new ResponseEntity<>("Error deleting reservation: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
