package com.common.camel.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class dee {
    private String s1;
    private String s2;

    public dee(String s, String s1) {
        this.s1 = s;
        this.s2 = s1;
    }
}
