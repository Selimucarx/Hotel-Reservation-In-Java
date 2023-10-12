package backend.service.reservation;

import backend.entity.Reservation;
import backend.entity.ReservationAvailability;
import backend.entity.Room;
import backend.repository.reservation.ReservationAvailabilityRepository;
import backend.repository.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ReservationAvailabilityService {
    @Autowired
    private ReservationAvailabilityRepository reservationAvailabilityRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationAvailability> getAvailabilityBetweenDates(Long roomId, Date start, Date end) {
        return reservationAvailabilityRepository.findAvailabilityBetweenDates(roomId, start, end);
    }

    public boolean isAvailable(Room room, Date checkInDate, Date checkOutDate) {
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(room.getRoomId(), checkInDate, checkOutDate);
        if (!overlappingReservations.isEmpty()) {
            return false;
        }

        List<ReservationAvailability> availabilities = reservationAvailabilityRepository.findAvailabilityBetweenDates(room.getRoomId(), checkInDate, checkOutDate);
        return availabilities.stream().allMatch(a -> "available".equals(a.getAvailable()));
    }


    public void saveAll(List<ReservationAvailability> availabilities) {
        reservationAvailabilityRepository.saveAll(availabilities);
    }



}
