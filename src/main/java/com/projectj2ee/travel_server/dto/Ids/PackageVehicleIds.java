package com.projectj2ee.travel_server.dto.Ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PackageVehicleIds implements Serializable {
    @Column(name = "package_id")
    int packageId;

    @Column(name = "vehicle_id")
    int vehicleId;
}
