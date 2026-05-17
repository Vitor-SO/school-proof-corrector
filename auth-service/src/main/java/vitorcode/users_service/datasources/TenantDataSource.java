package vitorcode.users_service.datasources;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vitorcode.users_service.dtos.AdminUserResponse;
import vitorcode.users_service.dtos.LoginAdminRequest;
import vitorcode.users_service.dtos.LoginTenantServiceRequest;


@FeignClient(name = "tenant-service")
public interface TenantDataSource {
    @PostMapping("/auth/admin/login")
    AdminUserResponse getAdminUserByUsername(@RequestBody @Valid LoginTenantServiceRequest username);
}
