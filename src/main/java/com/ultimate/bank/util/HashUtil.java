package com.ultimate.bank.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashUtil {
    public static String hashCPF(String CPF) {
        return Hashing.sha256()
                      .hashString(CPF, StandardCharsets.UTF_8)
                      .toString();
    }
}
