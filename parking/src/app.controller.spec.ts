import { Test, TestingModule } from '@nestjs/testing';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ParkingTicketDto, FreeParkingDto } from './dto/parking.dto';
import { HttpException } from '@nestjs/common';

describe('AppController', () => {
  let appController: AppController;

  const goodParkingDto: FreeParkingDto = {
    licensePlate: "896983",
  };

  const badParkingDto: FreeParkingDto = {
    licensePlate: "000000"
  };

  const goodParkingTicket: ParkingTicketDto = {
    licensePlate: "896983",
    duration: 30,
    price:0,
    startDate: Date.now(),
    endDate: Date.now() + 30*60000
  };

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      controllers: [AppController],
      providers: [AppService],
    }).compile();

    appController = app.get<AppController>(AppController);
  });

  describe('root', () => {
    it('should return no transactions at startup', () => {
      expect(appController.getAllTickets().length).toBe(0);
    });
  });

  describe('parkForFree()', () => {
    it('should return the same expected parking Ticket', () => {
      const parkingTicket = appController.parkForFree(goodParkingDto);
      expect(parkingTicket.duration).toBe(goodParkingTicket.duration);
      expect(parkingTicket.licensePlate).toBe(goodParkingTicket.licensePlate);
      expect(parkingTicket.price).toBe(goodParkingTicket.price);
      expect(parkingTicket.startDate).toBeGreaterThan(goodParkingTicket.startDate);
      expect(parkingTicket.endDate).toBeGreaterThan(goodParkingTicket.endDate);
      expect(parkingTicket.startDate).toBeLessThan(goodParkingTicket.startDate+1000);
      expect(parkingTicket.endDate).toBeLessThan(goodParkingTicket.endDate+1000);
      expect(appController.getAllTickets().length).toBe(1);
    });
  });

  describe('parkForFree()', () => {
    it('should throw exception parking failure', () => {
      expect(() => appController.parkForFree(badParkingDto)).toThrow(
        HttpException,
      );
      expect(appController.getAllTickets().length).toBe(0);
    });
  });
});
