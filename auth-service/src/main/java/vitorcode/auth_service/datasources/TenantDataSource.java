package vitorcode.auth_service.datasources;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vitorcode.auth_service.dtos.AdminUserResponse;
import vitorcode.auth_service.dtos.LoginTenantServiceRequest;


@FeignClient(name = "tenant-service")
public interface TenantDataSource {
    @PostMapping("/tenant/admin/login")
    AdminUserResponse getAdminUserByUsername(@RequestBody @Valid LoginTenantServiceRequest username);
}
