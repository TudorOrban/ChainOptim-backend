package org.chainoptim.features.supplier.dto;

import org.chainoptim.shared.search.dto.SmallEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierOverviewDTO {


    private List<SmallEntityDTO> suppliedComponents;
    private List<SmallEntityDTO> deliveredToFactories;
    private List<SmallEntityDTO> deliveredToWarehouses;
}
