package org.chainoptim.features.factory.dto;

import org.chainoptim.shared.search.dto.SmallEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactoryOverviewDTO {

    private List<SmallEntityDTO> manufacturedComponents;
    private List<SmallEntityDTO> manufacturedProducts;

}
