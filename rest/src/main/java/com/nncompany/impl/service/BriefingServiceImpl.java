package com.nncompany.impl.service;

import com.nncompany.api.interfaces.services.BriefingService;
import com.nncompany.api.interfaces.store.BriefingStore;
import com.nncompany.api.model.entities.Briefing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BriefingServiceImpl implements BriefingService {

    @Autowired
    private BriefingStore briefingStore;

    @Override
    public Briefing getById(int id) {
        return briefingStore.getById(id);
    }

    @Override
    public Page<Briefing> getWithPagination(Integer page, Integer pageSize) {
        return briefingStore.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Briefing save(Briefing briefing) {
        return briefingStore.save(briefing);
    }

    @Override
    public Briefing update(Briefing briefing) {
        return briefingStore.save(briefing);
    }

    @Override
    public void delete(Integer id) {
        briefingStore.deleteById(id);
    }
}
