package com.aukcje.service;

import com.aukcje.dto.PurchaseStatusDTO;
import com.aukcje.dto.mapper.PurchaseStatusDTOMapper;
import com.aukcje.entity.PurchaseStatus;
import com.aukcje.repository.PurchaseStatusRepository;
import com.aukcje.service.iface.PurchaseStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseStatusServiceImpl implements PurchaseStatusService {

    @Autowired
    PurchaseStatusRepository purchaseStatusRepository;

    @Override
    public PurchaseStatusDTO findById(Integer id) {
        Optional<PurchaseStatus> purchaseStatusOptional = purchaseStatusRepository.findById(id);
        //TODO: DODAĆ BŁĄD - błąd pobierania statusu zamówienia
        PurchaseStatus purchaseStatus = purchaseStatusRepository.findById(id).orElse(null);

        return getPurchaseStatusDTO(purchaseStatus);
    }

    @Override
    public List<PurchaseStatusDTO> findAll() {
        List<PurchaseStatus> purchaseStatuses = purchaseStatusRepository.findAll();
        //TODO: DODAĆ BŁĄD - błąd pobierania statusów zamówienia dla pustej listy

        return getPurchaseStatusDTO(purchaseStatuses);
    }

    private PurchaseStatusDTO getPurchaseStatusDTO(PurchaseStatus purchaseStatus){
        return PurchaseStatusDTOMapper.instance.purchaseStatusDTO(purchaseStatus);
    }

    private List<PurchaseStatusDTO> getPurchaseStatusDTO(List<PurchaseStatus> purchaseStatuses){
        List<PurchaseStatusDTO> purchaseStatusDTOS = new ArrayList<>();
        for(PurchaseStatus purchaseStatus : purchaseStatuses){
            PurchaseStatusDTO tempPurchaseStatus = PurchaseStatusDTOMapper.instance.purchaseStatusDTO(purchaseStatus);
            purchaseStatusDTOS.add(tempPurchaseStatus);
        }
        return purchaseStatusDTOS;
    }

}
