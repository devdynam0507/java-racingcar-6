package racingcar.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Streams {

    public static InputStream stringToByteInputStream(String str) {
        final byte[] stringByteBuf = str.getBytes();
        return new ByteArrayInputStream(stringByteBuf);
    }
}
