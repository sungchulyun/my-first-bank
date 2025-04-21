package com.myfirstbank.finance_kids.global.meta;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public class MetaService {

    public static final String SERVER_PATH_VERSION = "v1";
    private static final String SERVER_VERSION = "1.0.0";
    private static final String SERVER_ENCODING = "UTF-8";

    protected MetaService() {

    }

    /**
     * @return - server version : 서버 버전
     * - server path version : 서버 경로 버전
     * - server encoding : 서버 인코딩
     * - server time : 서버 시간
     */

    public static MetaInfos createMetaInfo(){
        MetaInfos metaInfos = new MetaInfos();
        metaInfos.add("serverVersion", SERVER_VERSION);
        metaInfos.add("serverPathVersion", SERVER_PATH_VERSION);
        metaInfos.add("serverEncoding", SERVER_ENCODING);
        metaInfos.add("serverResponseTime", LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString());
        return metaInfos;
    }
}
