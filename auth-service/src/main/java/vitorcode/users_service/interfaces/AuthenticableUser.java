package vitorcode.users_service.interfaces;

import java.util.List;

public interface AuthenticableUser {

    String username();

    String password();

    List<String> roles();

    boolean enabled();
}
