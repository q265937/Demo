package com.booking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.booking.dto.EventDTO;
import com.booking.dto.UserDTO;
import com.booking.entity.Booking;
import com.booking.interservices.EventClient;
import com.booking.interservices.UserClient;
import com.booking.repository.BookingRepository;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookRepo;

    @Mock
    private UserClient userClient;

    @Mock
    private EventClient eventClient;

    private BookingService bookingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        bookingService = new BookingService(bookRepo);

        // Manually inject mocks into @Autowired fields
        // Using reflection since they are private and not set via constructor
        try {
            java.lang.reflect.Field userClientField = BookingService.class.getDeclaredField("userClient");
            userClientField.setAccessible(true);
            userClientField.set(bookingService, userClient);

            java.lang.reflect.Field eventClientField = BookingService.class.getDeclaredField("eventClient");
            eventClientField.setAccessible(true);
            eventClientField.set(bookingService, eventClient);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mocks", e);
        }
    }

    @Test
    void testNewBooking_Success() {
        Booking booking = new Booking();
        booking.setPrice(100);
        booking.setNoOfSeats(2);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");

        when(userClient.findUser("testuser")).thenReturn(userDTO);
        when(bookRepo.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.newBooking(booking, "testuser");

        assertEquals("testuser", result.getUsername());
        assertEquals(200, result.getPrice());
        verify(bookRepo).save(any(Booking.class));
    }

    @Test
    void testNewBooking_UserNotFound() {
        Booking booking = new Booking();
        when(userClient.findUser("unknown")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> bookingService.newBooking(booking, "unknown"));
    }

    @Test
    void testAllBookings() {
        Booking b1 = new Booking();
        Booking b2 = new Booking();
        when(bookRepo.findAll()).thenReturn(List.of(b1, b2));

        List<Booking> bookings = bookingService.allBookings();

        assertEquals(2, bookings.size());
    }

    @Test
    void testFindBooking_Success() {
        Booking booking = new Booking();
        booking.setBookingId(1);

        when(bookRepo.findById(1)).thenReturn(Optional.of(booking));

        Booking result = bookingService.findBooking(1);

        assertEquals(1, result.getBookingId());
    }

    @Test
    void testFindBooking_NotFound() {
        when(bookRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.findBooking(99));
    }

    @Test
    void testFindByUsername() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");

        when(userClient.findUser("testuser")).thenReturn(userDTO);

        UserDTO result = bookingService.findByUsername("testuser");

        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testFindById() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(1);

        when(eventClient.eventData(1)).thenReturn(eventDTO);

        EventDTO result = bookingService.findById(1);

        assertEquals(1, result.getEventId());
    }

    @Test
    void testFindBookingByEventName_Success() {
        Booking booking = new Booking();
        booking.setEventName("Concert");

        when(bookRepo.findByEventName("Concert")).thenReturn(Optional.of(booking));

        Booking result = bookingService.findBookingByEventName("Concert");

        assertEquals("Concert", result.getEventName());
    }

    @Test
    void testFindBookingByEventName_NotFound() {
        when(bookRepo.findByEventName("Unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.findBookingByEventName("Unknown"));
    }

    @Test
    void testSetQuantity() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(1);
        eventDTO.setNoOfSeats(100);

        when(eventClient.eventData(1)).thenReturn(eventDTO);

        EventDTO result = bookingService.setQuantity(1, 10);

        assertEquals(90, result.getNoOfSeats());
    }

    @Test
    void testGetTotal_NotImplemented() {
        Booking booking = new Booking();
        double total = bookingService.getTotal(booking);
        assertEquals(0, total);
    }
}