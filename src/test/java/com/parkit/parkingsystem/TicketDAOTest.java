package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

import org.junit.jupiter.api.*;

import java.util.Date;

public class TicketDAOTest {

    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;


    @BeforeEach
    private void setUpTest() {
        ticketDAO = new TicketDAO();
        TicketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @AfterEach
            private void tearDown() {
            dataBasePrepareService.clearDataBaseEntries();
        }


    @Test 
    public void saveTicketTest(){
        Ticket ticket = new Ticket();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        ticket.setInTime(inTime);
        ticket.setPrice(1.5);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");

        ticketDAO.saveTicket(ticket);

        Assertions.assertFalse(ticketDAO.saveTicket(ticket)); // if ticketDAO.getTicket return "ABCDEF" so TicketDAO.saveTicket save ticket //
    }
    @ Test
    public void VehicleHistoryTest() {
        Ticket ticket = new Ticket();
        Date inTime = new Date();
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(2);
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);

        ticketDAO.saveTicket(ticket);
        ticketDAO.VehicleHistory("ABCDEF");

        Assertions.assertEquals(ticketDAO.VehicleHistory("ABCDEF"), 1);
    }

    @Test 
    public void updateTicketTest() {

        Ticket ticket = new Ticket();
        Date inTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(0);
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        ticket.setInTime(inTime);
        ticketDAO.saveTicket(ticket);

        Date outTime = new Date();
        ticket.setOutTime(outTime);
        ticket.setPrice(1.5);
        ticketDAO.updateTicket(ticket);

        Assertions.assertNotEquals(ticketDAO.updateTicket(ticket), ticketDAO.saveTicket(ticket));
    }
    }
