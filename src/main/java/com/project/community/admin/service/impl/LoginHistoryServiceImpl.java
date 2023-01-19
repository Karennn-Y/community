package com.project.community.admin.service.impl;

import com.project.community.admin.dto.LoginHistoryDto;
import com.project.community.admin.entity.LoginHistory;
import com.project.community.admin.mapper.LoginHistoryMapper;
import com.project.community.admin.model.LoginHistoryParam;
import com.project.community.admin.repository.LoginHistoryRepository;
import com.project.community.admin.service.LoginHistoryService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;
    private final LoginHistoryMapper loginHistoryMapper;
    @Override
    public boolean add(LoginHistoryParam parameter) {
        LoginHistory loginHistory = LoginHistory.builder()
                    .userId(parameter.getUserId())
                    .userAgent(parameter.getUserAgent())
                    .clientIp(parameter.getClientIp())
                    .loginDt(LocalDateTime.now())
                    .build();
        loginHistoryRepository.save(loginHistory);
        return true;
    }

    @Override
    public List<LoginHistoryDto> list(LoginHistoryParam parameter) {

        return loginHistoryMapper.selectList(parameter);
    }

}
