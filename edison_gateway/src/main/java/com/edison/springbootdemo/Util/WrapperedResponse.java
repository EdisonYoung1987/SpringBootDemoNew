package com.edison.springbootdemo.Util;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class WrapperedResponse extends HttpServletResponseWrapper {
    private ByteArrayOutputStream buffer=null;
    private ServletOutputStream out=null;
    private PrintWriter writer=null;

    public WrapperedResponse(HttpServletResponse response) throws UnsupportedEncodingException {
        super(response);
        buffer=new ByteArrayOutputStream();
        out=new WrapperedOutputStream(buffer);
//        writer=new PrintWriter(new OutputStreamWriter(buffer,this.getCharacterEncoding()));
        writer=new PrintWriter(new OutputStreamWriter(buffer,"UTF-8"));
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return  out;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if(out!=null) {
            out.flush();
        }
        if(writer!=null) {
            writer.flush();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    //将out、writer中的数据强制输出到WrapperedResponse的buffer中，否则娶不到数据
    public byte[] getResponseData() throws IOException{
        flushBuffer();
        return  buffer.toByteArray();
    }

    private class WrapperedOutputStream extends  ServletOutputStream{
        private ByteArrayOutputStream bos=null;

        public WrapperedOutputStream(ByteArrayOutputStream stream){
            bos=stream;
        }

        @Override
        public void write(byte[] b) throws IOException {
            bos.write(b);
        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}
