package com.hthjsj.web.kit;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * <p> @Date : 2021/9/9 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class HttpKit {
    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder ret;
            br = request.getReader();

            String line = br.readLine();
            if (line != null) {
                ret = new StringBuilder();
                ret.append(line);
            } else {
                return "";
            }

            while ((line = br.readLine()) != null) {
                ret.append('\n').append(line);
            }

            return ret.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		/* 去掉 close() 否则后续 ActionReporter 中的 getPara() 在部分 tomcat 中会报 IOException : Stream closed
		finally {
			if (br != null) {
				try {br.close();} catch (IOException e) {LogKit.error(e.getMessage(), e);}
			}
		}*/
    }

}
