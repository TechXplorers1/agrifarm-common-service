const fs = require('fs');
const paths = [
    "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\repository\\BookingRepository.java",
    "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\repository\\EquipmentRepository.java",
    "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\repository\\TransportVehicleRepository.java",
    "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\repository\\ServiceOfferingRepository.java",
    "c:\\Users\\csc\\Desktop\\Agri Farms\\agrifarm-common-service\\src\\main\\java\\com\\agrifarms\\common\\repository\\WorkerGroupRepository.java"
];

// BookingRepository
let content = fs.readFileSync(paths[0], 'utf8');
content = content.replace(/List<Booking> findByProviderId\(String providerId\);/, "List<Booking> findByProviderId(String providerId);\n    long countByProviderIdAndStatusIn(String providerId, java.util.Collection<String> statuses);");
fs.writeFileSync(paths[0], content);

// EquipmentRepository
content = fs.readFileSync(paths[1], 'utf8');
content = content.replace(/public interface EquipmentRepository extends JpaRepository<Equipment, String> {/, "public interface EquipmentRepository extends JpaRepository<Equipment, String> {\n    long countByOwnerId(String ownerId);");
fs.writeFileSync(paths[1], content);

// TransportVehicleRepository
content = fs.readFileSync(paths[2], 'utf8');
content = content.replace(/public interface TransportVehicleRepository extends JpaRepository<TransportVehicle, String> {/, "public interface TransportVehicleRepository extends JpaRepository<TransportVehicle, String> {\n    long countByOwnerId(String ownerId);");
fs.writeFileSync(paths[2], content);

// ServiceOfferingRepository
content = fs.readFileSync(paths[3], 'utf8');
content = content.replace(/public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, String> {/, "public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, String> {\n    long countByOwnerId(String ownerId);");
fs.writeFileSync(paths[3], content);

// WorkerGroupRepository
content = fs.readFileSync(paths[4], 'utf8');
content = content.replace(/public interface WorkerGroupRepository extends JpaRepository<WorkerGroup, String> {/, "public interface WorkerGroupRepository extends JpaRepository<WorkerGroup, String> {\n    long countByOwnerId(String ownerId);");
fs.writeFileSync(paths[4], content);
