package org.chainoptim.features.goods.stage.controller;

import org.chainoptim.core.general.security.service.SecurityService;
import org.chainoptim.features.goods.stage.dto.CreateStageDTO;
import org.chainoptim.features.goods.stage.dto.StagesSearchDTO;
import org.chainoptim.features.goods.stage.dto.UpdateStageDTO;
import org.chainoptim.features.goods.stage.model.Stage;
import org.chainoptim.features.goods.stage.service.StageService;
import org.chainoptim.features.goods.stage.service.StageWriteService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stages")
public class StageController {

    private final StageService stageService;
    private final StageWriteService stageWriteService;
    private final SecurityService securityService;

    @Autowired
    public StageController(StageService stageService,
                           StageWriteService stageWriteService,
                           SecurityService securityService) {
        this.stageService = stageService;
        this.stageWriteService = stageWriteService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Stage\", \"Read\")")
    @GetMapping("/organization/{organizationId}/small")
    public ResponseEntity<List<StagesSearchDTO>> getStagesByOrganizationIdSmall(@PathVariable Integer organizationId) {
        List<StagesSearchDTO> stages = stageService.getStagesByOrganizationIdSmall(organizationId);
        if (stages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stages);
    }

    @PreAuthorize("@securityService.canAccessEntity(#productId, \"Product\", \"Read\")")
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Stage>> getStagesByProductId(@PathVariable Integer productId) {
        return ResponseEntity.ok(stageService.getStagesByProductId(productId));
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Product\", \"Read\")")
    @GetMapping("/organization/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<StagesSearchDTO>> getStagesByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<StagesSearchDTO> paginatedResults = stageService.getStagesByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(paginatedResults);
    }

    @PreAuthorize("@securityService.canAccessEntity(#stageId, \"Stage\", \"Read\")")
    @GetMapping("/{stageId}")
    public ResponseEntity<Stage> getStageById(@PathVariable("stageId") Integer stageId) {
        Stage stage = stageService.getStageById(stageId);
        return ResponseEntity.ok(stage);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#stageDTO.getOrganizationId(), \"Stage\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<Stage> createStage(@RequestBody CreateStageDTO stageDTO) {
        Stage stage = stageWriteService.createStage(stageDTO);
        return ResponseEntity.ok(stage);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#updateStageDTO.getId(), \"Stage\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Stage> updateStage(@RequestBody UpdateStageDTO updateStageDTO) {
        Stage stage = stageWriteService.updateStage(updateStageDTO);
        return ResponseEntity.ok(stage);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#stageId, \"Stage\", \"Delete\")")
    @DeleteMapping("/delete/{stageId}")
    public ResponseEntity<Void> deleteStage(@PathVariable Integer stageId) {
        stageWriteService.deleteStage(stageId);
        return ResponseEntity.ok().build();
    }
}
