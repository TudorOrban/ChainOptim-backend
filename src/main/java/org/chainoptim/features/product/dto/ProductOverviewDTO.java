package org.chainoptim.features.product.dto;

import org.chainoptim.shared.search.dto.SmallEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOverviewDTO {

    private List<SmallEntityDTO> stages;
    private List<SmallEntityDTO> manufacturedInFactories;
    private List<SmallEntityDTO> storedInWarehouses;
    private List<SmallEntityDTO> orderedByClients;
}
