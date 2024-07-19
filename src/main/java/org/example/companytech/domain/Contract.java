package org.example.companytech.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String email;

    private String phone;

    @Column(name = "passport_series", nullable = false)
    private String passportSeries;

    @Column( nullable = false)
    private String jshshir;

    @Column(name = "contract_number", nullable = false)
    private String contractNumber;

    @Column(name = "contract_date", nullable = false)
    private Date contractDate;

    @Column(name = "contract_expiration_date")
    private Date contractExpirationDate;

}
