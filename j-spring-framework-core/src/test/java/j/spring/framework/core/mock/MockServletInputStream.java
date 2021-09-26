package j.spring.framework.core.mock;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MockServletInputStream extends ServletInputStream {

    private InputStream inputStream;

    private boolean finished;
    private boolean closed;


    public MockServletInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    @Override
    public int read() throws IOException {
        if (closed) {
            throw new IOException("MockServletInputStreamClosed");
        }

        int data = this.inputStream.read();
        if (data == -1) {
            this.finished = true;
        }
        return data;
    }

    @Override
    public int available() throws IOException {
        return this.inputStream.available();
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.inputStream.close();
        this.closed = true;
    }


    @Override
    public boolean isFinished() {
        return this.finished;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

}
