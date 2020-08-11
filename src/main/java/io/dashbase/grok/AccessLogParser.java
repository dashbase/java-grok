
// line 1 "src/main/java/io/dashbase/grok/access_log.rl"
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




    
// line 114 "src/main/java/io/dashbase/grok/access_log.rl"


    
// line 32 "src/main/java/io/dashbase/grok/AccessLogParser.java"
private static byte[] init__accessLog_actions_0()
{
	return new byte [] {
	    0,    1,    0,    1,    1,    1,    2,    1,    3,    1,    4,    1,
	    5,    1,    6,    1,    7,    1,    8,    1,    9,    1,   10,    1,
	   11,    1,   12,    2,    0,    1
	};
}

private static final byte _accessLog_actions[] = init__accessLog_actions_0();


private static short[] init__accessLog_key_offsets_0()
{
	return new short [] {
	    0,    0,    3,    7,    8,   16,   25,   26,   27,   35,   44,   45,
	   46,   49,   51,   52,   56,   61,   63,   65,   67,   69,   70,   73,
	   75,   76,   78,   80,   81,   83,   85,   87,   90,   94,   97,  101,
	  104,  105,  106,  110,  115,  116,  117,  120,  122,  123,  128,  130,
	  134,  138,  140,  144,  146,  148,  150,  152,  156,  161,  164,  166,
	  168,  169,  171,  174,  177,  180,  183,  186,  187,  189,  191
	};
}

private static final short _accessLog_key_offsets[] = init__accessLog_key_offsets_0();


private static char[] init__accessLog_trans_keys_0()
{
	return new char [] {
	   46,   48,   57,   32,   46,   48,   57,   34,   45,   46,   48,   58,
	   65,   90,   97,  122,   34,   45,   46,   48,   58,   65,   90,   97,
	  122,   32,   34,   45,   46,   48,   58,   65,   90,   97,  122,   34,
	   45,   46,   48,   58,   65,   90,   97,  122,   32,   91,   51,   49,
	   50,   48,   57,   47,   65,   90,   97,  122,   47,   65,   90,   97,
	  122,   48,   57,   48,   57,   48,   57,   48,   57,   58,   50,   48,
	   49,   48,   57,   58,   48,   53,   48,   57,   58,   48,   53,   48,
	   57,   32,   46,   43,   45,   90,   48,   53,   54,   57,   93,   48,
	   57,   58,   93,   48,   57,   93,   48,   57,   32,   34,   65,   90,
	   97,  122,   32,   65,   90,   97,  122,   32,   32,   32,   45,   72,
	   32,   34,   32,   32,   45,   72,   48,   57,   32,   34,   32,   34,
	   45,   72,   32,   45,   48,   57,   32,   34,   32,   34,   48,   57,
	   32,   84,   32,   84,   32,   80,   32,   47,   32,   46,   48,   57,
	   32,   34,   46,   48,   57,   32,   48,   57,   48,   53,   48,   57,
	   93,   48,   57,   32,   48,   57,   32,   48,   57,   32,   48,   57,
	   32,   48,   57,   32,   48,   57,   32,   48,   51,   48,   49,   32,
	    0
	};
}

private static final char _accessLog_trans_keys[] = init__accessLog_trans_keys_0();


private static byte[] init__accessLog_single_lengths_0()
{
	return new byte [] {
	    0,    1,    2,    1,    0,    1,    1,    1,    0,    1,    1,    1,
	    1,    0,    1,    0,    1,    0,    0,    0,    0,    1,    1,    0,
	    1,    0,    0,    1,    0,    0,    2,    3,    0,    1,    2,    1,
	    1,    1,    0,    1,    1,    1,    3,    2,    1,    3,    2,    4,
	    2,    2,    2,    2,    2,    2,    2,    2,    3,    1,    0,    0,
	    1,    0,    1,    1,    1,    1,    1,    1,    0,    0,    1
	};
}

private static final byte _accessLog_single_lengths[] = init__accessLog_single_lengths_0();


