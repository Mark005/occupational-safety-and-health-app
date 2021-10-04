package com.nncompany.api.interfaces.services;

import com.nncompany.api.model.entities.Briefing;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BriefingService {

    Briefing getById(int id);

    Page<Briefing> getWithPagination(Integer page, Integer pageSize);

    Briefing save(Briefing briefing);

    Briefing update(Briefing briefing);

    void delete(Integer id);
}
