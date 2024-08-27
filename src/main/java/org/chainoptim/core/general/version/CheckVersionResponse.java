package org.chainoptim.core.general.version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckVersionResponse {

    private String currentVersion;
    private String latestVersion;
    private String downloadUrl;
    private boolean isUpdateAvailable;
}
