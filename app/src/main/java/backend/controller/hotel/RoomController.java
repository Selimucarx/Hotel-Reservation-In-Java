package backend.controller.hotel;

import backend.entity.Room;
import backend.service.hotel.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotel/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.getRoomById(id);
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        try {
            Room createdRoom = roomService.saveRoom(room);
            return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while trying to create room", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
