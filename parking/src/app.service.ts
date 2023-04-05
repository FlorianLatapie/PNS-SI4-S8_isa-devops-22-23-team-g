import { Injectable } from '@nestjs/common';

import {
  ParkingDurationDto,
  ParkingTicketDto,
  FreeParkingDto,
} from './dto/parking.dto';
import { ParkingRejectedException } from './exceptions/parking-rejected-exception';

@Injectable()
export class AppService {
  private static readonly magicKey: string = '896983'; // ASCII code for 'YES'
  private parkingTransactions: Array<ParkingTicketDto>;
  private FREE_PARKING_DURATION = 30; // conversion ms to minutes
  private MS_TO_MINUTES = 60*1000; // conversion ms to minutes
  private PRICE_PER_HOUR = 1;

  constructor() {
    this.parkingTransactions = [];
  }

  findAll(): ParkingTicketDto[] {
    return this.parkingTransactions;
  }

  parkForFree(freeParkingDto: FreeParkingDto): ParkingTicketDto {
    console.log('paymentDto.creditCard : ' + freeParkingDto.licensePlate);
    if (freeParkingDto.licensePlate.includes(AppService.magicKey)) {
      const parkingTicket: ParkingTicketDto = {
        licensePlate: freeParkingDto.licensePlate,
        duration: this.FREE_PARKING_DURATION,
        startDate: Date.now(),
        endDate: Date.now() + this.FREE_PARKING_DURATION * this.MS_TO_MINUTES,
        price: 0,
      };
      this.parkingTransactions.push(parkingTicket);
      console.log('parkingSuccessful');
      console.log('---------------------------------');
      return parkingTicket;
    } else {
      console.log('parkingRejected');
      console.log('---------------------------------');
      throw new ParkingRejectedException(freeParkingDto.licensePlate);
    }
  }

  private calculatePrice(duration: number): number {
    return Math.ceil(duration / 60) * this.PRICE_PER_HOUR;
  }

  parkForDuration(parkingDurationDto: ParkingDurationDto): ParkingTicketDto {
    console.log('paymentDto.creditCard : ' + parkingDurationDto.licensePlate);
    if (parkingDurationDto.licensePlate.includes(AppService.magicKey)) {
      const payableDuration = Math.abs(
        parkingDurationDto.duration - this.FREE_PARKING_DURATION,
      );
      const price = this.calculatePrice(payableDuration);
      const parkingTicket: ParkingTicketDto = {
        licensePlate: parkingDurationDto.licensePlate,
        duration: parkingDurationDto.duration,
        startDate: Date.now(),
        endDate: Date.now() + parkingDurationDto.duration * this.MS_TO_MINUTES,
        price,
      };
      this.parkingTransactions.push(parkingTicket);
      console.log('parkingSuccessful');
      console.log('---------------------------------');
      return parkingTicket;
    } else {
      console.log('parkingRejected');
      console.log('---------------------------------');
      throw new ParkingRejectedException(parkingDurationDto.licensePlate);
    }
  }
}
