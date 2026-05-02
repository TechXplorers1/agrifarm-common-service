const fs = require('fs');

const mapperPath = "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\DtoMapper.java";
let content = fs.readFileSync(mapperPath, 'utf8');

// Define replacements carefully
const addressFields = `
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());`;

const addressGetters = `
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getVillage(),
                entity.getDistrict(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),`;

// Equipment
content = content.replace(
    /entity\.getOperatorAvailable\(\),\s+entity\.getLocation\(\),\s+entity\.getIsAvailable\(\)/,
    `entity.getOperatorAvailable(),\n                entity.getLocation(),${addressGetters}\n                entity.getIsAvailable()`
);
content = content.replace(
    /entity\.setLocation\(dto\.getLocation\(\)\);\s+entity\.setIsAvailable/,
    `entity.setLocation(dto.getLocation());${addressFields}\n        entity.setIsAvailable`
);

// TransportVehicle
content = content.replace(
    /entity\.getServiceArea\(\),\s+entity\.getLocation\(\),\s+entity\.getIsAvailable\(\)/,
    `entity.getServiceArea(),\n                entity.getLocation(),${addressGetters}\n                entity.getIsAvailable()`
);
content = content.replace(
    /entity\.setServiceArea\(dto\.getServiceArea\(\)\);\s+entity\.setLocation\(dto\.getLocation\(\)\);\s+entity\.setIsAvailable/,
    `entity.setServiceArea(dto.getServiceArea());\n        entity.setLocation(dto.getLocation());${addressFields}\n        entity.setIsAvailable`
);

// ServiceOffering
content = content.replace(
    /entity\.getOperatorIncluded\(\),\s+entity\.getLocation\(\),\s+entity\.getIsAvailable\(\)/,
    `entity.getOperatorIncluded(),\n                entity.getLocation(),${addressGetters}\n                entity.getIsAvailable()`
);
content = content.replace(
    /entity\.setOperatorIncluded\(dto\.getOperatorIncluded\(\)\);\s+entity\.setLocation\(dto\.getLocation\(\)\);\s+entity\.setIsAvailable/,
    `entity.setOperatorIncluded(dto.getOperatorIncluded());\n        entity.setLocation(dto.getLocation());${addressFields}\n        entity.setIsAvailable`
);

// WorkerGroup
content = content.replace(
    /entity\.getSkills\(\),\s+entity\.getLocation\(\),\s+entity\.getServiceRangeKm\(\)/,
    `entity.getSkills(),\n                entity.getLocation(),${addressGetters}\n                entity.getServiceRangeKm()`
);
content = content.replace(
    /entity\.setSkills\(dto\.getSkills\(\)\);\s+entity\.setLocation\(dto\.getLocation\(\)\);\s+entity\.setServiceRangeKm/,
    `entity.setSkills(dto.getSkills());\n        entity.setLocation(dto.getLocation());${addressFields}\n        entity.setServiceRangeKm`
);

fs.writeFileSync(mapperPath, content);
