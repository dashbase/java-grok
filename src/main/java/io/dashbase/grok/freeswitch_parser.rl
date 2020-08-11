package io.dashbase.grok;

import java.util.Arrays;

public class FreeSwitchParser {

    public static class FreeSwitch {
        String uuid;
        String level;
        String time;
        String source_file;
        String source_line;
        String message;
    }
    %%{

        machine freeswitch;
        include common "common.rl";

        action uuid {
            event.uuid = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }

        action level {
            event.level = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }

        action time {
            event.time = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }

        action source_file {
            event.source_file = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }

        action source_line {
            event.source_line = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }

        action message {
            event.message = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }



        UUID                = [0-9a-zA-Z\-]+ >tok %uuid;


        TIME                = (CHAR+ space CHAR+) >tok %time;

        LEVEL_NAME          = alpha+  >tok %level;
        LEVEL               = '[' LEVEL_NAME ']' ;

        SOURCE_FILE         = CHAR+ > tok %source_file;
        SOURCE_LINE         = digit+ > tok %source_line;
        SOURCE              = SOURCE_FILE ":" SOURCE_LINE;


        MESSAGE = any* > tok %message;

        main := UUID SP TIME SP LEVEL SP SOURCE SP MESSAGE;

    }%%

    %% write data;



    public FreeSwitch parser(String d) {
        int cs; /* state number */
        var event = new FreeSwitch();
        char[] data = d.toCharArray(); /* input */
        int p = 0, /* start of input */
            pe = data.length, /* end of input */
            eof = pe;

        var tok = 0;

        %% write init;

        %% write exec;
        return event;
    }
}