package org.chainoptim.core.tenant.settings.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralSettings {

    private InfoLevel infoLevel;


    public enum InfoLevel {
        NONE,
        ADVANCED,
        ALL
    }
}
