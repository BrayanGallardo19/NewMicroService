package com.example.geografiaService.controller;

import com.example.geografiaService.model.Region;
import com.example.geografiaService.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regiones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegionController {
    
    private final RegionService regionService;
    
    @GetMapping
    public ResponseEntity<List<Region>> getAllRegiones() {
        return ResponseEntity.ok(regionService.getAllRegiones());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.getRegionById(id));
    }
    
    @PostMapping
    public ResponseEntity<Region> createRegion(@Valid @RequestBody Region region) {
        return ResponseEntity.status(HttpStatus.CREATED).body(regionService.createRegion(region));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable Integer id, @Valid @RequestBody Region region) {
        return ResponseEntity.ok(regionService.updateRegion(id, region));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        regionService.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }
}