private static byte[] init__accessLog_range_lengths_0()
{
	return new byte [] {
	    0,    1,    1,    0,    4,    4,    0,    0,    4,    4,    0,    0,
	    1,    1,    0,    2,    2,    1,    1,    1,    1,    0,    1,    1,
	    0,    1,    1,    0,    1,    1,    0,    0,    2,    1,    1,    1,
	    0,    0,    2,    2,    0,    0,    0,    0,    0,    1,    0,    0,
	    1,    0,    1,    0,    0,    0,    0,    1,    1,    1,    1,    1,
	    0,    1,    1,    1,    1,    1,    1,    0,    1,    1,    0
	};
}

private static final byte _accessLog_range_lengths[] = init__accessLog_range_lengths_0();


private static short[] init__accessLog_index_offsets_0()
{
	return new short [] {
	    0,    0,    3,    7,    9,   14,   20,   22,   24,   29,   35,   37,
	   39,   42,   44,   46,   49,   53,   55,   57,   59,   61,   63,   66,
	   68,   70,   72,   74,   76,   78,   80,   83,   87,   90,   93,   97,
	  100,  102,  104,  107,  111,  113,  115,  119,  122,  124,  129,  132,
	  137,  141,  144,  148,  151,  154,  157,  160,  164,  169,  172,  174,
	  176,  178,  180,  183,  186,  189,  192,  195,  197,  199,  201
	};
}

private static final short _accessLog_index_offsets[] = init__accessLog_index_offsets_0();


private static byte[] init__accessLog_indicies_0()
{
	return new byte [] {
	    0,    0,    1,    2,    3,    3,    1,    4,    1,    5,    5,    5,
	    5,    1,    6,    5,    5,    5,    5,    1,    7,    1,    8,    1,
	    9,    9,    9,    9,    1,   10,    9,    9,    9,    9,    1,   11,
	    1,   12,    1,   14,   13,    1,   15,    1,   16,    1,   17,   17,
	    1,   18,   19,   19,    1,   20,    1,   21,    1,   22,    1,   23,
	    1,   24,    1,   26,   25,    1,   27,    1,   28,    1,   29,    1,
	   30,    1,   31,    1,   32,    1,   33,    1,   34,   35,    1,   36,
	   36,   37,    1,   38,   39,    1,   41,   40,    1,   42,   41,   39,
	    1,   41,   39,    1,   43,    1,   44,    1,   45,   45,    1,   46,
	   47,   47,    1,   49,   48,   51,   50,   51,   52,   53,   50,   51,
	   54,   50,   55,   50,   51,   56,   53,   57,   50,   58,   54,   50,
	   51,   59,   52,   53,   50,   51,   60,   61,   50,   51,   62,   50,
	   51,   62,   63,   50,   51,   64,   50,   51,   65,   50,   51,   66,
	   50,   51,   67,   50,   51,   68,   68,   50,   51,   54,   68,   68,
	   50,   58,   69,   50,   70,    1,   37,    1,   41,    1,   71,    1,
	   72,   73,    1,   72,   74,    1,   72,   75,    1,   72,   76,    1,
	   72,   77,    1,   72,    1,   27,    1,   15,    1,   51,   50,    0
	};
}

private static final byte _accessLog_indicies[] = init__accessLog_indicies_0();


private static byte[] init__accessLog_trans_targs_0()
{
	return new byte [] {
	    2,    0,    3,    2,    4,    5,    6,    7,    8,    9,   10,   11,
	   12,   13,   69,   14,   15,   16,   17,   16,   18,   19,   20,   21,
	   22,   23,   68,   24,   25,   26,   27,   28,   29,   30,   31,   61,
	   32,   60,   33,   35,   34,   36,   58,   37,   38,   39,   40,   39,
	   41,   42,   41,   42,   43,   51,   44,   45,   46,   57,   47,   48,
	   49,   50,   70,   50,   52,   53,   54,   55,   56,   57,   59,   62,
	   31,   63,   64,   65,   66,   67
	};
}

private static final byte _accessLog_trans_targs[] = init__accessLog_trans_targs_0();


private static byte[] init__accessLog_trans_actions_0()
{
	return new byte [] {
	    1,    0,    7,    0,    1,    0,    0,    9,    1,    0,    0,   11,
	    0,    1,    1,    0,   17,    1,   15,    0,    1,    0,    0,    0,
	   13,    1,    1,    0,   19,    1,    0,   21,    1,    0,   23,   23,
	    1,    0,    0,    0,    0,    0,    0,    0,    0,    1,    5,    0,
	    1,   27,    0,    3,    1,    1,    0,    3,    1,    1,    3,    0,
	    1,    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
	   25,    0,    0,    0,    0,    0
	};
}

