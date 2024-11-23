package com.operator.charge_config.service.impl;

import com.operator.charge_config.repository.KeywordDetailsRepository;
import com.operator.charge_config.service.KeywordDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeywordDetailsServiceImpl implements KeywordDetailsService {

    @Autowired
    private KeywordDetailsRepository keywordDetailsRepository;
    @Override
    public Boolean findByKeyword(String keyword) {
        return keywordDetailsRepository.existsByKeyword(keyword);
    }
}
