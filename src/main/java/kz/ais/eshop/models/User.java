package kz.ais.eshop.models;

import kz.ais.eshop.models.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "name is required")
    private String firstName;

    @NotNull(message = "lastname is required")
    private String lastName;

    @Column(unique = true)
    @NotNull(message = "email is required")
    private String email;

    @Column(unique = true)
    @NotNull(message = "username is required")
    private String username;

    @NotNull(message = "password is required")
    private String password;

    @NotNull(message = "phone number is required")
    @Column(length = 11)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_role"))
    private Set<Role> role = new HashSet<>();

    public User(@NotNull(message = "name is required") String firstName, @NotNull(message = "lastname is required") String lastName, @NotNull(message = "email is required") String email, @NotNull(message = "username is required") String username, @NotNull(message = "password is required") String password, @NotNull(message = "phone number is required") String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

}
