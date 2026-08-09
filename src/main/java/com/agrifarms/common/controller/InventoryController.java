package com.agrifarms.common.controller;

import com.agrifarms.common.dto.DtoMapper;
import com.agrifarms.common.dto.EquipmentDTO;
import com.agrifarms.common.dto.ServiceOfferingDTO;
import com.agrifarms.common.dto.TransportVehicleDTO;
import com.agrifarms.common.dto.WorkerGroupDTO;
import com.agrifarms.common.entity.Equipment;
import com.agrifarms.common.entity.ServiceOffering;
import com.agrifarms.common.entity.TransportVehicle;
import com.agrifarms.common.entity.WorkerGroup;
import com.agrifarms.common.service.InventoryService;
import com.agrifarms.common.service.NotificationService;
import com.agrifarms.common.service.UserService;
import com.agrifarms.common.entity.Skill;
import com.agrifarms.common.repository.SkillRepository;
import com.agrifarms.common.entity.VehicleCategory;
import com.agrifarms.common.repository.VehicleCategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*") // Allow Flutter to access
public class InventoryController {

    private final InventoryService inventoryService;
    private final DtoMapper dtoMapper;
    private final NotificationService notificationService;
    private final SkillRepository skillRepository;
    private final VehicleCategoryRepository vehicleCategoryRepository;
    private final UserService userService;

    public InventoryController(InventoryService inventoryService, DtoMapper dtoMapper, NotificationService notificationService, SkillRepository skillRepository, VehicleCategoryRepository vehicleCategoryRepository, UserService userService) {
        this.inventoryService = inventoryService;
        this.dtoMapper = dtoMapper;
        this.notificationService = notificationService;
        this.skillRepository = skillRepository;
        this.vehicleCategoryRepository = vehicleCategoryRepository;
        this.userService = userService;
    }

