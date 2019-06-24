package com.ippse.web.utils;

public class CommentException extends RuntimeException{
	private static final long serialVersionUID = -8312478292840911242L;
	private Integer code;

    public CommentException(String message, Integer code) {
        super(message);
        this.code = code;
    }

	public CommentException(CommentEnum commentEnum) {
        super(commentEnum.getMessage());
        this.code = commentEnum.getCode();
    }
}