private static final byte _accessLog_trans_actions[] = init__accessLog_trans_actions_0();


static final int accessLog_start = 1;
static final int accessLog_first_final = 70;
static final int accessLog_error = 0;

static final int accessLog_en_main = 1;


// line 117 "src/main/java/io/dashbase/grok/access_log.rl"



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

        
// line 221 "src/main/java/io/dashbase/grok/AccessLogParser.java"
	{
	cs = accessLog_start;
	}

// line 141 "src/main/java/io/dashbase/grok/access_log.rl"

        
// line 229 "src/main/java/io/dashbase/grok/AccessLogParser.java"
	{
	int _klen;
	int _trans = 0;
	int _acts;
	int _nacts;
	int _keys;
	int _goto_targ = 0;

	_goto: while (true) {
	switch ( _goto_targ ) {
	case 0:
	if ( p == pe ) {
		_goto_targ = 4;
		continue _goto;
	}
	if ( cs == 0 ) {
		_goto_targ = 5;
		continue _goto;
	}
case 1:
	_match: do {
	_keys = _accessLog_key_offsets[cs];
	_trans = _accessLog_index_offsets[cs];
	_klen = _accessLog_single_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + _klen - 1;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + ((_upper-_lower) >> 1);
			if ( data[p] < _accessLog_trans_keys[_mid] )
				_upper = _mid - 1;
			else if ( data[p] > _accessLog_trans_keys[_mid] )
				_lower = _mid + 1;
			else {
				_trans += (_mid - _keys);
				break _match;
			}
		}
		_keys += _klen;
		_trans += _klen;
	}

	_klen = _accessLog_range_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + (_klen<<1) - 2;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + (((_upper-_lower) >> 1) & ~1);
			if ( data[p] < _accessLog_trans_keys[_mid] )
				_upper = _mid - 2;
			else if ( data[p] > _accessLog_trans_keys[_mid+1] )
				_lower = _mid + 2;
			else {
				_trans += ((_mid - _keys)>>1);
				break _match;
			}
		}
		_trans += _klen;
	}
	} while (false);

	_trans = _accessLog_indicies[_trans];
	cs = _accessLog_trans_targs[_trans];

	if ( _accessLog_trans_actions[_trans] != 0 ) {
		_acts = _accessLog_trans_actions[_trans];
		_nacts = (int) _accessLog_actions[_acts++];
		while ( _nacts-- > 0 )
	{
			switch ( _accessLog_actions[_acts++] )
			{
	case 0:
// line 4 "src/main/java/io/dashbase/grok/common.rl"
	{
    tok = p;
  }
	break;
	case 1:
// line 29 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            event.put("message", new Entity(tok, p));
        }
	break;
	case 2:
// line 33 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            event.put("verb", new Entity(tok, p));
        }
	break;
	case 3:
// line 37 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            event.put("ip", new Entity(tok, p));
        }
	break;
	case 4:
// line 40 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            event.put("ident", new Entity(tok, p));
        }
	break;
	case 5:
// line 44 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            event.put("auth", new Entity(tok, p));
        }
	break;
	case 6:
// line 48 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            year = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
	break;
	case 7:
// line 52 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            month = parseMothStr(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
	break;
	case 8:
// line 55 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            day = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
	break;
	case 9:
// line 59 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            hour = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
	break;
	case 10:
// line 63 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            minute = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
	break;
	case 11:
// line 67 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            second = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
	break;
	case 12:
// line 70 "src/main/java/io/dashbase/grok/access_log.rl"
	{
            nano = Integer.parseInt(String.valueOf(Arrays.copyOfRange(data, tok, p)));
        }
	break;
// line 387 "src/main/java/io/dashbase/grok/AccessLogParser.java"
			}
		}
	}

case 2:
	if ( cs == 0 ) {
		_goto_targ = 5;
		continue _goto;
	}
	if ( ++p != pe ) {
		_goto_targ = 1;
		continue _goto;
	}
case 4:
case 5:
	}
	break; }
	}

// line 143 "src/main/java/io/dashbase/grok/access_log.rl"

        var date = LocalDateTime.of(year, month, day, hour,second, minute,nano);

        return event;
    }
}