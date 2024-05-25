package br.com.scheiner.exemplo.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.util.StreamUtils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class CachedHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private byte[] cache;

	public CachedHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.cache = StreamUtils.copyToByteArray(super.getInputStream());
	}

	@Override
	public ServletInputStream getInputStream() {
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(this.cache);

		return new ServletInputStream() {
			
			@Override
			public boolean isFinished() {
				return inputStream.available() == 0;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public int read() {
				return inputStream.read();
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				throw new UnsupportedOperationException();
			}
		};
	}

	public byte[] getContentAsByteArray() {
		return this.cache;
	}
}