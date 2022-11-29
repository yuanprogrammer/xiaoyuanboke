package com.xiaoyuan.three.service;

public interface ThreadService {

    void sendSampleMail(String to, String theme, String content, String... cc);
}
