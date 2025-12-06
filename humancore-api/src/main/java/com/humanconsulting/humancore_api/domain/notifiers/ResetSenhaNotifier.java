package com.humanconsulting.humancore_api.domain.notifiers;

import java.util.Map;

public interface ResetSenhaNotifier {
    void sendResetInfo(Map<String, String> resetInfo);
}
