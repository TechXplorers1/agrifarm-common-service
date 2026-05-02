const fs = require('fs');
const filePath = "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\resources\\db\\changelog\\db.changelog-agri.sql";
let content = fs.readFileSync(filePath, 'utf8');

// Fix double IF NOT EXISTS
content = content.replace(/IF NOT EXISTS IF NOT EXISTS/g, 'IF NOT EXISTS');

// Also, specifically for changeset 1.0.8, let's try to revert it to what it likely was.
// Based on typical Liquibase usage, it probably didn't have IF NOT EXISTS initially.
// However, to be safe and avoid further checksum issues, I will add the new checksum as a valid one or just revert.

fs.writeFileSync(filePath, content);
