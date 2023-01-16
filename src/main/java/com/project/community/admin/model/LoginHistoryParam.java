package com.project.community.admin.model;

import com.project.community.common.model.CommonParam;
import lombok.Data;

@Data
public class LoginHistoryParam extends CommonParam {

    String userId;
    String userAgent;
    String clientIp;
}
