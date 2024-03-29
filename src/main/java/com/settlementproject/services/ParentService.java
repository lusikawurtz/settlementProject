package com.settlementproject.services;

import com.settlementproject.entities.ParentEntity;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.repositories.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;


    public ParentEntity getParentById(Long id) throws ParentNotExistException {
        Optional<ParentEntity> parentDTO = parentRepository.findParentEntityById(id);
        if (parentDTO.isEmpty())
            throw new ParentNotExistException(id.toString());
        return parentDTO.get();
    }

}