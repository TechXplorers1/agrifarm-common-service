const fs = require('fs');

const mapperPath = "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\dto\\DtoMapper.java";
let content = fs.readFileSync(mapperPath, 'utf8');

// Entities to fix
const entities = ['Equipment', 'TransportVehicle', 'ServiceOffering', 'WorkerGroup'];

entities.forEach(entity => {
    // 1. Fix constructor calls (toDTO) - Add latitude, longitude at the end before the closing parenthesis
    // Find the return new ...DTO(...) block
    const dtoName = entity === 'ServiceOffering' ? 'ServiceOfferingDTO' : (entity + 'DTO');
    const constructorRegex = new RegExp(`return new ${dtoName}\\(`, 'g');
    
    // We need to find where the constructor call ends.
    // It usually ends with entity.getLongitude()); or entity.getImageUrl());
    content = content.replace(
        new RegExp(`entity\\.getImageUrl\\(\\)\\);`, 'g'),
        `entity.getImageUrl(),\n                entity.getLatitude(),\n                entity.getLongitude());`
    );

    // 2. Fix entity setters (toEntity) - Add latitude, longitude
    const entitySetterRegex = new RegExp(`entity\\.setImageUrl\\(dto\\.getImageUrl\\(\\)\\);`, 'g');
    content = content.replace(
        entitySetterRegex,
        `entity.setImageUrl(dto.getImageUrl());\n        entity.setLatitude(dto.getLatitude());\n        entity.setLongitude(dto.getLongitude());`
    );
});

// WorkerGroup is special because it has roles
content = content.replace(
    `entity.getImageUrl(),\n                null,\n                entity.getLatitude(),\n                entity.getLongitude());`,
    `entity.getImageUrl(),\n                null,\n                entity.getLatitude(),\n                entity.getLongitude());` // already handled by previous or needs specific fix
);

fs.writeFileSync(mapperPath, content);
