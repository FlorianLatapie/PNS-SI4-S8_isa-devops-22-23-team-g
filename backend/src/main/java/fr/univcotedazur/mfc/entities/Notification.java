package fr.univcotedazur.mfc.entities;

import java.util.List;

public record Notification(List<ContactDetails> contactDetails, NotificationMessage message) {
}
