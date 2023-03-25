package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class IBAN {
    String iban;

    public IBAN() {
    }

    public IBAN(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IBAN iban1 = (IBAN) o;
        return Objects.equals(iban, iban1.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }

    @Override
    public String toString() {
        return "IBAN{" +
                "iban='" + iban + '\'' +
                '}';
    }
}
