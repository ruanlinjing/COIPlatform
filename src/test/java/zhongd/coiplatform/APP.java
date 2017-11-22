package zhongd.coiplatform;

import java.util.Calendar;
import java.util.TimeZone;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import zhongd.coiplatform.utils.Constant;

public class APP {
	@Test
	public void ggg() {
		RandomNumberGenerator rd = new SecureRandomNumberGenerator();
		String algorithmName = Constant.MD5_STR;
		int hashIterations = 1;
		// 以用户名作为'盐'
		String password = new SimpleHash(algorithmName, "pass",
                ByteSource.Util.bytes("333"), hashIterations).toHex();
		System.out.println(password);
    }
	@Test
	public void hhh() {
		Calendar cal = Calendar.getInstance();
		TimeZone timeZone = cal.getTimeZone();
		System.out.println(timeZone.getID());
		System.out.println(timeZone.getDisplayName());
	}
}
