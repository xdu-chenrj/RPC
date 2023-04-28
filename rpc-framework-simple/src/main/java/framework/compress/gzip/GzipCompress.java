package framework.compress.gzip;

import framework.compress.Compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipCompress implements Compress {
    private static final int BUTTER_SIZE =1024 * 4;

    @Override
    public byte[] compress(byte[] bytes) {
        if(bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(bytes);
            gzip.flush();
            gzip.finish();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("gzip compress error");
        }
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        if(bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        // TODO
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes))){
            byte[] buffer = new byte[BUTTER_SIZE];
            int n;
            while((n = gzipInputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, n);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("gzip decompress error", e);
        }
    }
}
