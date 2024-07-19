package org.example.companytech.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    private List<Role> roles;

    @ManyToOne
    private Company company;

    @OneToOne
    private Contract contract;

}
