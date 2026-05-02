const fs = require('fs');
const path = require('path');

const file = 'c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\DtoMapper.java';
let content = fs.readFileSync(file, 'utf8');

// constructor replacement
content = content.replace(/entity\.getVillage\(\),/g, 'entity.getVillage(),\n                entity.getHouseNo(),\n                entity.getStreet(),\n                entity.getState(),\n                entity.getCountry(),\n                entity.getPincode(),');

// setter replacement
content = content.replace(/entity\.setVillage\(dto\.getVillage\(\)\);/g, 'entity.setVillage(dto.getVillage());\n        entity.setHouseNo(dto.getHouseNo());\n        entity.setStreet(dto.getStreet());\n        entity.setState(dto.getState());\n        entity.setCountry(dto.getCountry());\n        entity.setPincode(dto.getPincode());');

fs.writeFileSync(file, content);
console.log('Fixed DtoMapper.java for user address fields');
