package com.org.mokey.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 */
public final class ResponseUtil {
    private final static int MAX_BUFFER_SIZE = 1024;
    private final static String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    private ResponseUtil() {
    }

    /**
     * Bolb����
     * @param response
     * @param blobdata
     * @param filename
     */
    public static void download(HttpServletResponse response, Blob blob,
            String filename) {
        if (blob == null) {
            return;
        }
        try {
            byte[]ab = blob.getBytes(1, (int) blob.length());
            download(response, new ByteArrayInputStream(ab), filename, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * inputStream����
     * @param response
     * @param blobdata
     * @param filename
     */
    public static void download(HttpServletResponse response, InputStream in, String filename, String contentType) {
        if (in == null) {
            return;
        }

        OutputStream out = null;
        try {
            if(contentType == null) {
                contentType = DEFAULT_CONTENT_TYPE;
            }
            response.setContentType(contentType);
            out = response.getOutputStream();
            byte[] buffer = new byte[MAX_BUFFER_SIZE];
            while (in.available() > 0) {
                int len = in.read(buffer);
                out.write(buffer, 0, len);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

    }

    /**
     * ���ָ����File���󣬽������ز�����ʹ��Ĭ�ϵ�application/octet-stream��ΪMimeType
     * @param response servlet response����
     * @param file ָ����File����ʵ��
     * @throws IOException ���ع���з����IO�쳣
     */
    public final static void download(HttpServletResponse response, File file) throws IOException{
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        String fileName = file.getName();
        download(response, in, (int)file.length(), fileName, DEFAULT_CONTENT_TYPE);
    }

    /**
     * ���ָ�����ֽ�����������ز�����ʹ��Ĭ�ϵ�application/octet-stream��ΪMimeType
     * @param response servlet response ����
     * @param data ָ�����ֽ�����
     * @param filename ָ�����ڿͻ������������ʾ���ļ���
     * @throws IOException ���ع���з����IO�쳣
     */
    public final static void download(HttpServletResponse response, byte[] data, String filename) throws IOException{
        if (data == null || data.length == 0) {
            return;
        }

        download(response, new ByteArrayInputStream(data), data.length, filename, DEFAULT_CONTENT_TYPE);
    }

    /**
     * ���ָ����������������ز�����ʹ��Ĭ�ϵ�application/octet-stream��ΪMimeType
     * @param response servlet response����
     * @param in ����������
     * @param length ������������Ҫ��ȡ���ֽ�����
     * @param filename ��Ӧ���ͻ������������ʾ���ļ���
     * @throws IOException ���ع���з����IO�쳣
     */
    public final static void download(HttpServletResponse response, InputStream in, int length, String filename) throws IOException{
        download(response, in, length, filename, DEFAULT_CONTENT_TYPE);
    }

    /**
     * ���ָ�����ļ�����������ز�����ʹ��ָ����MimeType��ʹ���ļ�������Ϊ���������ʾ���ļ���
     * @param response server response����
     * @param file ָ�����ļ�����
     * @param mimeType ָ����mimeType
     * @throws IOException ���ع���з����IO�쳣
     */
    public final static void download(HttpServletResponse response, File file, String mimeType) throws IOException{
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        String fileName = file.getName();
        download(response, in, (int)file.length(), fileName, mimeType);
    }

    /**
     * ���ָ�����ֽ�����������ز�����ʹ��ָ����MimeType��ָ�����ļ�����ʾ
     * @param response servlet response����
     * @param data �ֽ��������
     * @param filename ��Ӧ���ͻ������������ʾ���ļ���
     * @param mimeType ָ����mimeType
     * @throws IOException ���ع���з����IO�쳣
     */
    public final static void download(HttpServletResponse response, byte[] data, String filename, String mimeType) throws IOException{
        if (data == null || data.length == 0) {
            return;
        }

        download(response, new ByteArrayInputStream(data), data.length, filename, mimeType);
    }

    /**
     * ���ָ�����������������ز�����ʹ��ָ����MimeType��ָ�����ļ�����ʾ
     * @param response servlet response ����
     * @param in ������
     * @param contentLength ��Ҫ���ص���ݳ���
     * @param filename ��Ӧ���ͻ������������ʾ���ļ���
     * @param mimeType ָ����mimeType
     * @throws IOException ���ع���з����IO�쳣
     */
    public final static void download(HttpServletResponse response, InputStream in, int contentLength, String filename, String mimeType) throws IOException{

        response.reset();
        response.setContentLength(contentLength);

        //ISO8859_1
        //must encode charset to Unicode
        String s = new String(filename.getBytes("gb2312"), "ISO8859_1");
        response.addHeader("Content-Disposition",
                "attachment; filename=\"" + s + "\"");
        response.setContentType(mimeType);

        OutputStream out = null;
        try{
            out = response.getOutputStream();
            byte[] buffer = new byte[MAX_BUFFER_SIZE];
            while (in.available() > 0) {
                int len = in.read(buffer);
                out.write(buffer, 0, len);
                out.flush();
            }
        } 
        finally{
            try {
                in.close();
            } catch (IOException e) { }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {}
            }
        }
    }
}