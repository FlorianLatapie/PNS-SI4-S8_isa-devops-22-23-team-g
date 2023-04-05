import { IsNotEmpty, IsPositive, IsString } from 'class-validator';

class FreeParkingDto {
  @IsNotEmpty()
  @IsString()
  licensePlate: string;
}

class ParkingDurationDto {
  @IsNotEmpty()
  @IsString()
  licensePlate: string;

  @IsNotEmpty()
  @IsPositive()
  duration: number;
}

class ParkingTicketDto {
  @IsNotEmpty()
  @IsString()
  licensePlate: string;

  @IsNotEmpty()
  endDate: number;

  @IsNotEmpty()
  startDate: number;

  @IsNotEmpty()
  @IsPositive()
  duration: number;

  @IsNotEmpty()
  @IsPositive()
  price: number;
}

export { FreeParkingDto, ParkingDurationDto, ParkingTicketDto };
