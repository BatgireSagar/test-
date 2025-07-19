package com.example.demo;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientType;
    private String jurisdiction;
    private String products;

    @OneToMany(mappedBy = "kycProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KycDocument> submittedDocs;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "kyc_profile_extracted_data", joinColumns = @JoinColumn(name = "kyc_profile_id"))
    @MapKeyColumn(name = "field")
    @Column(name = "value")
    private Map<String, String> extractedData;
}