const fs = require('fs');
const filePath = "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\controller\\InventoryController.java";
let content = fs.readFileSync(filePath, 'utf8');

const updateLogic = `
        if (dto.getHouseNo() != null) entity.setHouseNo(dto.getHouseNo());
        if (dto.getStreet() != null) entity.setStreet(dto.getStreet());
        if (dto.getVillage() != null) entity.setVillage(dto.getVillage());
        if (dto.getDistrict() != null) entity.setDistrict(dto.getDistrict());
        if (dto.getState() != null) entity.setState(dto.getState());
        if (dto.getCountry() != null) entity.setCountry(dto.getCountry());
        if (dto.getPincode() != null) entity.setPincode(dto.getPincode());
        if (dto.getLatitude() != null) entity.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null) entity.setLongitude(dto.getLongitude());`;

// Equipment
content = content.replace(/if \(equipmentDTO\.getImageUrl\(\) != null\) existingEquipment\.setImageUrl\(equipmentDTO\.getImageUrl\(\)\);/g, 
    `if (equipmentDTO.getImageUrl() != null) existingEquipment.setImageUrl(equipmentDTO.getImageUrl());` + updateLogic.replace(/dto/g, 'equipmentDTO').replace(/entity/g, 'existingEquipment'));

// Vehicle
content = content.replace(/if \(vehicleDTO\.getImageUrl\(\) != null\) existingVehicle\.setImageUrl\(vehicleDTO\.getImageUrl\(\)\);/g, 
    `if (vehicleDTO.getImageUrl() != null) existingVehicle.setImageUrl(vehicleDTO.getImageUrl());` + updateLogic.replace(/dto/g, 'vehicleDTO').replace(/entity/g, 'existingVehicle'));

// Service
content = content.replace(/if \(serviceDTO\.getImageUrl\(\) != null\) existingService\.setImageUrl\(serviceDTO\.getImageUrl\(\)\);/g, 
    `if (serviceDTO.getImageUrl() != null) existingService.setImageUrl(serviceDTO.getImageUrl());` + updateLogic.replace(/dto/g, 'serviceDTO').replace(/entity/g, 'existingService'));

// WorkerGroup
content = content.replace(/if \(groupDTO\.getImageUrl\(\) != null\) existingGroup\.setImageUrl\(groupDTO\.getImageUrl\(\)\);/g, 
    `if (groupDTO.getImageUrl() != null) existingGroup.setImageUrl(groupDTO.getImageUrl());` + updateLogic.replace(/dto/g, 'groupDTO').replace(/entity/g, 'existingGroup'));

fs.writeFileSync(filePath, content);
