package io.dashbase.grok;

import java.util.Arrays;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.Month;
import static io.dashbase.grok.Helper.parseMothStr;

public class AccessLogParser {

 public static class Entity {
        public int from;
        public int to;
         public Entity(int from, int to) {
            this.from = from;
            this.to = to;

        }
    }




    %%{

        machine accessLog;
        include common "common.rl";

        action message {
            event.put("message", new Entity(tok, p));
        }

        action verb {
            event.put("verb", new Entity(tok, p));
        }

        action ip {
            event.put("ip", new Entity(tok, p));
        }
        action ident{
            event.put("ident", new Entity(tok, p));
        }

        action auth{
            event.put("auth", new Entity(tok, p));
        }

        action year {
            year = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }

        action month {
            month = parseMothStr(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
        action day {
            day = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }

        action hour {
            hour = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }

        action minute{
            minute = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }

        action second{
            second = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
        action nanosecond{
            nano = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
          # timestamp
          DATE_FULLYEAR   = digit{4}>tok %year;
          DATE_MONTH      = [a-zA-Z]+ >tok %month;
          #DATE_MONTH      = (("0"[1-9]) | ("1"[0-2]))>tok %month;
          DATE_MDAY       = (([12][0-9]) | ("3"[01]))>tok %day;
          FULL_DATE       = DATE_MDAY "/" DATE_MONTH "/" DATE_FULLYEAR;

          TIME_HOUR       = ([01][0-9] | "2"[0-3])>tok %hour;
          TIME_MINUTE     = ([0-5][0-9])>tok %minute;
          TIME_SECOND     = ([0-5][0-9])>tok %second;
          TIME_SECFRAC    = '.' digit{1,6}>tok %nanosecond;
          TIME_NUMOFFSET  = ('+' | '-') (([0-5][0-9]) ':' ([0-5][0-9]) |[0-9]+);
          TIME_OFFSET     = 'Z' | TIME_NUMOFFSET >tok ;
          PARTIAL_TIME    = TIME_HOUR ":" TIME_MINUTE ":" TIME_SECOND  TIME_SECFRAC?;
          FULL_TIME       = PARTIAL_TIME SP TIME_OFFSET;


          # 25/Feb/2018:23:10:39 +0000;


        IP = [0-9.] + > tok % ip;
        NIL = "-";
        STRING_DATA = DB_QUOTE (alnum|[\-:.])+ DB_QUOTE;

        HTTP_DATE = "[" FULL_DATE ":" FULL_TIME"]";

        VERB = alpha+ > tok % verb;

        IDENT= STRING_DATA > tok %ident;

        AUTH= STRING_DATA > tok %auth;
        HTTP_VERSION = (NIL | "HTTP/" [0-9.]+) >tok;
        RESPONSE = (NIL | digit+) >tok;
        BYTE = (NIL | digit+) >tok;

        MESSAGE = any* > tok %message;

        main := IP SP IDENT SP AUTH SP HTTP_DATE SP
                DB_QUOTE VERB SP MESSAGE SP HTTP_VERSION DB_QUOTE
                SP RESPONSE SP DB_QUOTE BYTE DB_QUOTE;

    }%%

    %% write data;



    public HashMap<String, Entity> parser(String d) {
        int cs; /* state number */
        var event = new HashMap<String, Entity>();
        char[] data = d.toCharArray(); /* input */
        int p = 0, /* start of input */
            pe = data.length, /* end of input */
            eof = pe;

        int year = 1970,
            month = 1,
            day =1,
            hour =0,
            second = 0,
            minute =0,
            nano = 0;



        var tok = 0;

        %% write init;

        %% write exec;

        var date = LocalDateTime.of(year, month, day, hour,second, minute,nano);

        return event;
    }
}