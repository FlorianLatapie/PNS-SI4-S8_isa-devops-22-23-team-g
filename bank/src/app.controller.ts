import {Body, Controller, Get, HttpException, HttpStatus, Post} from '@nestjs/common';

import { AppService } from './app.service';
import { PaymentDto } from './dto/payment.dto';

@Controller('cctransactions')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getAllTransactions(): PaymentDto[] {
    return this.appService.findAll();
  }

  @Post()
  payByCreditCard(@Body() paymentDto: PaymentDto): PaymentDto {
    try {
      return this.appService.pay(paymentDto);
    } catch (e) {
      throw new HttpException('business error: ' + e.message, HttpStatus.BAD_REQUEST);
    }
  }

}
