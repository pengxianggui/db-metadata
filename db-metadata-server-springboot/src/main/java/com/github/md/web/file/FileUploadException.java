package com.github.md.web.file;

import com.github.md.web.WebException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class FileUploadException extends WebException {
    @Getter
    private MultipartFile file;

    public FileUploadException(MultipartFile file, Exception ex) {
        super(ex);
        this.file = file;
    }

    public FileUploadException(MultipartFile file, String message) {
        super(message);
    }

    public FileUploadException(MultipartFile file, String messageTmpl, String... args) {
        super(messageTmpl, args);
    }
}
