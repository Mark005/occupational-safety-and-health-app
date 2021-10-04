package com.nncompany.api.interfaces.services;

import com.nncompany.api.model.entities.Briefing;

import java.util.List;

public interface BriefingService {

    Briefing get(int id);

    List<Briefing> getAll();

    List<Briefing> getWithPagination(Integer page, Integer pageSize);

    void save(Briefing briefing);

    void update(Briefing briefing);

    void delete(Briefing briefing);
}
