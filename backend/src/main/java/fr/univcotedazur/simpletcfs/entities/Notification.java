package fr.univcotedazur.simpletcfs.entities;

import java.util.List;

public record Notification(List<ContactDetails> contactDetails, NotificationMessage message) {
}
