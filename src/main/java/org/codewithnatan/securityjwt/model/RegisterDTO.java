package org.codewithnatan.securityjwt.model;

public record RegisterDTO(String username, String password, UserRole role) {
}
