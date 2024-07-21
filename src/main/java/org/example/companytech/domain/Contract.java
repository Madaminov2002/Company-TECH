package org.example.companytech.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    private String jshshir;

    @Column(name = "contract_number", nullable = false)
    private String contractNumber;

    @Column(name = "contract_date", nullable = false)
    private Date contractDate;

    @Column(name = "contract_expiration_date")
    private Date contractExpirationDate;

}
