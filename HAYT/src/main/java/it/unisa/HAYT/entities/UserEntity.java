package it.unisa.HAYT.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Lob
    private byte[] profileImage;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;


    public UserEntity(String email, String firstName, String lastName , String password, byte[] profileImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.setProfileImage(profileImage);
    }

    public String getProfileImageBase64() {
        return (profileImage != null) ? Base64.getEncoder().encodeToString(profileImage) : null;
    }

    public abstract String getRole();
}

