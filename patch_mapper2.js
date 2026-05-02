const fs = require('fs');
const path = require('path');

const file = 'c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\DtoMapper.java';
let content = fs.readFileSync(file, 'utf8');

// Update toDTO calls that end with getProfileImageUrl() or getImageUrl()
content = content.replace(/entity\.getProfileImageUrl\(\)\);/g, 'entity.getProfileImageUrl(),\n                entity.getLatitude(),\n                entity.getLongitude());');

// EquipmentDTO, TransportVehicleDTO, ServiceOfferingDTO end with getImageUrl()
content = content.replace(/entity\.getImageUrl\(\)\);/g, 'entity.getImageUrl(),\n                entity.getLatitude(),\n                entity.getLongitude());');

// toEntity calls end with setProfileImageUrl or setImageUrl
content = content.replace(/entity\.setProfileImageUrl\(dto\.getProfileImageUrl\(\)\);/g, 'entity.setProfileImageUrl(dto.getProfileImageUrl());\n        entity.setLatitude(dto.getLatitude());\n        entity.setLongitude(dto.getLongitude());');

content = content.replace(/entity\.setImageUrl\(dto\.getImageUrl\(\)\);/g, 'entity.setImageUrl(dto.getImageUrl());\n        entity.setLatitude(dto.getLatitude());\n        entity.setLongitude(dto.getLongitude());');

// WorkerGroupDTO constructor is manual:
// new WorkerGroupDTO( ..., entity.getImageUrl(), null);
content = content.replace(/entity\.getImageUrl\(\),\n\s*null\);/g, 'entity.getImageUrl(),\n                entity.getLatitude(),\n                entity.getLongitude(),\n                null);');

fs.writeFileSync(file, content);
console.log('Updated DtoMapper');
