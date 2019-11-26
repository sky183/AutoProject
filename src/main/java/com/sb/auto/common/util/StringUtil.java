package com.sb.auto.common.util;

import org.springframework.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;

public class StringUtil extends StringUtils {

    public static final String BRACE = "{}";

    /**
     * 파라미터가 Null 인경우 빈값으로 치환
     * @param string 검사해야할 값
     * @return
     */
    public final static String null2void(String string){
        return null2string(string, "");
    }

    /**
     * 파라미터가 Null 인경우 기본값으로 치환
     * @param string
     * @param defaultString
     * @return
     */
    public final static String null2string(String string, String defaultString){
        return isEmpty(string) ? defaultString : string;
    }

    /**
     * null이거나 빈 값이 아닌 경우 true
     * @param string
     * @return
     */
    public final static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }
    
    /**
     * 파라미터가 null인 경우 white-space, 아닐 경우 inputstring trim 해서 리턴
     * @param inputStr
     * @return
     */
    public static String nvl(String inputStr) {
		return (inputStr != null) ? inputStr.trim() : "";
	}

    /**
     * 파라미터가 object인 경우 String 으로 casting 해서 nvl 처리
     * @param inputStr
     * @return
     */
	public static String nvl(Object inputStr) {
		return ((inputStr != null) ? String.valueOf(inputStr).trim() : "");
	}

    /**
     * URL 조합
     * @param host
     * @param contextPath
     * @param path
     * @return
     */
    public static String joinUrl(String host, String contextPath, String path){
        String $host = host, $path= path;
        if($host.lastIndexOf("/") == ($host.length() - 1)) $host = host.substring(0, $host.length() - 1);
        $path = replace(contextPath + $path, "/", ";");
        String result = new String($host + StringUtil.replace($path, ";;", ";")).replace(';', '/');
        return result;
    }

    /**
     * 랜덤값 생성
     * @param isNumber 숫자로 구성할지 여부(문자열 포함인경우 숫자가 포함되며, 대문자)
     * @param len 난수길이
     * @return
     */
    public static String getRandom(boolean isNumber, int len) {
        StringBuilder buf = new StringBuilder();
        Random ran = new Random();
        for(int idx = 0; idx < len; idx += 1) {
            if(isNumber) buf.append(ran.nextInt(10));
            else {
                if(ran.nextBoolean()) buf.append(ran.nextInt(10));
                else buf.append((char)(ran.nextInt(26) + 65));
            }
        }
        return buf.toString();
    }

    /**
     * null 여부
     * @param ch
     * @return
     */
    public static boolean isNull(char ch) {
        return new Character('\u0000').equals(ch);
    }

    /**
     * split 확장, split 대상 문자열의 시작(0) 부터 끝에 도달할때까지 패턴 유형에 대응되는 문자열을 거르고 분할
     * @param toSplit
     * @param delimiter
     * @param patterns
     * @return
     */
    public static List<String> split(String toSplit, String delimiter, String[] patterns){
        String[] arr = toSplit.split("");
        List<String> result = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        f:for(int idx = 0; idx < arr.length; idx += 1) {
            String str = arr[idx];

            for(String pattern : patterns) {
                if(BRACE.equals(pattern) && "{".equals(str)) {
                    int endIdx = toSplit.indexOf("}", idx + 1);
                    if(idx > endIdx) {
                        endIdx = toSplit.length();
                        stringBuilder.append(toSplit, idx, endIdx);
                    } else stringBuilder.append(toSplit, idx, endIdx + 1);
                    idx = endIdx;
                    continue f;
                }
            }

            if(str.indexOf(delimiter) > -1) {
                if(!"".equals(stringBuilder.toString())) {
                    result.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                } else result.add("");
            } else {
                stringBuilder.append(str);
            }
        }
        if(!"".equals(stringBuilder.toString())) result.add(stringBuilder.toString());
        return result;
    }

    /**
     * 문자열 값이 len 보다 큰지 여부
     * @param string
     * @param len
     * @return
     */
    public static boolean isOverSize(String string, int len) {
        return null2void(string).length() > len;
    }


    /**
     * SHUB Log parse
     * @param payload
     * @return
     */
    public static List<String> sHubParsePayload(String payload){
        List<String> list;
        try {
            String temp = payload.trim().substring(1, payload.trim().length() - 1);
            list = StringUtil.split(temp, "|", new String[]{StringUtil.BRACE});
            if(list != null) list.stream().forEach(t->t.trim());
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    /**
     * SHUB Log-Data parse
     * @param data
     * @return
     */
    public static Map<String, String> sHubParseData(String data) {
        Map<String, String> map;
        try{
            StringBuilder stringBuilder = new StringBuilder(data);
            String[] array = stringBuilder.insert(data.lastIndexOf("{"), "\n").toString().split("\n");
            String[] keys = array[0].trim().substring(array[0].trim().indexOf("{") + 1, array[0].trim().length() - 1).split("\\|");
            String[] values = array[1].trim().substring(array[1].trim().indexOf("{")+ 1, array[1].trim().length() - 1).split("\\|");
            map = new HashMap<>();
            for(int idx = 0; idx < keys.length; idx += 1) map.put(keys[idx], values[idx]);
        }catch (Exception e){
            e.printStackTrace();
            map = null;
        }
        return map;
    }

    /**
     * 명령어 치환
     * @param command
     * @return
     */
    public static String replaceCommand(String command) {
        if(isNotEmpty(command)){
            List<String> commandList = Arrays.asList(command.split(" "))
                    .stream()
                    .filter(s -> !StringUtil.null2void(s).replaceAll(" ", "").equals(""))
                    .collect(Collectors.toList());

            StringBuilder stringBuilder = new StringBuilder(commandList.get(0));
            commandList.remove(0);
            commandList.stream().sorted(Comparator.comparing(String::toString)).forEach(str -> stringBuilder.append(" " + str));
            return stringBuilder.toString();
        } else return null;
    }
}
