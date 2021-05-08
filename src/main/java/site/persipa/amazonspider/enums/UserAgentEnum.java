package site.persipa.amazonspider.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum UserAgentEnum {

    /**
     * 枚举的UA
     */
    UA1("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.51");

    private String userAgent;

    UserAgentEnum(String userAgent) {
        this.userAgent = userAgent;
    }

    public static String randomUA() {
        UserAgentEnum[] values = UserAgentEnum.values();
        Random random = new Random();
        int i = random.nextInt(values.length);
        return values[i].getUserAgent();
    }
}
