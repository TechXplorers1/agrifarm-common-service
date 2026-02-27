package com.agrifarms.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agrifarms.common.entity.Equipment;
import com.agrifarms.common.entity.ServiceOffering;
import com.agrifarms.common.entity.TransportVehicle;
import com.agrifarms.common.entity.WorkerGroup;
import com.agrifarms.common.repository.EquipmentRepository;
import com.agrifarms.common.repository.ServiceOfferingRepository;
import com.agrifarms.common.repository.TransportVehicleRepository;
import com.agrifarms.common.repository.WorkerGroupRepository;

@Service
public class InventoryService {

    private final EquipmentRepository equipmentRepository;
    private final TransportVehicleRepository transportVehicleRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final WorkerGroupRepository workerGroupRepository;

    public InventoryService(EquipmentRepository equipmentRepository,
                            TransportVehicleRepository transportVehicleRepository,
                            ServiceOfferingRepository serviceOfferingRepository,
                            WorkerGroupRepository workerGroupRepository) {
        this.equipmentRepository = equipmentRepository;
        this.transportVehicleRepository = transportVehicleRepository;
        this.serviceOfferingRepository = serviceOfferingRepository;
        this.workerGroupRepository = workerGroupRepository;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public List<Equipment> getAvailableEquipment() {
        return equipmentRepository.findByIsAvailableTrue();
    }

    public List<Equipment> getEquipmentByCategory(String category) {
        return equipmentRepository.findByCategory(category);
    }

    public List<Equipment> getEquipmentByOwnerId(String ownerId) {
        return equipmentRepository.findByOwnerId(ownerId);
    }

    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public void deleteEquipment(String equipmentId) {
        equipmentRepository.deleteById(equipmentId);
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

    public List<TransportVehicle> getVehiclesByOwnerId(String ownerId) {
        return transportVehicleRepository.findByOwnerId(ownerId);
    }

    public TransportVehicle saveVehicle(TransportVehicle vehicle) {
        return transportVehicleRepository.save(vehicle);
    }

    public void deleteVehicle(String vehicleId) {
        transportVehicleRepository.deleteById(vehicleId);
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

    public List<ServiceOffering> getServicesByOwnerId(String ownerId) {
        return serviceOfferingRepository.findByOwnerId(ownerId);
    }

    public ServiceOffering saveService(ServiceOffering service) {
        return serviceOfferingRepository.save(service);
    }

    public void deleteService(String serviceId) {
        serviceOfferingRepository.deleteById(serviceId);
    }

    public List<WorkerGroup> getAllWorkerGroups() {
        return workerGroupRepository.findAll();
    }

    public List<WorkerGroup> getWorkerGroupsByLocation(String location) {
        return workerGroupRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<WorkerGroup> getWorkerGroupsByOwnerId(String ownerId) {
        return workerGroupRepository.findByOwnerId(ownerId);
    }

    public WorkerGroup saveWorkerGroup(WorkerGroup workerGroup) {
        return workerGroupRepository.save(workerGroup);
    }

    public void deleteWorkerGroup(String groupId) {
        workerGroupRepository.deleteById(groupId);
    }
}
