import { Injectable } from '@nestjs/common';

import {PaymentDto} from './dto/payment.dto';
import {PaymentRejectedException} from './exceptions/payment-rejected-exception';

@Injectable()
export class AppService {

  private static readonly magicKey : string = '896983'; // ASCII code for 'YES'

  private transactions : Array<PaymentDto>;

  constructor() {
    this.transactions = [];
  }

  findAll(): PaymentDto[] {
    return this.transactions;
  }

  pay(paymentDto: PaymentDto): PaymentDto {
    if (paymentDto.creditCard.includes(AppService.magicKey)) {
        this.transactions.push(paymentDto);
        return paymentDto;
      } else {
        throw new PaymentRejectedException(paymentDto.amount);
      }
    }

}
