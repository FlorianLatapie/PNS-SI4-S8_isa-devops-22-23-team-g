package fr.univcotedazur.simpletcfs.connectors.externalDTO;

public class FreeParkingDTO {

    private String licensePlate;

    public FreeParkingDTO() {}

    public FreeParkingDTO(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setCreditCard(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
