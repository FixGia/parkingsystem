package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;

import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;



public class FareCalculatorService {



    public void calculateFare(Ticket ticket) {
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }



        double inHour = ticket.getInTime().getTime();      // replace int by double and  getHours() by getTime() //
        double outHour = ticket.getOutTime().getTime();
        double duration = ((outHour - inHour)/1000)/3600;// replace int by double and modify logic //






        if (duration > 0.5)  {// add boolean to apply 30minutes free //

        switch (ticket.getParkingSpot().getParkingType()){

            case CAR: {

                if (TicketDAO.checkVehicleRegNumber(ticket.getVehicleRegNumber())) { // reduce 5% for recurrent CarUsers //
                    ticket.setPrice((duration * Fare.CAR_RATE_PER_HOUR)-Fare.REDUCTION_FOR_RECURRENT_USERS);
                }
                else ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);

                break;
            }
            case BIKE: {
                if (TicketDAO.checkVehicleRegNumber(ticket.getVehicleRegNumber())) { // reduce 5% for recurrent BikeUsers//
                    ticket.setPrice((duration * Fare.CAR_RATE_PER_HOUR) - Fare.REDUCTION_FOR_RECURRENT_USERS);
                }
                else ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);

                break;
            }


            default: throw new IllegalArgumentException("Unknown Parking Type");
        }

    }
        else ticket.setPrice(0);
}
}