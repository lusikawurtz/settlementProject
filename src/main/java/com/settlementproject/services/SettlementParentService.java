package com.settlementproject.services;

import com.settlementproject.dtos.SettlementChildDTO;
import com.settlementproject.dtos.SettlementParentDTO;
import com.settlementproject.entities.ChildEntity;
import com.settlementproject.entities.ParentEntity;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.exceptions.WrongDateInpuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementParentService {
    private final SettlementChildService settlementChildService;
    private final ParentService parentService;
    private final ChildService childService;


    public SettlementParentDTO createSettlementForParent(ParentEntity inputParent, String inputDate) throws ParentNotExistException, SchoolNotExistException, WrongDateInpuException {
        ParentEntity parent = parentService.getParentById(inputParent.getId());
        return createSettlement(inputDate, parent);
    }

    private SettlementParentDTO createSettlement(String inputDate, ParentEntity parent) throws SchoolNotExistException, WrongDateInpuException {
        SettlementParentDTO settlementParent = new SettlementParentDTO();
        setParentData(parent, settlementParent);
        setChildrenData(parent, inputDate, settlementParent);
        return settlementParent;
    }

    private static void setParentData(ParentEntity parent, SettlementParentDTO settlementParent) {
        settlementParent.setFirstName(parent.getFirstName());
        settlementParent.setLastName(parent.getLastName());
    }

    private void setChildrenData(ParentEntity parent, String inputDate, SettlementParentDTO settlementParent) throws SchoolNotExistException, WrongDateInpuException {
        List<ChildEntity> parentsChildren = childService.getChildListByParentId(parent.getId());
        if (parentsChildren.isEmpty()) {
            return;
        }
        List<SettlementChildDTO> settlementsForChildren = settlementChildService.createSettlementsForChildren(inputDate, parentsChildren);
        settlementParent.setChildren(settlementsForChildren);
    }

}