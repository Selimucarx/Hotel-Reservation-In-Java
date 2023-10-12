package backend.service.hotel;

import backend.entity.Room;
import backend.repository.hotel.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }


    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
}
