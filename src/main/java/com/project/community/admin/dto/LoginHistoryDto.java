package com.project.community.admin.dto;

import com.project.community.admin.entity.LoginHistory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginHistoryDto {
    Long id;

    String userId;
    String userAgent;
    String clientIp;
    LocalDateTime loginDt;

    Long seq;


    public static List<LoginHistoryDto> of (List<LoginHistory> loginHistories) {
        if (loginHistories != null) {
            List<LoginHistoryDto> loginHistoryList = new ArrayList<>();
            for (LoginHistory x : loginHistories) {
                loginHistoryList.add(of(x));
            }

            return loginHistoryList;
        }
        return null;
    }

    public static LoginHistoryDto of (LoginHistory loginHistory) {
        return LoginHistoryDto.builder()
            .id(loginHistory.getId())
            .userId(loginHistory.getUserId())
            .userAgent(loginHistory.getUserAgent())
            .clientIp(loginHistory.getClientIp())
            .loginDt(loginHistory.getLoginDt())
            .build();
    }

    public String getLoginDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return loginDt != null ? loginDt.format(formatter) : "";
    }


}
