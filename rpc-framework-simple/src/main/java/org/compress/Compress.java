package org.compress;

import org.common.extension.SPI;

@SPI
public interface Compress {

    byte[] compress(byte[] bytes);
    byte[] decompress(byte[] bytes);
}
