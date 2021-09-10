package com.hthjsj.web.upload;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.*;

/**
 * 基于Apache Common FileUpload的MultipartRequest
 * JFinal com.jfinal.upload.MultipartRequest 在与spring 一起使用时会出现java.io.IOException: Corrupt form data: premature ending
 *
 * <p> @Date : 2020/7/28 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MultipartRequest extends HttpServletRequestWrapper {

    private static String baseUploadPath;

    private static int maxPostSize;

    private static String encoding;

    private List<UploadFile> uploadFiles;

    private ServletFileUpload upload;

    private Map<String, String> params = new HashMap<String, String>();

    private boolean sizeLimitExceeded;

    public MultipartRequest(HttpServletRequest request, String baseUploadPath, int maxPostSize, String encoding) {
        super(request);
        wrapMultipartRequest(request, getFinalPath(baseUploadPath), maxPostSize, encoding);
    }

    public MultipartRequest(HttpServletRequest request, String baseUploadPath, int maxPostSize) {
        super(request);
        wrapMultipartRequest(request, getFinalPath(baseUploadPath), maxPostSize, encoding);
    }

    public MultipartRequest(HttpServletRequest request, String baseUploadPath) {
        super(request);
        wrapMultipartRequest(request, getFinalPath(baseUploadPath), maxPostSize, encoding);
    }

    public MultipartRequest(HttpServletRequest request) {
        super(request);
        wrapMultipartRequest(request, getFinalPath(baseUploadPath), maxPostSize, encoding);
    }

    public static void init(String saveDirectory, int maxPostSize, String encoding) {
        MultipartRequest.baseUploadPath = saveDirectory;
        MultipartRequest.maxPostSize = maxPostSize;
        MultipartRequest.encoding = encoding;
    }

    /**
     * 路径允许为 "" 值，表示直接使用基础路径 baseUploadPath
     */
    private String getFinalPath(String uploadPath) {
        if (uploadPath == null) {
            throw new IllegalArgumentException("uploadPath can not be null.");
        }
        if (!uploadPath.endsWith("/")) {
            uploadPath = uploadPath + "/";
        }
        uploadPath = uploadPath.trim();
        if (uploadPath.startsWith("/") || uploadPath.startsWith("\\")) {
            if (baseUploadPath.equals("/")) {
                return uploadPath;
            } else {
                return baseUploadPath + uploadPath;
            }
        } else {
            return baseUploadPath + File.separator + uploadPath;
        }
    }

    private void wrapMultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize, String encoding) {

        File dir = new File(saveDirectory);
        if (!dir.exists())
            if (!dir.mkdirs())
                throw new RuntimeException("Directory " + saveDirectory + " not exists and can not create directory.");

        if (params == null)
            params = new HashMap<String, String>();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(JFinal.me().getConstants().getMaxPostSize());
        uploadFiles = new ArrayList<UploadFile>();

        FileItemIterator iter;

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            iter = upload.getItemIterator(request);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String paramName = item.getFieldName();
                InputStream stream = item.openStream();
                if (item.isFormField()) {
                    String value = Streams.asString(stream);
                    params.put(paramName, value);
                } else {
                    String originalfileName = item.getName();
                    String uploadedFileName = System.currentTimeMillis() + originalfileName;
                    String contentType = item.getContentType();

                    String filePath = saveDirectory + uploadedFileName;
                    File file = new File(filePath);
                    bis = new BufferedInputStream(stream);
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    copy(bis, bos);

                    UploadFile uploadFile = new UploadFile(paramName, saveDirectory, uploadedFileName, originalfileName, contentType);
                    if (isSafeFile(uploadFile))
                        uploadFiles.add(uploadFile);
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        } catch (SizeLimitExceededException e) {
            this.sizeLimitExceeded = true;
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void copy(InputStream in, OutputStream out) throws IOException, SizeLimitExceededException {
        int contentLength = 0;
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
            contentLength += len;
            if (contentLength > JFinal.me().getConstants().getMaxPostSize())
                throw new SizeLimitExceededException();
        }
    }

    public boolean isSizeLimitExceeded() {
        return sizeLimitExceeded;
    }

    private boolean isSafeFile(UploadFile uploadFile) {
        if (uploadFile.getFileName().toLowerCase().endsWith(".jsp")) {
            uploadFile.getFile().delete();
            return false;
        }
        return true;
    }

    public List<UploadFile> getFiles() {
        return uploadFiles;
    }

    /**
     * Methods to replace HttpServletRequest methods
     */
    @Override
    public Enumeration getParameterNames() {
        if (params == null)
            return null;
        return new Enumeration<String>() {

            private final Iterator<String> keySetIter = params.keySet().iterator();

            @Override
            public String nextElement() {
                return keySetIter.next();
            }

            @Override
            public boolean hasMoreElements() {
                return keySetIter.hasNext();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        return params.get(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[] { params.get(name) };
    }

    @Override
    public Map getParameterMap() {
        Map map = new HashMap();
        Enumeration enumm = getParameterNames();
        while (enumm.hasMoreElements()) {
            String name = (String) enumm.nextElement();
            map.put(name, getParameterValues(name));
        }
        return map;
    }
}
