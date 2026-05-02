const fs = require('fs');
const path = require('path');

const file = 'c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\DtoMapper.java';
let content = fs.readFileSync(file, 'utf8');

content = content.replace(/entity\.getImageUrl\(\),\n\s*entity\.getLatitude\(\),\n\s*entity\.getLongitude\(\),\n\s*null\);/g, 'entity.getImageUrl(),\n                null,\n                entity.getLatitude(),\n                entity.getLongitude());');

fs.writeFileSync(file, content);
console.log('Fixed WorkerGroupDTO constructor');
