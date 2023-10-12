package backend.controller.hotel;

import backend.entity.Hotel;
import backend.entity.Reservation;
import backend.repository.reservation.ReservationRepository;
import backend.service.hotel.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelInformation(@PathVariable Integer id) {
        return hotelService.getHotelInformation(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rooms/{roomId}/availability")
    public ResponseEntity<String> checkRoomAvailability(@PathVariable Long roomId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkInDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkOutDate) {
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(roomId, checkInDate, checkOutDate);

        if (overlappingReservations.isEmpty()) {
            return ResponseEntity.ok("Available");
        } else {
            return ResponseEntity.ok("Busy");
        }
    }

}
