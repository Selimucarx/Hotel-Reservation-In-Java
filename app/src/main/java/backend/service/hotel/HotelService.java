package backend.service.hotel;

import backend.entity.Hotel;
import backend.repository.hotel.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public Optional<Hotel> getHotelInformation(Integer id) {
        return hotelRepository.findById(id);
    }


}
