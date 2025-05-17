package com.dkforum.utils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Utils {

    public static UUID generateMinecraftUUID(String username) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
    }

}
