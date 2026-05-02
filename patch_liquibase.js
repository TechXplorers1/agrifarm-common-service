const fs = require('fs');
const filePath = "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\resources\\db\\changelog\\db.changelog-agri.sql";
let content = fs.readFileSync(filePath, 'utf8');

// Replace ADD COLUMN with ADD COLUMN IF NOT EXISTS
content = content.replace(/ADD COLUMN/g, 'ADD COLUMN IF NOT EXISTS');

fs.writeFileSync(filePath, content);
