package com.project.community.admin.service;

import com.project.community.admin.dto.LoginHistoryDto;
import com.project.community.admin.model.LoginHistoryParam;
import java.util.List;

public interface LoginHistoryService {

    boolean add(LoginHistoryParam parameter);
    List<LoginHistoryDto> list(LoginHistoryParam parameter);
}
