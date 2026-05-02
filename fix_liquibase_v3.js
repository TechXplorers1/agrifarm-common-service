const fs = require('fs');
const filePath = "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\resources\\db\\changelog\\db.changelog-agri.sql";
let content = fs.readFileSync(filePath, 'utf8');

// Revert all ADD COLUMN IF NOT EXISTS to ADD COLUMN
content = content.replace(/ADD COLUMN IF NOT EXISTS/g, 'ADD COLUMN');

// Remove any validCheckSum comments I added
content = content.replace(/validCheckSum:.*\n/g, '');

// Now manually add IF NOT EXISTS ONLY to changeset 1.0.13
// Changeset 1.0.13 starts after changeset agrihub:1.0.13
const parts = content.split('-- changeset agrihub:1.0.13');
if (parts.length > 1) {
    parts[1] = parts[1].replace(/ADD COLUMN/g, 'ADD COLUMN IF NOT EXISTS');
    content = parts.join('-- changeset agrihub:1.0.13');
}

fs.writeFileSync(filePath, content);
