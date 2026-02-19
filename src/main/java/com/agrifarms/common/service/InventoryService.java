package com.agrifarms.common.service;

import com.agrifarms.common.entity.Equipment;
import com.agrifarms.common.entity.ServiceOffering;
import com.agrifarms.common.entity.TransportVehicle;
import com.agrifarms.common.entity.WorkerGroup;
import com.agrifarms.common.repository.EquipmentRepository;
import com.agrifarms.common.repository.ServiceOfferingRepository;
import com.agrifarms.common.repository.TransportVehicleRepository;
import com.agrifarms.common.repository.WorkerGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final EquipmentRepository equipmentRepository;
    private final TransportVehicleRepository transportVehicleRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final WorkerGroupRepository workerGroupRepository;

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public List<Equipment> getAvailableEquipment() {
        return equipmentRepository.findByIsAvailableTrue();
    }

    public List<Equipment> getEquipmentByCategory(String category) {
        return equipmentRepository.findByCategory(category);
    }

    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public List<TransportVehicle> getAllVehicles() {
        return transportVehicleRepository.findAll();
    }

    public List<TransportVehicle> getAvailableVehicles() {
        return transportVehicleRepository.findByIsAvailableTrue();
    }

    public List<TransportVehicle> getVehiclesByType(String type) {
        return transportVehicleRepository.findByVehicleType(type);
    }

    public TransportVehicle saveVehicle(TransportVehicle vehicle) {
        return transportVehicleRepository.save(vehicle);
    }

    public List<ServiceOffering> getAllServices() {
        return serviceOfferingRepository.findAll();
    }

    public List<ServiceOffering> getAvailableServices() {
        return serviceOfferingRepository.findByIsAvailableTrue();
    }

    public List<ServiceOffering> getServicesByType(String type) {
        return serviceOfferingRepository.findByServiceType(type);
    }

    public ServiceOffering saveService(ServiceOffering service) {
        return serviceOfferingRepository.save(service);
    }

    public List<WorkerGroup> getAllWorkerGroups() {
        return workerGroupRepository.findAll();
    }

    public List<WorkerGroup> getWorkerGroupsByLocation(String location) {
        return workerGroupRepository.findByLocationContainingIgnoreCase(location);
    }

    public WorkerGroup saveWorkerGroup(WorkerGroup workerGroup) {
        return workerGroupRepository.save(workerGroup);
    }
}
