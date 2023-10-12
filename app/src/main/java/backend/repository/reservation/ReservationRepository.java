package backend.repository.reservation;

import backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserId(Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.room.roomId = :roomId AND " +
            "((:checkInDate BETWEEN r.checkInDate AND r.checkOutDate) OR " +
            "(:checkOutDate BETWEEN r.checkInDate AND r.checkOutDate) OR " +
            "(r.checkInDate BETWEEN :checkInDate AND :checkOutDate))")
    List<Reservation> findOverlappingReservations(@Param("roomId") Long roomId, @Param("checkInDate") Date checkInDate, @Param("checkOutDate") Date checkOutDate);
}

