const fs = require('fs');

const mapperPath = "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\DtoMapper.java";
let content = fs.readFileSync(mapperPath, 'utf8');

// List of entities to update in DtoMapper
const entities = ['Equipment', 'TransportVehicle', 'ServiceOffering', 'WorkerGroup'];

entities.forEach(entity => {
    // Update toEntity methods (setter style)
    const setterRegex = new RegExp(`entity\\.setLocation\\(dto\\.getLocation\\(\\)\\);`, 'g');
    content = content.replace(setterRegex, `entity.setLocation(dto.getLocation());\n        entity.setHouseNo(dto.getHouseNo());\n        entity.setStreet(dto.getStreet());\n        entity.setVillage(dto.getVillage());\n        entity.setDistrict(dto.getDistrict());\n        entity.setState(dto.getState());\n        entity.setCountry(dto.getCountry());\n        entity.setPincode(dto.getPincode());`);

    // Update toDTO methods (constructor style)
    // For Equipment, TransportVehicle, ServiceOffering
    if (entity !== 'WorkerGroup') {
        const constructorRegex = new RegExp(`entity\\.getLocation\\(\\),\\s+entity\\.getIsAvailable\\(\\),`, 'g');
        content = content.replace(constructorRegex, `entity.getLocation(),\n                entity.getHouseNo(),\n                entity.getStreet(),\n                entity.getVillage(),\n                entity.getDistrict(),\n                entity.getState(),\n                entity.getCountry(),\n                entity.getPincode(),\n                entity.getIsAvailable(),`);
    } else {
        // WorkerGroup has a slightly different constructor
        content = content.replace(`entity.getLocation(),\n                entity.getServiceRangeKm(),`, `entity.getLocation(),\n                entity.getHouseNo(),\n                entity.getStreet(),\n                entity.getVillage(),\n                entity.getDistrict(),\n                entity.getState(),\n                entity.getCountry(),\n                entity.getPincode(),\n                entity.getServiceRangeKm(),`);
    }
});

fs.writeFileSync(mapperPath, content);
