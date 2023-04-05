export class ParkingRejectedException extends Error {
  constructor(licencePlate: string) {
    super(`Parking rejected as "${licencePlate}" cannot be found`);
  }
}
