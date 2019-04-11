package demo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Controller
public class CacheTestController {

    @RequestMapping("/cache")
    public ResponseEntity<String> cache(@RequestHeader(value = "If-Modified-Since",required = false)Date ifModifiedSince) throws ExecutionException {
        DateFormat gmtDateFormate=new SimpleDateFormat("EEE,d MM HH:mm:ss 'GMT'", Locale.US);
        long lastModifiedMills=getLastModified()/1000*1000;
        long now=System.currentTimeMillis()/1000*1000;
        long maxAge=20;
        if(ifModifiedSince!=null){
            System.out.println("since:"+ifModifiedSince.getTime());
        }

        if(ifModifiedSince!=null && ifModifiedSince.getTime()==lastModifiedMills){
            MultiValueMap<String,String> headers=new HttpHeaders();
            headers.add("Date",gmtDateFormate.format(new Date(now)));
            headers.add("Expires",gmtDateFormate.format(new Date(now+maxAge*1000)));
            headers.add("Cache-Control","max-age="+maxAge);
            return new ResponseEntity<String>(headers, HttpStatus.NOT_MODIFIED);
        }
        String body="<a href''>点击访问当前连接</a>";
        MultiValueMap headers=new HttpHeaders();
        headers.add("Date",gmtDateFormate.format(new Date(now)));
        headers.add("Last-Modified",gmtDateFormate.format(new Date(lastModifiedMills)));
        headers.add("Expires",gmtDateFormate.format(new Date(now+maxAge*1000)));
        headers.add("Cache-Control","max-age="+maxAge);
        lastModifiedCache.put("lastModified",lastModifiedMills);
        return new ResponseEntity<String>(body,headers, HttpStatus.OK);

    }

   Cache<String,Long> lastModifiedCache=CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
    public long getLastModified() throws ExecutionException{
        long lastModified= lastModifiedCache.get("lastModified",()->System.currentTimeMillis());
        System.out.println(lastModified);
        return lastModified;
    }



}
