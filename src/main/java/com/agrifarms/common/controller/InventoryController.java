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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*") // Allow Flutter to access
public class InventoryController {

    private final InventoryService inventoryService;
    private final DtoMapper dtoMapper;

    public InventoryController(InventoryService inventoryService, DtoMapper dtoMapper) {
        this.inventoryService = inventoryService;
        this.dtoMapper = dtoMapper;
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
        return dtoMapper.toEquipmentDTO(savedEquipment);
    }

    @PutMapping("/equipment/{id}")
    public EquipmentDTO updateEquipment(@PathVariable String id, @RequestBody EquipmentDTO equipmentDTO) {
        Equipment existingEquipment = inventoryService.getAllEquipment().stream()
                .filter(e -> e.getEquipmentId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        
        // Update fields from DTO
        if (equipmentDTO.getCategory() != null) existingEquipment.setCategory(equipmentDTO.getCategory());
        if (equipmentDTO.getBrandModel() != null) existingEquipment.setBrandModel(equipmentDTO.getBrandModel());
        if (equipmentDTO.getConditionStatus() != null) existingEquipment.setConditionStatus(equipmentDTO.getConditionStatus());
        if (equipmentDTO.getPricePerHour() != null) existingEquipment.setPricePerHour(equipmentDTO.getPricePerHour());
        if (equipmentDTO.getOperatorAvailable() != null) existingEquipment.setOperatorAvailable(equipmentDTO.getOperatorAvailable());
        if (equipmentDTO.getLocation() != null) existingEquipment.setLocation(equipmentDTO.getLocation());
        if (equipmentDTO.getIsAvailable() != null) existingEquipment.setIsAvailable(equipmentDTO.getIsAvailable());
        if (equipmentDTO.getRating() != null) existingEquipment.setRating(equipmentDTO.getRating());
        if (equipmentDTO.getApprovalStatus() != null) existingEquipment.setApprovalStatus(equipmentDTO.getApprovalStatus());
        if (equipmentDTO.getImageUrl() != null) existingEquipment.setImageUrl(equipmentDTO.getImageUrl());
        
        Equipment savedEquipment = inventoryService.saveEquipment(existingEquipment);
        return dtoMapper.toEquipmentDTO(savedEquipment);
    }

    @DeleteMapping("/equipment/{id}")
    public void deleteEquipment(@PathVariable String id) {
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
        return dtoMapper.toTransportVehicleDTO(savedVehicle);
    }

    @PutMapping("/vehicles/{id}")
    public TransportVehicleDTO updateVehicle(@PathVariable String id, @RequestBody TransportVehicleDTO vehicleDTO) {
        TransportVehicle existingVehicle = inventoryService.getAllVehicles().stream()
                .filter(v -> v.getVehicleId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (vehicleDTO.getVehicleType() != null) existingVehicle.setVehicleType(vehicleDTO.getVehicleType());
        if (vehicleDTO.getVehicleNumber() != null) existingVehicle.setVehicleNumber(vehicleDTO.getVehicleNumber());
        if (vehicleDTO.getLoadCapacity() != null) existingVehicle.setLoadCapacity(vehicleDTO.getLoadCapacity());
        if (vehicleDTO.getPricePerKmOrTrip() != null) existingVehicle.setPricePerKmOrTrip(vehicleDTO.getPricePerKmOrTrip());
        if (vehicleDTO.getDriverIncluded() != null) existingVehicle.setDriverIncluded(vehicleDTO.getDriverIncluded());
        if (vehicleDTO.getServiceArea() != null) existingVehicle.setServiceArea(vehicleDTO.getServiceArea());
        if (vehicleDTO.getLocation() != null) existingVehicle.setLocation(vehicleDTO.getLocation());
        if (vehicleDTO.getIsAvailable() != null) existingVehicle.setIsAvailable(vehicleDTO.getIsAvailable());
        if (vehicleDTO.getRating() != null) existingVehicle.setRating(vehicleDTO.getRating());
        if (vehicleDTO.getApprovalStatus() != null) existingVehicle.setApprovalStatus(vehicleDTO.getApprovalStatus());
        if (vehicleDTO.getImageUrl() != null) existingVehicle.setImageUrl(vehicleDTO.getImageUrl());

        TransportVehicle savedVehicle = inventoryService.saveVehicle(existingVehicle);
        return dtoMapper.toTransportVehicleDTO(savedVehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    public void deleteVehicle(@PathVariable String id) {
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
        return dtoMapper.toServiceOfferingDTO(savedService);
    }

    @PutMapping("/services/{id}")
    public ServiceOfferingDTO updateService(@PathVariable String id, @RequestBody ServiceOfferingDTO serviceDTO) {
        ServiceOffering existingService = inventoryService.getAllServices().stream()
                .filter(s -> s.getServiceId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (serviceDTO.getServiceType() != null) existingService.setServiceType(serviceDTO.getServiceType());
        if (serviceDTO.getBusinessName() != null) existingService.setBusinessName(serviceDTO.getBusinessName());
        if (serviceDTO.getDescription() != null) existingService.setDescription(serviceDTO.getDescription());
        if (serviceDTO.getEquipmentUsed() != null) existingService.setEquipmentUsed(serviceDTO.getEquipmentUsed());
        if (serviceDTO.getPriceRate() != null) existingService.setPriceRate(serviceDTO.getPriceRate());
        if (serviceDTO.getOperatorIncluded() != null) existingService.setOperatorIncluded(serviceDTO.getOperatorIncluded());
        if (serviceDTO.getLocation() != null) existingService.setLocation(serviceDTO.getLocation());
        if (serviceDTO.getIsAvailable() != null) existingService.setIsAvailable(serviceDTO.getIsAvailable());
        if (serviceDTO.getRating() != null) existingService.setRating(serviceDTO.getRating());
        if (serviceDTO.getApprovalStatus() != null) existingService.setApprovalStatus(serviceDTO.getApprovalStatus());
        if (serviceDTO.getImageUrl() != null) existingService.setImageUrl(serviceDTO.getImageUrl());

        ServiceOffering savedService = inventoryService.saveService(existingService);
        return dtoMapper.toServiceOfferingDTO(savedService);
    }

    @DeleteMapping("/services/{id}")
    public void deleteService(@PathVariable String id) {
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
        return dtoMapper.toWorkerGroupDTO(savedGroup);
    }

    @PutMapping("/worker-groups/{id}")
    public WorkerGroupDTO updateWorkerGroup(@PathVariable String id, @RequestBody WorkerGroupDTO groupDTO) {
        WorkerGroup existingGroup = inventoryService.getAllWorkerGroups().stream()
                .filter(g -> g.getGroupId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Worker Group not found"));

        if (groupDTO.getGroupName() != null) existingGroup.setGroupName(groupDTO.getGroupName());
        if (groupDTO.getMaleCount() != null) existingGroup.setMaleCount(groupDTO.getMaleCount());
        if (groupDTO.getFemaleCount() != null) existingGroup.setFemaleCount(groupDTO.getFemaleCount());
        if (groupDTO.getPricePerMale() != null) existingGroup.setPricePerMale(groupDTO.getPricePerMale());
        if (groupDTO.getPricePerFemale() != null) existingGroup.setPricePerFemale(groupDTO.getPricePerFemale());
        if (groupDTO.getSkills() != null) existingGroup.setSkills(groupDTO.getSkills());
        if (groupDTO.getLocation() != null) existingGroup.setLocation(groupDTO.getLocation());
        if (groupDTO.getServiceRangeKm() != null) existingGroup.setServiceRangeKm(groupDTO.getServiceRangeKm());
        if (groupDTO.getIsAvailable() != null) existingGroup.setIsAvailable(groupDTO.getIsAvailable());
        if (groupDTO.getRating() != null) existingGroup.setRating(groupDTO.getRating());
        if (groupDTO.getApprovalStatus() != null) existingGroup.setApprovalStatus(groupDTO.getApprovalStatus());
        if (groupDTO.getImageUrl() != null) existingGroup.setImageUrl(groupDTO.getImageUrl());

        WorkerGroup savedGroup = inventoryService.saveWorkerGroup(existingGroup);
        return dtoMapper.toWorkerGroupDTO(savedGroup);
    }

    @DeleteMapping("/worker-groups/{id}")
    public void deleteWorkerGroup(@PathVariable String id) {
        inventoryService.deleteWorkerGroup(id);
    }
}
