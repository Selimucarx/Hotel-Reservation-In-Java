package backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Long userId;
    private Date checkInDate;
    private Date checkOutDate;
    private String trackerNumber;
    private int adult;
    private Long roomId;
}
