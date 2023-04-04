package fr.univcotedazur.simpletcfs.controllers.dto.payement;

import java.util.ArrayList;
import java.util.List;

public class ListAdvantageItemDTO {
    private final List<AdvantageItemDTO> advantageItemDTOs;

    public ListAdvantageItemDTO() {
        this.advantageItemDTOs = new ArrayList<>();
    }

    public ListAdvantageItemDTO(List<AdvantageItemDTO> advantageItemDTOs) {
        this.advantageItemDTOs = advantageItemDTOs;
    }

    public ListAdvantageItemDTO(AdvantageItemDTO[] advantageItemDTOs) {
        this.advantageItemDTOs = List.of(advantageItemDTOs);
    }

    public List<AdvantageItemDTO> getAdvantageItemDTOs() {
        return advantageItemDTOs;
    }

    @Override
    public String toString() {
        return "ListAdvantageItemDTO{" +
                "advantageItemDTOs=" + advantageItemDTOs +
                '}';
    }
}