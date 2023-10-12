package backend.repository.reservation;

import backend.entity.ReservationAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationAvailabilityRepository extends JpaRepository<ReservationAvailability, Long> {
    @Query("SELECT ra FROM ReservationAvailability ra WHERE ra.room.roomId = :roomId AND ra.date BETWEEN :start AND :end")
    List<ReservationAvailability> findAvailabilityBetweenDates(@Param("roomId") Long roomId, @Param("start") Date start, @Param("end") Date end);

}