const fs = require('fs');
const filePath = "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\DtoMapper.java";
let content = fs.readFileSync(filePath, 'utf8');

// Update toEquipmentDTO
content = content.replace(/return new EquipmentDTO\([\s\S]*?entity\.getImageUrl\(\)\);/g, 
`return new EquipmentDTO(
                entity.getEquipmentId(),
                entity.getOwnerId(),
                ownerName,
                entity.getCategory(),
                entity.getBrandModel(),
                entity.getConditionStatus(),
                entity.getPricePerHour(),
                entity.getOperatorAvailable(),
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
                entity.getLongitude());`);

// Update toEquipmentEntity
content = content.replace(/entity\.setImageUrl\(dto\.getImageUrl\(\)\);\s*return entity;/g,
`entity.setImageUrl(dto.getImageUrl());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;`);

// Update toTransportVehicleDTO
content = content.replace(/return new TransportVehicleDTO\([\s\S]*?entity\.getImageUrl\(\)\);/g,
`return new TransportVehicleDTO(
                entity.getVehicleId(),
                entity.getOwnerId(),
                ownerName,
                entity.getVehicleType(),
                entity.getVehicleNumber(),
                entity.getLoadCapacity(),
                entity.getPricePerKmOrTrip(),
                entity.getDriverIncluded(),
                entity.getServiceArea(),
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
                entity.getLongitude());`);

// Update toTransportVehicleEntity
content = content.replace(/entity\.setApprovalStatus\(dto\.getApprovalStatus\(\)\);\s*entity\.setImageUrl\(dto\.getImageUrl\(\)\);\s*return entity;/g,
`entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;`);

// Update toServiceOfferingDTO
content = content.replace(/return new ServiceOfferingDTO\([\s\S]*?entity\.getImageUrl\(\)\);/g,
`return new ServiceOfferingDTO(
                entity.getServiceId(),
                entity.getOwnerId(),
                ownerName,
                entity.getServiceType(),
                entity.getBusinessName(),
                entity.getDescription(),
                entity.getEquipmentUsed(),
                entity.getPriceRate(),
                entity.getOperatorIncluded(),
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
                entity.getLongitude());`);

// Update toServiceOfferingEntity
content = content.replace(/entity\.setApprovalStatus\(dto\.getApprovalStatus\(\)\);\s*entity\.setImageUrl\(dto\.getImageUrl\(\)\);\s*return entity;/g,
`entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;`);

// Update toWorkerGroupDTO
// WorkerGroupDTO constructor also changed
content = content.replace(/WorkerGroupDTO dto = new WorkerGroupDTO\([\s\S]*?null\);/g,
`WorkerGroupDTO dto = new WorkerGroupDTO(
                entity.getGroupId(),
                entity.getOwnerId(),
                entity.getGroupName(),
                entity.getMaleCount(),
                entity.getFemaleCount(),
                entity.getPricePerMale(),
                entity.getPricePerFemale(),
                entity.getSkills(),
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
                entity.getLatitude(),
                entity.getLongitude(),
                null);`);

// Update toWorkerGroupEntity
content = content.replace(/entity\.setImageUrl\(dto\.getImageUrl\(\)\);/g,
`entity.setImageUrl(dto.getImageUrl());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());`);

fs.writeFileSync(filePath, content);
