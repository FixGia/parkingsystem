package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;

import com.parkit.parkingsystem.model.Ticket;



public class FareCalculatorService {


    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }


        double inHour = ticket.getInTime().getTime();      // replace int by double and  getHours() by getTime() //
        double outHour = ticket.getOutTime().getTime();
        double duration = ((outHour - inHour) / 1000) / 3600;// replace int by double and modify logic //


        if (duration > 0.5) {// add boolean to apply 30minutes free //
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR:
                    ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                    break;
                case BIKE:
                    ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown Parking Type");
            }
        } else ticket.setPrice(0);
    }

    public void applyReduction(int numberOfTicket, Ticket ticket) {

        if (numberOfTicket > 0 && ticket.getPrice() > 0) {
            ticket.setPrice(ticket.getPrice() - (ticket.getPrice() * 0.05));
            System.out.println("victoire");
        }

    }
}


