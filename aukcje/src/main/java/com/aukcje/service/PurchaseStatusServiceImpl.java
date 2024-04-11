package com.aukcje.service;

import com.aukcje.dto.PurchaseStatusDTO;
import com.aukcje.dto.mapper.PurchaseStatusDTOMapper;
import com.aukcje.entity.PurchaseStatus;
import com.aukcje.exception.customException.PurchaseStatusNotFoundException;
import com.aukcje.repository.PurchaseStatusRepository;
import com.aukcje.service.iface.PurchaseStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class PurchaseStatusServiceImpl implements PurchaseStatusService {

    private final PurchaseStatusRepository purchaseStatusRepository;

    @Override
    public PurchaseStatusDTO findById(Integer id) throws PurchaseStatusNotFoundException {
        PurchaseStatus purchaseStatus = purchaseStatusRepository.findById(id).orElseThrow(() -> new PurchaseStatusNotFoundException(id));

        return getPurchaseStatusDTO(purchaseStatus);
    }

    @Override
    public List<PurchaseStatusDTO> findAll() {
        List<PurchaseStatus> purchaseStatuses = purchaseStatusRepository.findAll();
        //TODO: DODAĆ BŁĄD - błąd pobierania statusów zamówienia dla pustej listy

        return getPurchaseStatusDTO(purchaseStatuses);
    }

    private PurchaseStatusDTO getPurchaseStatusDTO(PurchaseStatus purchaseStatus) {
        return PurchaseStatusDTOMapper.instance.purchaseStatusDTO(purchaseStatus);
    }

    private List<PurchaseStatusDTO> getPurchaseStatusDTO(List<PurchaseStatus> purchaseStatuses) {
        List<PurchaseStatusDTO> purchaseStatusDTOS = new ArrayList<>();
        for(PurchaseStatus purchaseStatus : purchaseStatuses){
            PurchaseStatusDTO tempPurchaseStatus = PurchaseStatusDTOMapper.instance.purchaseStatusDTO(purchaseStatus);
            purchaseStatusDTOS.add(tempPurchaseStatus);
        }
        return purchaseStatusDTOS;
    }

}
