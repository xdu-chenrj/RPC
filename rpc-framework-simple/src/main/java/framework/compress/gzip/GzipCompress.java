package framework.compress.gzip;

import framework.compress.Compress;

public class GzipCompress implements Compress {
    @Override
    public byte[] compress(byte[] bytes) {
        return new byte[0];
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return new byte[0];
    }
}
