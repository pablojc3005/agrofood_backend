package com.example.agrofood_backend.service.impl;

import com.example.agrofood_backend.entity.Area;
import com.example.agrofood_backend.repository.AreaRepository;
import com.example.agrofood_backend.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Override
    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    @Override
    public Optional<Area> findById(Integer id) {
        return areaRepository.findById(id);
    }

    @Override
    public Area save(Area area) {
        return areaRepository.save(area);
    }

    @Override
    public void deleteById(Integer id) {
        areaRepository.deleteById(id);
    }
}