    // Equipment
    @GetMapping("/equipment")
    public List<EquipmentDTO> getEquipment(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String ownerId) {
        List<Equipment> equipmentList;
        if (ownerId != null && !ownerId.isEmpty()) {
            equipmentList = inventoryService.getEquipmentByOwnerId(ownerId);
        } else if (category != null && !category.isEmpty()) {
            equipmentList = inventoryService.getEquipmentByCategory(category);
        } else {
            equipmentList = inventoryService.getAllEquipment();
        }
        return equipmentList.stream()
                .map(dtoMapper::toEquipmentDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/equipment")
    public EquipmentDTO addEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = dtoMapper.toEquipmentEntity(equipmentDTO);
        Equipment savedEquipment = inventoryService.saveEquipment(equipment);

        String equipName = savedEquipment.getBrandModel() != null ? savedEquipment.getBrandModel()
                : (savedEquipment.getCategory() != null ? savedEquipment.getCategory() : "equipment");
        notificationService.sendTopicNotification(
                "all_assets",
                "New Equipment Available!",
                "A new " + equipName + " was just added to the platform.",
                null
        );

        return dtoMapper.toEquipmentDTO(savedEquipment);
    }

    @PutMapping("/equipment/{id}")
    public EquipmentDTO updateEquipment(@PathVariable("id") String id, @RequestBody EquipmentDTO equipmentDTO) {
        Equipment existingEquipment = inventoryService.getAllEquipment().stream()
                .filter(e -> e.getEquipmentId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        String oldStatus = existingEquipment.getApprovalStatus();

        // Update fields from DTO
        if (equipmentDTO.getCategory() != null) existingEquipment.setCategory(equipmentDTO.getCategory());
        if (equipmentDTO.getBrand() != null) existingEquipment.setBrand(equipmentDTO.getBrand());
        if (equipmentDTO.getModel() != null) existingEquipment.setModel(equipmentDTO.getModel());
        if (equipmentDTO.getDescription() != null) existingEquipment.setDescription(equipmentDTO.getDescription());
        if (equipmentDTO.getVehicleNumber() != null) existingEquipment.setVehicleNumber(equipmentDTO.getVehicleNumber());
        if (equipmentDTO.getOwnerBusinessName() != null) existingEquipment.setOwnerBusinessName(equipmentDTO.getOwnerBusinessName());
        
        if (equipmentDTO.getBrand() != null && equipmentDTO.getModel() != null) {
            existingEquipment.setBrandModel(equipmentDTO.getBrand() + " " + equipmentDTO.getModel());
        } else if (equipmentDTO.getBrandModel() != null) {
            existingEquipment.setBrandModel(equipmentDTO.getBrandModel());
        }

        if (equipmentDTO.getConditionStatus() != null) existingEquipment.setConditionStatus(equipmentDTO.getConditionStatus());
        if (equipmentDTO.getPricePerHour() != null) existingEquipment.setPricePerHour(equipmentDTO.getPricePerHour());
        if (equipmentDTO.getPricePerHalfDay() != null) existingEquipment.setPricePerHalfDay(equipmentDTO.getPricePerHalfDay());
        if (equipmentDTO.getOperatorAvailable() != null) existingEquipment.setOperatorAvailable(equipmentDTO.getOperatorAvailable());
        if (equipmentDTO.getOperatorPrice() != null) existingEquipment.setOperatorPrice(equipmentDTO.getOperatorPrice());
        if (equipmentDTO.getLocation() != null) existingEquipment.setLocation(equipmentDTO.getLocation());
        if (equipmentDTO.getIsAvailable() != null) existingEquipment.setIsAvailable(equipmentDTO.getIsAvailable());
        if (equipmentDTO.getRating() != null) existingEquipment.setRating(equipmentDTO.getRating());
        if (equipmentDTO.getApprovalStatus() != null) existingEquipment.setApprovalStatus(equipmentDTO.getApprovalStatus());
        if (equipmentDTO.getImageUrl() != null) existingEquipment.setImageUrl(equipmentDTO.getImageUrl());
        if (equipmentDTO.getHouseNo() != null) existingEquipment.setHouseNo(equipmentDTO.getHouseNo());
        if (equipmentDTO.getStreet() != null) existingEquipment.setStreet(equipmentDTO.getStreet());
        if (equipmentDTO.getVillage() != null) existingEquipment.setVillage(equipmentDTO.getVillage());
        if (equipmentDTO.getDistrict() != null) existingEquipment.setDistrict(equipmentDTO.getDistrict());
        if (equipmentDTO.getState() != null) existingEquipment.setState(equipmentDTO.getState());
        if (equipmentDTO.getCountry() != null) existingEquipment.setCountry(equipmentDTO.getCountry());
        if (equipmentDTO.getPincode() != null) existingEquipment.setPincode(equipmentDTO.getPincode());
        if (equipmentDTO.getLatitude() != null) existingEquipment.setLatitude(equipmentDTO.getLatitude());
        if (equipmentDTO.getLongitude() != null) existingEquipment.setLongitude(equipmentDTO.getLongitude());
        if (equipmentDTO.getAttachedEquipments() != null) existingEquipment.setAttachedEquipments(equipmentDTO.getAttachedEquipments());


        Equipment savedEquipment = inventoryService.saveEquipment(existingEquipment);
        checkAndNotifyApprovalStatus(savedEquipment.getOwnerId(), oldStatus, savedEquipment.getApprovalStatus(), savedEquipment.getBrandModel(), savedEquipment.getEquipmentId(), "Equipment");
        return dtoMapper.toEquipmentDTO(savedEquipment);
    }

    @DeleteMapping("/equipment/{id}")
    public void deleteEquipment(@PathVariable("id") String id) {
        inventoryService.deleteEquipment(id);
    }

    // Vehicles
    @GetMapping("/vehicles")
    public List<TransportVehicleDTO> getVehicles(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String ownerId) {
        List<TransportVehicle> vehicleList;
        if (ownerId != null && !ownerId.isEmpty()) {
            vehicleList = inventoryService.getVehiclesByOwnerId(ownerId);
        } else if (type != null && !type.isEmpty()) {
            vehicleList = inventoryService.getVehiclesByType(type);
        } else {
            vehicleList = inventoryService.getAllVehicles();
        }
        return vehicleList.stream()
                .map(dtoMapper::toTransportVehicleDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/vehicles")
    public TransportVehicleDTO addVehicle(@RequestBody TransportVehicleDTO vehicleDTO) {
        TransportVehicle vehicle = dtoMapper.toTransportVehicleEntity(vehicleDTO);
        TransportVehicle savedVehicle = inventoryService.saveVehicle(vehicle);

        String vehName = savedVehicle.getVehicleType() != null ? savedVehicle.getVehicleType() : "vehicle";
        notificationService.sendTopicNotification(
                "all_assets",
                "New Transport Vehicle Available!",
                "A new transport vehicle (" + vehName + ") was just added to the platform.",
                null
        );

        return dtoMapper.toTransportVehicleDTO(savedVehicle);
    }

    @PutMapping("/vehicles/{id}")
    public TransportVehicleDTO updateVehicle(@PathVariable("id") String id, @RequestBody TransportVehicleDTO vehicleDTO) {
        TransportVehicle existingVehicle = inventoryService.getAllVehicles().stream()
                .filter(v -> v.getVehicleId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        String oldStatus = existingVehicle.getApprovalStatus();

        if (vehicleDTO.getVehicleType() != null) {
            existingVehicle.setVehicleType(vehicleDTO.getVehicleType());
        }
        if (vehicleDTO.getVehicleNumber() != null) {
            existingVehicle.setVehicleNumber(vehicleDTO.getVehicleNumber());
        }
        if (vehicleDTO.getLoadCapacity() != null) {
            existingVehicle.setLoadCapacity(vehicleDTO.getLoadCapacity());
        }
        if (vehicleDTO.getPricePerKmOrTrip() != null) {
            existingVehicle.setPricePerKmOrTrip(vehicleDTO.getPricePerKmOrTrip());
        }
        if (vehicleDTO.getDriverIncluded() != null) {
            existingVehicle.setDriverIncluded(vehicleDTO.getDriverIncluded());
        }
        if (vehicleDTO.getOperatorPrice() != null) {
            existingVehicle.setOperatorPrice(vehicleDTO.getOperatorPrice());
        }
        if (vehicleDTO.getServiceArea() != null) {
            existingVehicle.setServiceArea(vehicleDTO.getServiceArea());
        }
        if (vehicleDTO.getLocation() != null) {
            existingVehicle.setLocation(vehicleDTO.getLocation());
        }
        if (vehicleDTO.getIsAvailable() != null) {
            existingVehicle.setIsAvailable(vehicleDTO.getIsAvailable());
        }
        if (vehicleDTO.getRating() != null) {
            existingVehicle.setRating(vehicleDTO.getRating());
        }
        if (vehicleDTO.getApprovalStatus() != null) {
            existingVehicle.setApprovalStatus(vehicleDTO.getApprovalStatus());
        }
        if (vehicleDTO.getImageUrl() != null) {
            existingVehicle.setImageUrl(vehicleDTO.getImageUrl());
        }
        if (vehicleDTO.getHouseNo() != null) {
            existingVehicle.setHouseNo(vehicleDTO.getHouseNo());
        }
        if (vehicleDTO.getStreet() != null) {
            existingVehicle.setStreet(vehicleDTO.getStreet());
        }
        if (vehicleDTO.getVillage() != null) {
            existingVehicle.setVillage(vehicleDTO.getVillage());
        }
        if (vehicleDTO.getDistrict() != null) {
            existingVehicle.setDistrict(vehicleDTO.getDistrict());
        }
        if (vehicleDTO.getState() != null) {
            existingVehicle.setState(vehicleDTO.getState());
        }
        if (vehicleDTO.getCountry() != null) {
            existingVehicle.setCountry(vehicleDTO.getCountry());
        }
        if (vehicleDTO.getPincode() != null) {
            existingVehicle.setPincode(vehicleDTO.getPincode());
        }
        if (vehicleDTO.getLatitude() != null) {
            existingVehicle.setLatitude(vehicleDTO.getLatitude());
        }
        if (vehicleDTO.getLongitude() != null) {
            existingVehicle.setLongitude(vehicleDTO.getLongitude());
        }
        if (vehicleDTO.getOwnerBusinessName() != null) {
            existingVehicle.setOwnerBusinessName(vehicleDTO.getOwnerBusinessName());
        }
        if (vehicleDTO.getBrand() != null) {
            existingVehicle.setBrand(vehicleDTO.getBrand());
        }
        if (vehicleDTO.getModel() != null) {
            existingVehicle.setModel(vehicleDTO.getModel());
        }
        if (vehicleDTO.getYearOfManufacture() != null) {
            existingVehicle.setYearOfManufacture(vehicleDTO.getYearOfManufacture());
        }
        if (vehicleDTO.getPricePerKm() != null) {
            existingVehicle.setPricePerKm(vehicleDTO.getPricePerKm());
        }
        if (vehicleDTO.getPricePerHour() != null) {
            existingVehicle.setPricePerHour(vehicleDTO.getPricePerHour());
        }
        if (vehicleDTO.getVehicleCondition() != null) {
            existingVehicle.setVehicleCondition(vehicleDTO.getVehicleCondition());
        }

        TransportVehicle savedVehicle = inventoryService.saveVehicle(existingVehicle);
        checkAndNotifyApprovalStatus(savedVehicle.getOwnerId(), oldStatus, savedVehicle.getApprovalStatus(), savedVehicle.getVehicleType(), savedVehicle.getVehicleId(), "Transport Vehicle");
        return dtoMapper.toTransportVehicleDTO(savedVehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    public void deleteVehicle(@PathVariable("id") String id) {
        inventoryService.deleteVehicle(id);
    }

    // Services
    @GetMapping("/services")
    public List<ServiceOfferingDTO> getServices(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String ownerId) {
        List<ServiceOffering> serviceList;
        if (ownerId != null && !ownerId.isEmpty()) {
            serviceList = inventoryService.getServicesByOwnerId(ownerId);
        } else if (type != null && !type.isEmpty()) {
            serviceList = inventoryService.getServicesByType(type);
        } else {
            serviceList = inventoryService.getAllServices();
        }
        return serviceList.stream()
                .map(dtoMapper::toServiceOfferingDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/services")
    public ServiceOfferingDTO addService(@RequestBody ServiceOfferingDTO serviceDTO) {
        ServiceOffering service = dtoMapper.toServiceOfferingEntity(serviceDTO);
        ServiceOffering savedService = inventoryService.saveService(service);

        String servName = savedService.getBusinessName() != null ? savedService.getBusinessName()
                : (savedService.getServiceType() != null ? savedService.getServiceType() : "service");
        notificationService.sendTopicNotification(
                "all_assets",
                "New Service Offering Available!",
                "A new service offered by " + servName + " was just added to the platform.",
                null
        );

        return dtoMapper.toServiceOfferingDTO(savedService);
    }

    @PutMapping("/services/{id}")
    public ServiceOfferingDTO updateService(@PathVariable("id") String id, @RequestBody ServiceOfferingDTO serviceDTO) {
        ServiceOffering existingService = inventoryService.getAllServices().stream()
                .filter(s -> s.getServiceId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service not found"));

        String oldStatus = existingService.getApprovalStatus();

        if (serviceDTO.getServiceType() != null) {
            existingService.setServiceType(serviceDTO.getServiceType());
        }
        if (serviceDTO.getBusinessName() != null) {
            existingService.setBusinessName(serviceDTO.getBusinessName());
        }
        if (serviceDTO.getDescription() != null) {
            existingService.setDescription(serviceDTO.getDescription());
        }
        if (serviceDTO.getEquipmentUsed() != null) {
            existingService.setEquipmentUsed(serviceDTO.getEquipmentUsed());
        }
        if (serviceDTO.getPriceRate() != null) {
            existingService.setPriceRate(serviceDTO.getPriceRate());
        }
        if (serviceDTO.getOperatorIncluded() != null) {
            existingService.setOperatorIncluded(serviceDTO.getOperatorIncluded());
        }
        if (serviceDTO.getLocation() != null) {
            existingService.setLocation(serviceDTO.getLocation());
        }
        if (serviceDTO.getIsAvailable() != null) {
            existingService.setIsAvailable(serviceDTO.getIsAvailable());
        }
        if (serviceDTO.getRating() != null) {
            existingService.setRating(serviceDTO.getRating());
        }
        if (serviceDTO.getApprovalStatus() != null) {
            existingService.setApprovalStatus(serviceDTO.getApprovalStatus());
        }
        if (serviceDTO.getImageUrl() != null) {
            existingService.setImageUrl(serviceDTO.getImageUrl());
        }
        if (serviceDTO.getHouseNo() != null) {
            existingService.setHouseNo(serviceDTO.getHouseNo());
        }
        if (serviceDTO.getStreet() != null) {
            existingService.setStreet(serviceDTO.getStreet());
        }
        if (serviceDTO.getVillage() != null) {
            existingService.setVillage(serviceDTO.getVillage());
        }
        if (serviceDTO.getDistrict() != null) {
            existingService.setDistrict(serviceDTO.getDistrict());
        }
        if (serviceDTO.getState() != null) {
            existingService.setState(serviceDTO.getState());
        }
        if (serviceDTO.getCountry() != null) {
            existingService.setCountry(serviceDTO.getCountry());
        }
        if (serviceDTO.getPincode() != null) {
            existingService.setPincode(serviceDTO.getPincode());
        }
        if (serviceDTO.getLatitude() != null) {
            existingService.setLatitude(serviceDTO.getLatitude());
        }
        if (serviceDTO.getLongitude() != null) {
            existingService.setLongitude(serviceDTO.getLongitude());
        }

        ServiceOffering savedService = inventoryService.saveService(existingService);
        checkAndNotifyApprovalStatus(savedService.getOwnerId(), oldStatus, savedService.getApprovalStatus(), savedService.getBusinessName(), savedService.getServiceId(), "Service Offering");
        return dtoMapper.toServiceOfferingDTO(savedService);
    }

    @DeleteMapping("/services/{id}")
    public void deleteService(@PathVariable("id") String id) {
        inventoryService.deleteService(id);
    }

    // Worker Groups
    @GetMapping("/worker-groups")
    public List<WorkerGroupDTO> getWorkerGroups(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String ownerId) {
        List<WorkerGroup> groupList;
        if (ownerId != null && !ownerId.isEmpty()) {
            groupList = inventoryService.getWorkerGroupsByOwnerId(ownerId);
        } else if (location != null && !location.isEmpty()) {
            groupList = inventoryService.getWorkerGroupsByLocation(location);
        } else {
            groupList = inventoryService.getAllWorkerGroups();
        }
        return groupList.stream()
                .map(dtoMapper::toWorkerGroupDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/worker-groups")
    public WorkerGroupDTO addWorkerGroup(@RequestBody WorkerGroupDTO groupDTO) {
        WorkerGroup group = dtoMapper.toWorkerGroupEntity(groupDTO);
        WorkerGroup savedGroup = inventoryService.saveWorkerGroup(group);

        String grpName = savedGroup.getGroupName() != null ? savedGroup.getGroupName() : "farm workers group";
        notificationService.sendTopicNotification(
                "all_assets",
                "New Farm Workers Group Available!",
                "A new group of workers (" + grpName + ") was just added to the platform.",
                null
        );

        return dtoMapper.toWorkerGroupDTO(savedGroup);
    }

    @PutMapping("/worker-groups/{id}")
    public WorkerGroupDTO updateWorkerGroup(@PathVariable("id") String id, @RequestBody WorkerGroupDTO groupDTO) {
        WorkerGroup existingGroup = inventoryService.getAllWorkerGroups().stream()
                .filter(g -> g.getGroupId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Worker Group not found"));

        String oldStatus = existingGroup.getApprovalStatus();

        if (groupDTO.getGroupName() != null) {
            existingGroup.setGroupName(groupDTO.getGroupName());
        }
        if (groupDTO.getMaleCount() != null) {
            existingGroup.setMaleCount(groupDTO.getMaleCount());
        }
        if (groupDTO.getFemaleCount() != null) {
            existingGroup.setFemaleCount(groupDTO.getFemaleCount());
        }
        if (groupDTO.getPricePerMale() != null) {
            existingGroup.setPricePerMale(groupDTO.getPricePerMale());
        }
        if (groupDTO.getPricePerFemale() != null) {
            existingGroup.setPricePerFemale(groupDTO.getPricePerFemale());
        }
        if (groupDTO.getPricePerMaleHourly() != null) {
            existingGroup.setPricePerMaleHourly(groupDTO.getPricePerMaleHourly());
        }
        if (groupDTO.getPricePerFemaleHourly() != null) {
            existingGroup.setPricePerFemaleHourly(groupDTO.getPricePerFemaleHourly());
        }
        if (groupDTO.getSkills() != null) {
            existingGroup.setSkills(groupDTO.getSkills());
        }
        if (groupDTO.getLocation() != null) {
            existingGroup.setLocation(groupDTO.getLocation());
        }
        if (groupDTO.getServiceRangeKm() != null) {
            existingGroup.setServiceRangeKm(groupDTO.getServiceRangeKm());
        }
        if (groupDTO.getIsAvailable() != null) {
            existingGroup.setIsAvailable(groupDTO.getIsAvailable());
        }
        if (groupDTO.getRating() != null) {
            existingGroup.setRating(groupDTO.getRating());
        }
        if (groupDTO.getApprovalStatus() != null) {
            existingGroup.setApprovalStatus(groupDTO.getApprovalStatus());
        }
        if (groupDTO.getImageUrl() != null) {
            existingGroup.setImageUrl(groupDTO.getImageUrl());
        }
        if (groupDTO.getHouseNo() != null) {
            existingGroup.setHouseNo(groupDTO.getHouseNo());
        }
        if (groupDTO.getStreet() != null) {
            existingGroup.setStreet(groupDTO.getStreet());
        }
        if (groupDTO.getVillage() != null) {
            existingGroup.setVillage(groupDTO.getVillage());
        }
        if (groupDTO.getDistrict() != null) {
            existingGroup.setDistrict(groupDTO.getDistrict());
        }
        if (groupDTO.getState() != null) {
            existingGroup.setState(groupDTO.getState());
        }
        if (groupDTO.getCountry() != null) {
            existingGroup.setCountry(groupDTO.getCountry());
        }
        if (groupDTO.getPincode() != null) {
            existingGroup.setPincode(groupDTO.getPincode());
        }
        if (groupDTO.getLatitude() != null) {
            existingGroup.setLatitude(groupDTO.getLatitude());
        }
        if (groupDTO.getLongitude() != null) {
            existingGroup.setLongitude(groupDTO.getLongitude());
        }

        if (groupDTO.getRoles() != null) {
            if (existingGroup.getRoles() == null) {
                existingGroup.setRoles(new java.util.ArrayList<>());
            } else {
                existingGroup.getRoles().clear();
            }
            List<com.agrifarms.common.entity.WorkerGroupRole> newRoles = groupDTO.getRoles().stream()
                    .map(dtoMapper::toWorkerGroupRoleEntity)
                    .collect(Collectors.toList());
            for (com.agrifarms.common.entity.WorkerGroupRole role : newRoles) {
                role.setWorkerGroup(existingGroup);
                existingGroup.getRoles().add(role);
            }
        }

        WorkerGroup savedGroup = inventoryService.saveWorkerGroup(existingGroup);
        checkAndNotifyApprovalStatus(savedGroup.getOwnerId(), oldStatus, savedGroup.getApprovalStatus(), savedGroup.getGroupName(), savedGroup.getGroupId(), "Worker Group");
        return dtoMapper.toWorkerGroupDTO(savedGroup);
    }

    @DeleteMapping("/worker-groups/{id}")
    public void deleteWorkerGroup(@PathVariable("id") String id) {
        inventoryService.deleteWorkerGroup(id);
    }

    // Skills Endpoints
    @GetMapping("/skills")
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    @PostMapping("/skills")
    public Skill addSkill(@RequestBody Skill skill) {
        return skillRepository.findByName(skill.getName())
                .orElseGet(() -> skillRepository.save(skill));
    }

    // Vehicle Categories Endpoints
    @GetMapping("/vehicle-categories")
    public List<VehicleCategory> getVehicleCategories() {
        return vehicleCategoryRepository.findAll();
    }

    @PostMapping("/vehicle-categories")
    public VehicleCategory addVehicleCategory(@RequestBody VehicleCategory vehicleCategory) {
        return vehicleCategoryRepository.findByName(vehicleCategory.getName())
                .orElseGet(() -> vehicleCategoryRepository.save(vehicleCategory));
    }

    private void checkAndNotifyApprovalStatus(String ownerId, String oldStatus, String newStatus, String assetName, String assetId, String assetType) {
        if (newStatus != null && !newStatus.equals(oldStatus)) {
            String title = "";
            String body = "";
            if ("Approved".equalsIgnoreCase(newStatus)) {
                title = "Listing Approved!";
                body = "Your " + assetType + " listing \"" + assetName + "\" has been approved by the admin and is now live.";
            } else if ("Rejected".equalsIgnoreCase(newStatus)) {
                title = "Listing Rejected";
                body = "Your " + assetType + " listing \"" + assetName + "\" was not approved by the admin.";
            }

            if (!title.isEmpty()) {
                String fcmToken = null;
                try {
                    com.agrifarms.common.entity.User owner = userService.getUserById(ownerId).orElse(null);
                    if (owner != null) {
                        fcmToken = owner.getFcmToken();
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching owner for notification: " + e.getMessage());
                }
                notificationService.saveAndSendNotification(ownerId, fcmToken, title, body, "asset_approval", assetId, null);
            }
        }
    }
}
