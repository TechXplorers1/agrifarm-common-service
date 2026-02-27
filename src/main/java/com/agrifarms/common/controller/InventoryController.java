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
        Equipment equipment = dtoMapper.toEquipmentEntity(equipmentDTO);
        equipment.setEquipmentId(id);
        Equipment savedEquipment = inventoryService.saveEquipment(equipment);
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
        TransportVehicle vehicle = dtoMapper.toTransportVehicleEntity(vehicleDTO);
        vehicle.setVehicleId(id);
        TransportVehicle savedVehicle = inventoryService.saveVehicle(vehicle);
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
        ServiceOffering service = dtoMapper.toServiceOfferingEntity(serviceDTO);
        service.setServiceId(id);
        ServiceOffering savedService = inventoryService.saveService(service);
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
        WorkerGroup group = dtoMapper.toWorkerGroupEntity(groupDTO);
        group.setGroupId(id);
        WorkerGroup savedGroup = inventoryService.saveWorkerGroup(group);
        return dtoMapper.toWorkerGroupDTO(savedGroup);
    }

    @DeleteMapping("/worker-groups/{id}")
    public void deleteWorkerGroup(@PathVariable String id) {
        inventoryService.deleteWorkerGroup(id);
    }
}
