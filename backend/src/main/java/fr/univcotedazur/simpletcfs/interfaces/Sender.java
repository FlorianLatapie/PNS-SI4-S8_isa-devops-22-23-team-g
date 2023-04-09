package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Notification;

public interface Sender {
    void send(Notification notification);
}
