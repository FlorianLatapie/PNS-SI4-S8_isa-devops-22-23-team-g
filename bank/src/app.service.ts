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
    console.log('paymentDto.creditCard : ' + paymentDto.creditCard + ' amount : ' + paymentDto.amount);
    if (paymentDto.creditCard.includes(AppService.magicKey)) {
        this.transactions.push(paymentDto);
        console.log('paymentSuccess');
        console.log('---------------------------------');
        return paymentDto;
      } else {
        console.log('paymentRejected');
        console.log('---------------------------------');
        throw new PaymentRejectedException(paymentDto.amount);
      }
    }

}
