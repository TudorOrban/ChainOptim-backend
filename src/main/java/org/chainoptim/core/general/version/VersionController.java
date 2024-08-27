package org.chainoptim.core.general.version;

import com.vdurmont.semver4j.Semver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/desktop-versions")
public class VersionController {

    @Value("${chainoptim.desktop.latest.version}")
    private String desktopLatestVersion;

    @GetMapping("/check-version")
    public ResponseEntity<CheckVersionResponse> checkVersion(@RequestParam String currentVersion) {
        try {
            Semver current = new Semver(currentVersion, Semver.SemverType.NPM);
            Semver latest = getLatestVersion();
            if (current.isLowerThan(latest)) {
               return ResponseEntity.ok(
                   new CheckVersionResponse(
                        currentVersion,
                        latest.getValue(),
                        "https://chainoptim.org/desktop/download/" + latest.getValue(),
                        true
                   )
               );
            } else {
                return ResponseEntity.ok(
                    new CheckVersionResponse(
                        currentVersion,
                        latest.getValue(),
                        "",
                        false
                    )
                );
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private Semver getLatestVersion() {
        return new Semver(desktopLatestVersion, Semver.SemverType.NPM);
    }


}
