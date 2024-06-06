package ra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String email;
    private String password;
    private String phone;
    @Column(columnDefinition = "date")
    private Date dob;
    private Boolean sex;
    private String fullName;
    private Boolean isDeleted;
    private Boolean isBlocked;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // cascade : lan truyền hành vi
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "role_id")
    )
    private Set<Role> roleSet;
}
