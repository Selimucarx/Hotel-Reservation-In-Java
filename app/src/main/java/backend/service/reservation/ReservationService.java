package backend.service.reservation;

import backend.dto.ReservationDTO;
import backend.entity.Reservation;
import backend.entity.Room;
import backend.repository.reservation.ReservationRepository;
import backend.repository.hotel.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private RoomRepository roomRepository;

    public ReservationDTO createReservation(ReservationDTO reservationDTO, Principal principal) {
        Reservation reservation = new Reservation();

        if(reservationDTO.getRoomId() == null) {
            throw new IllegalArgumentException("Room ID cannot be null.");
        }

        Room room = roomRepository.findById(reservationDTO.getRoomId()).orElse(null);
        if(room == null) {
            throw new IllegalArgumentException("Room with ID " + reservationDTO.getRoomId() + " not found.");
        }

        reservation.setRoom(room);


        reservation.setUserId(reservationDTO.getUserId());
        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        reservation.setTrackerNumber(reservationDTO.getTrackerNumber());
        reservation.setAdult(reservationDTO.getAdult());

        reservation = reservationRepository.save(reservation);

        return convertToDTO(reservation);
    }



    private ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(reservation.getUserId());
        reservationDTO.setCheckInDate(reservation.getCheckInDate());
        reservationDTO.setCheckOutDate(reservation.getCheckOutDate());
        reservationDTO.setTrackerNumber(reservation.getTrackerNumber());
        reservationDTO.setAdult(reservation.getAdult());
        if (reservation.getRoom() != null) {
            reservationDTO.setRoomId(reservation.getRoom().getRoomId());
        }
        return reservationDTO;
    }

}
