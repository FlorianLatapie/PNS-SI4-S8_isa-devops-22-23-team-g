import { Test, TestingModule } from '@nestjs/testing';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import {PaymentDto} from "./dto/payment.dto";
import {HttpException} from "@nestjs/common";

describe('AppController', () => {
  let appController: AppController;

  const goodPaymentDto: PaymentDto = {
    creditCard: '1230896983',
    amount: 43.7
  };

  const badPaymentDto: PaymentDto = {
    creditCard: '1234567890',
    amount: 43.7
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
      expect(appController.getAllTransactions().length).toBe(0);
    });
  });

  describe('payByCredit()', () => {
    it('should return the same PaymentDto with transaction success', () => {
      expect(appController.payByCreditCard(goodPaymentDto)).toBe(goodPaymentDto);
      expect(appController.getAllTransactions().length).toBe(1);
    });
  });

  describe('payByCredit()', () => {
    it('should throw exception transaction failure', () => {
      expect(() => appController.payByCreditCard(badPaymentDto)).toThrow(HttpException);
      expect(appController.getAllTransactions().length).toBe(0);
    });
  });

});
