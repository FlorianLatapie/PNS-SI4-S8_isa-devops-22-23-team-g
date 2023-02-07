import { IsNotEmpty, IsPositive, IsString } from 'class-validator';

export class PaymentDto {

    @IsNotEmpty()
    @IsString()
    creditCard: string;

    @IsNotEmpty()
    @IsPositive()
    amount: number;

}