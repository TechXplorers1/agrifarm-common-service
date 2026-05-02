const fs = require('fs');
const path = require('path');

const baseDir = 'c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common';
const files = [
    'entity/User.java', 'entity/Equipment.java', 'entity/TransportVehicle.java', 'entity/ServiceOffering.java', 'entity/WorkerGroup.java',
    'dto/UserDTO.java', 'dto/EquipmentDTO.java', 'dto/TransportVehicleDTO.java', 'dto/ServiceOfferingDTO.java', 'dto/WorkerGroupDTO.java'
];

files.forEach(file => {
    const fullPath = path.join(baseDir, file);
    let content = fs.readFileSync(fullPath, 'utf8');
    
    if (!content.includes('private BigDecimal latitude;')) {
        if (!content.includes('java.math.BigDecimal')) {
            content = content.replace(/import\s+(.*?);/, 'import java.math.BigDecimal;\nimport ;');
        }
        
        const lastBraceIndex = content.lastIndexOf('}');
        if (lastBraceIndex !== -1) {
            content = content.substring(0, lastBraceIndex) + 
                     '    private BigDecimal latitude;\n    private BigDecimal longitude;\n}\n';
            fs.writeFileSync(fullPath, content);
            console.log('Updated ' + file);
        }
    }
});
