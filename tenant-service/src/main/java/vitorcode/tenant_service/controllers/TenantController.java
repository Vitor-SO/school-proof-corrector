package vitorcode.tenant_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vitorcode.tenant_service.dtos.CreateTenantRequest;
import vitorcode.tenant_service.services.TenantService;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Validated CreateTenantRequest request){
        try{
            tenantService.createTenant(request);
            tenantService.createUserDb(request);
            tenantService.grantPermissionToUser(request);
            tenantService.runMigrations(request);
            tenantService.insertSchool(request);
            tenantService.insertUser(request);
            tenantService.insertTenantConnectionIntoMaster(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            tenantService.deleteTenant(request);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
