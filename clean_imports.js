const fs = require('fs');

function cleanImports(filePath) {
    let content = fs.readFileSync(filePath, 'utf8');
    const lines = content.split('\n');
    const seen = new Set();
    const newLines = [];
    for (const line of lines) {
        if (line.trim().startsWith('import ')) {
            if (seen.has(line.trim())) continue;
            seen.add(line.trim());
        }
        newLines.push(line);
    }
    fs.writeFileSync(filePath, newLines.join('\n'));
}

cleanImports("c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\UserDTO.java");
cleanImports("c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\entity\\User.java");
