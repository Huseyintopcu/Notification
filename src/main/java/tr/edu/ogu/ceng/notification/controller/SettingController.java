package tr.edu.ogu.ceng.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ogu.ceng.notification.service.SettingsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings")
public class SettingController {

    private final SettingsService settingsService;

    @GetMapping("/test")
    public String getSetting(@PathVariable("id") Long id) {
        return "ffx ";
    }

}
