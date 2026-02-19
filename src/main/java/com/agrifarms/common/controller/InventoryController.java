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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow Flutter to access
public class InventoryController {

    private final InventoryService inventoryService;
    private final DtoMapper dtoMapper;

    // Equipment
    @GetMapping("/equipment")
    public List<EquipmentDTO> getEquipment(@RequestParam(required = false) String category) {
        List<Equipment> equipmentList;
        if (category != null && !category.isEmpty()) {
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

    // Vehicles
    @GetMapping("/vehicles")
    public List<TransportVehicleDTO> getVehicles(@RequestParam(required = false) String type) {
        List<TransportVehicle> vehicleList;
        if (type != null && !type.isEmpty()) {
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

    // Services
    @GetMapping("/services")
    public List<ServiceOfferingDTO> getServices(@RequestParam(required = false) String type) {
        List<ServiceOffering> serviceList;
        if (type != null && !type.isEmpty()) {
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

    // Worker Groups
    @GetMapping("/worker-groups")
    public List<WorkerGroupDTO> getWorkerGroups(@RequestParam(required = false) String location) {
        List<WorkerGroup> groupList;
        if (location != null && !location.isEmpty()) {
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
}
