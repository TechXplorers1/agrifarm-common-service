const fs = require('fs');

const mapperPath = "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\DtoMapper.java";
let content = fs.readFileSync(mapperPath, 'utf8');

// Replacement 1: toEquipmentDTO
content = content.replace(
`                entity.getOperatorAvailable(),
                entity.getLocation(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                entity.getLatitude(),
                entity.getLongitude());`,
`                entity.getOperatorAvailable(),
                entity.getLocation(),
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getVillage(),
                entity.getDistrict(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                entity.getLatitude(),
                entity.getLongitude());`
);

// Replacement 2: toEquipmentEntity
content = content.replace(
`        entity.setLocation(dto.getLocation());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());`,
`        entity.setLocation(dto.getLocation());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());`
);

// Replacement 3: toTransportVehicleDTO
content = content.replace(
`                entity.getServiceArea(),
                entity.getLocation(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                entity.getLatitude(),
                entity.getLongitude());`,
`                entity.getServiceArea(),
                entity.getLocation(),
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getVillage(),
                entity.getDistrict(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                entity.getLatitude(),
                entity.getLongitude());`
);

// Replacement 4: toTransportVehicleEntity
content = content.replace(
`        entity.setServiceArea(dto.getServiceArea());
        entity.setLocation(dto.getLocation());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());`,
`        entity.setServiceArea(dto.getServiceArea());
        entity.setLocation(dto.getLocation());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());`
);

// Replacement 5: toServiceOfferingDTO
content = content.replace(
`                entity.getOperatorIncluded(),
                entity.getLocation(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                entity.getLatitude(),
                entity.getLongitude());`,
`                entity.getOperatorIncluded(),
                entity.getLocation(),
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getVillage(),
                entity.getDistrict(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                entity.getLatitude(),
                entity.getLongitude());`
);

// Replacement 6: toServiceOfferingEntity
content = content.replace(
`        entity.setOperatorIncluded(dto.getOperatorIncluded());
        entity.setLocation(dto.getLocation());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());`,
`        entity.setOperatorIncluded(dto.getOperatorIncluded());
        entity.setLocation(dto.getLocation());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());`
);

// Replacement 7: toWorkerGroupDTO
content = content.replace(
`                entity.getSkills(),
                entity.getLocation(),
                entity.getServiceRangeKm(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                null,
                entity.getLatitude(),
                entity.getLongitude());`,
`                entity.getSkills(),
                entity.getLocation(),
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getVillage(),
                entity.getDistrict(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),
                entity.getServiceRangeKm(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                null,
                entity.getLatitude(),
                entity.getLongitude());`
);

// Replacement 8: toWorkerGroupEntity
content = content.replace(
`        entity.setSkills(dto.getSkills());
        entity.setLocation(dto.getLocation());
        entity.setServiceRangeKm(dto.getServiceRangeKm());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());`,
`        entity.setSkills(dto.getSkills());
        entity.setLocation(dto.getLocation());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setServiceRangeKm(dto.getServiceRangeKm());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());`
);

fs.writeFileSync(mapperPath, content);
