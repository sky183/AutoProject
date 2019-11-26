package com.sb.auto.common.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

/**
 * [LAMP] version [1.0]
 *
 * Copyright ⓒ [2018] kt corp. All rights reserved.
 *
 * This is a proprietary software of kt corp, and you may not use this file except in
 * compliance with license agreement with kt corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of kt corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 *
 * @author kt
 * @since 2018. 07. 20.
 * @Version 1.0
 * @see
 * @Copyright 2018 By KT corp. All rights reserved.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *  수정일               수정자                수정내용
 *  -------------        ----------       -------------------------
 *  2018. 07. 12.          이용준              최초생성
 */
@Component
public class FileUtil {

    private String defaultFilePath;

    /**
     * 파일을 저장하고 경로를 리턴함
     * @param multipartFile
     * @return
     */
    public String store(MultipartFile multipartFile) throws IOException {
        String originName = multipartFile.getOriginalFilename();
        String extension = originName.indexOf(".") > -1 ? "." + originName.substring(originName.lastIndexOf(".")) : "";
        String fileName = originName.substring(0, originName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + extension;
        String fullPath = this.defaultFilePath + File.separator + fileName;

        try {
            multipartFile.transferTo(new File(fullPath));
        } catch (IOException e) {
            throw e;
        }

        return fullPath;
    }

    /**
     * 파일삭제
     * @param filePath
     * @return
     */
    public boolean delete(String filePath) {
        if(StringUtil.isEmpty(filePath)) return false; //의외로 개발환경에서 빈번하게 발생됨.
        File file = new File(this.check(filePath));
        return file.exists() ? file.delete() : false;
    }

    /**
     * 경로 파라미터 유효성
     * @param filePath
     * @return
     */
    private String check(String filePath){
        if(filePath == null) return null;
        StringBuilder sb = new StringBuilder();
        Function<Character, Character> fn  = (ch) -> {
            for( int i = 48; i < 58; ++i) if(ch == i) return (char) i;
            for( int i = 65; i < 91; ++i) if(ch == i) return (char) i;
            for( int i = 97; i < 123; ++i) if(ch == i) return (char) i;
            switch(ch) {
                case '/': return '/';
                case '.': return '.';
                case '-': return '-';
                case '_': return '_';
                case ' ': return ' ';
                case ':': return ':';
                case '&': return '&';
                default: return '%';
            }
        };

        for(int idx = 0; idx < filePath.length(); idx += 1) sb.append(fn.apply(filePath.charAt(idx)));
        return sb.toString();
    }
}
