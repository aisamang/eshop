package kz.ais.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    public final static Long ROLE_ADMIN_ID = 1L;
    public final static Long ROLE_CLIENT_ID = 2L;
    public final static Long ROLE_GUEST_ID = 3L;

    public final static String ROLE_ADMIN_NAME = "ROLE_ADMIN";
    public final static String ROLE_CLIENT_NAME = "ROLE_CLIENT";
    public final static String ROLE_GUEST_NAME = "ROLE_GUEST";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String description;

    public static Long getRoleAdminId() {
        return ROLE_ADMIN_ID;
    }

    public static Long getRoleClientId() {
        return ROLE_CLIENT_ID;
    }

    public static Long getRoleGuestId() {
        return ROLE_GUEST_ID;
    }

    public static String getRoleAdminName() {
        return ROLE_ADMIN_NAME;
    }

    public static String getRoleClientName() {
        return ROLE_CLIENT_NAME;
    }

    public static String getRoleGuestName() {
        return ROLE_GUEST_NAME;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
