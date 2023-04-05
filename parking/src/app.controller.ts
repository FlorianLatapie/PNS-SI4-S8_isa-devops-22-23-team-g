import {
  Body,
  Controller,
  Get,
  HttpException,
  HttpStatus,
  Post,
} from '@nestjs/common';

import { AppService } from './app.service';
import {
  ParkingDurationDto,
  FreeParkingDto,
  ParkingTicketDto,
} from './dto/parking.dto';

@Controller('parking')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getAllTickets(): ParkingTicketDto[] {
    return this.appService.findAll();
  }


  @Post()
  parkForDuration(
    @Body() parkingDuration: ParkingDurationDto,
  ): ParkingTicketDto {
    try {
      return this.appService.parkForDuration(parkingDuration);
    } catch (e) {
      throw new HttpException(
        'business error: ' + e.message,
        HttpStatus.BAD_REQUEST,
      );
    }
  }

  @Post()
  parkForFree(@Body() parkForFree: FreeParkingDto): ParkingTicketDto {
    try {
      return this.appService.parkForFree(parkForFree);
    } catch (e) {
      throw new HttpException(
        'business error: ' + e.message,
        HttpStatus.BAD_REQUEST,
      );
    }
  }
}
