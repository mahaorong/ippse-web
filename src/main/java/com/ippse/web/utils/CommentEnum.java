package com.ippse.web.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommentEnum {
    COMMENT_NOT_FOUND(1,"评论不存在"),
    USER_NOT_FOUND(2,"用户未登陆或不存在"),
    PRAISE_CANCLE(3,"取消赞成功"),
    APP_NOT_FOUND(4,"APP不存在"),
    ;

    private Integer code;
    private String message;
}
