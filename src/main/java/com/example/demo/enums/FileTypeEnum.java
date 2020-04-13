package com.example.demo.enums;

/**
 * description:文件类型枚举
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
public enum FileTypeEnum {
    /**
     * 图片类型文件
     */
    IMG(1, "图片", ".jpg"),
    /**
     * 音频类型文件
     */
    AUDIO(2, "音频", ".mp3"),
    /**
     * 视频类型文件
     */
    VIDEO(3, "视频", ".mp4"),
    /**
     * apk类型文件
     */
    APP(4, "App包", ".apk"),
    /**
     * 其他类型文件
     */
    OTHER(5, "其他", "");

    private Integer code;
    private String message;
    private String defaultSuffix;

    FileTypeEnum(Integer code, String message, String defaultSuffix) {
        this.code = code;
        this.message = message;
        this.defaultSuffix = defaultSuffix;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDefaultSuffix() {
        return defaultSuffix;
    }

    public static String fromCode(Integer code) {
        for (FileTypeEnum enums : FileTypeEnum.values()) {
            if (enums.getCode().equals(code)) {
                return enums.getDefaultSuffix();
            }
        }
        return "";
    }

}


