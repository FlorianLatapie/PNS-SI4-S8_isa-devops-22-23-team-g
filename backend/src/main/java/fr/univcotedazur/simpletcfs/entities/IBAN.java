package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class IBAN {
    String number;

    public IBAN() {
    }

    public IBAN(String iban) {
        this.number = iban;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String iban) {
        this.number = iban;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IBAN iban1 = (IBAN) o;
        return Objects.equals(number, iban1.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "IBAN{" +
                "iban='" + number + '\'' +
                '}';
    }
}
