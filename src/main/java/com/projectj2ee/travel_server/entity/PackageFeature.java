package com.projectj2ee.travel_server.entity;

import com.projectj2ee.travel_server.dto.Ids.PackageActivityIds;
import com.projectj2ee.travel_server.dto.Ids.PackageFeatureIds;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PACKAGE)
@Table(name = "package_features")
public class PackageFeature {
  @EmbeddedId
  PackageFeatureIds id;

}
