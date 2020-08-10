
// line 1 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"
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
    
// line 63 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"


    
// line 23 "src/main/java/io/dashbase/grok/FreeSwitchParser.java"
private static byte[] init__freeswitch_actions_0()
{
	return new byte [] {
	    0,    1,    0,    1,    1,    1,    2,    1,    3,    1,    4,    1,
	    5,    1,    6,    2,    6,    5
	};
}

private static final byte _freeswitch_actions[] = init__freeswitch_actions_0();


private static byte[] init__freeswitch_key_offsets_0()
{
	return new byte [] {
	    0,    0,    7,   15,   24,   36,   45,   55,   56,   60,   65,   66,
	   75,   85,   95,  106,  106
	};
}

private static final byte _freeswitch_key_offsets[] = init__freeswitch_key_offsets_0();


private static char[] init__freeswitch_trans_keys_0()
{
	return new char [] {
	   45,   48,   57,   65,   90,   97,  122,   32,   45,   48,   57,   65,
	   90,   97,  122,   95,   45,   46,   48,   58,   65,   90,   97,  122,
	   32,   95,    9,   13,   45,   46,   48,   58,   65,   90,   97,  122,
	   95,   45,   46,   48,   58,   65,   90,   97,  122,   32,   95,   45,
	   46,   48,   58,   65,   90,   97,  122,   91,   65,   90,   97,  122,
	   93,   65,   90,   97,  122,   32,   95,   45,   46,   48,   58,   65,
	   90,   97,  122,   58,   95,   45,   46,   48,   57,   65,   90,   97,
	  122,   58,   95,   45,   46,   48,   57,   65,   90,   97,  122,   32,
	   58,   95,   45,   46,   48,   57,   65,   90,   97,  122,    0
	};
}

private static final char _freeswitch_trans_keys[] = init__freeswitch_trans_keys_0();


private static byte[] init__freeswitch_single_lengths_0()
{
	return new byte [] {
	    0,    1,    2,    1,    2,    1,    2,    1,    0,    1,    1,    1,
	    2,    2,    3,    0,    0
	};
}

private static final byte _freeswitch_single_lengths[] = init__freeswitch_single_lengths_0();


private static byte[] init__freeswitch_range_lengths_0()
{
	return new byte [] {
	    0,    3,    3,    4,    5,    4,    4,    0,    2,    2,    0,    4,
	    4,    4,    4,    0,    0
	};
}

private static final byte _freeswitch_range_lengths[] = init__freeswitch_range_lengths_0();


private static byte[] init__freeswitch_index_offsets_0()
{
	return new byte [] {
	    0,    0,    5,   11,   17,   25,   31,   38,   40,   43,   47,   49,
	   55,   62,   69,   77,   78
	};
}

private static final byte _freeswitch_index_offsets[] = init__freeswitch_index_offsets_0();


private static byte[] init__freeswitch_indicies_0()
{
	return new byte [] {
	    0,    0,    0,    0,    1,    2,    3,    3,    3,    3,    1,    4,
	    4,    4,    4,    4,    1,    5,    6,    5,    6,    6,    6,    6,
	    1,    7,    7,    7,    7,    7,    1,    8,    7,    7,    7,    7,
	    7,    1,    9,    1,   10,   10,    1,   12,   11,   11,    1,   13,
	    1,   14,   14,   14,   14,   14,    1,   16,   15,   15,   15,   15,
	   15,    1,   16,   15,   15,   17,   15,   15,    1,   18,   16,   15,
	   15,   19,   15,   15,    1,   20,   21,    0
	};
}

private static final byte _freeswitch_indicies[] = init__freeswitch_indicies_0();


private static byte[] init__freeswitch_trans_targs_0()
{
	return new byte [] {
	    2,    0,    3,    2,    4,    5,    4,    6,    7,    8,    9,    9,
	   10,   11,   12,   12,   13,   14,   15,   14,   16,   16
	};
}

private static final byte _freeswitch_trans_targs[] = init__freeswitch_trans_targs_0();


private static byte[] init__freeswitch_trans_actions_0()
{
	return new byte [] {
	   13,    0,    1,    0,   13,    0,    0,    0,    5,    0,   13,    0,
	    3,    0,   13,    0,    7,   13,    9,    0,   13,    0
	};
}

private static final byte _freeswitch_trans_actions[] = init__freeswitch_trans_actions_0();


private static byte[] init__freeswitch_eof_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,   15,   11
	};
}

private static final byte _freeswitch_eof_actions[] = init__freeswitch_eof_actions_0();


static final int freeswitch_start = 1;
static final int freeswitch_first_final = 15;
static final int freeswitch_error = 0;

static final int freeswitch_en_main = 1;


// line 66 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"



    public FreeSwitch parser(String d) {
        int cs; /* state number */
        var event = new FreeSwitch();
        char[] data = d.toCharArray(); /* input */
        int p = 0, /* start of input */
            pe = data.length, /* end of input */
            eof = pe;

        var tok = 0;

        
// line 168 "src/main/java/io/dashbase/grok/FreeSwitchParser.java"
	{
	cs = freeswitch_start;
	}

// line 80 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"

        
// line 176 "src/main/java/io/dashbase/grok/FreeSwitchParser.java"
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
	_keys = _freeswitch_key_offsets[cs];
	_trans = _freeswitch_index_offsets[cs];
	_klen = _freeswitch_single_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + _klen - 1;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + ((_upper-_lower) >> 1);
			if ( data[p] < _freeswitch_trans_keys[_mid] )
				_upper = _mid - 1;
			else if ( data[p] > _freeswitch_trans_keys[_mid] )
				_lower = _mid + 1;
			else {
				_trans += (_mid - _keys);
				break _match;
			}
		}
		_keys += _klen;
		_trans += _klen;
	}

	_klen = _freeswitch_range_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + (_klen<<1) - 2;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + (((_upper-_lower) >> 1) & ~1);
			if ( data[p] < _freeswitch_trans_keys[_mid] )
				_upper = _mid - 2;
			else if ( data[p] > _freeswitch_trans_keys[_mid+1] )
				_lower = _mid + 2;
			else {
				_trans += ((_mid - _keys)>>1);
				break _match;
			}
		}
		_trans += _klen;
	}
	} while (false);

	_trans = _freeswitch_indicies[_trans];
	cs = _freeswitch_trans_targs[_trans];

	if ( _freeswitch_trans_actions[_trans] != 0 ) {
		_acts = _freeswitch_trans_actions[_trans];
		_nacts = (int) _freeswitch_actions[_acts++];
		while ( _nacts-- > 0 )
	{
			switch ( _freeswitch_actions[_acts++] )
			{
	case 0:
// line 19 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"
	{
            event.uuid = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }
	break;
	case 1:
// line 23 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"
	{
            event.level = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }
	break;
	case 2:
// line 27 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"
	{
            event.time = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }
	break;
	case 3:
// line 31 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"
	{
            event.source_file = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }
	break;
	case 4:
// line 35 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"
	{
            event.source_line = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }
	break;
	case 6:
// line 4 "src/main/java/io/dashbase/grok/common.rl"
	{
    tok = p;
  }
	break;
// line 292 "src/main/java/io/dashbase/grok/FreeSwitchParser.java"
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
	if ( p == eof )
	{
	int __acts = _freeswitch_eof_actions[cs];
	int __nacts = (int) _freeswitch_actions[__acts++];
	while ( __nacts-- > 0 ) {
		switch ( _freeswitch_actions[__acts++] ) {
	case 5:
// line 39 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"
	{
            event.message = String.valueOf(Arrays.copyOfRange(data, tok, p)) ;
        }
	break;
	case 6:
// line 4 "src/main/java/io/dashbase/grok/common.rl"
	{
    tok = p;
  }
	break;
// line 325 "src/main/java/io/dashbase/grok/FreeSwitchParser.java"
		}
	}
	}

case 5:
	}
	break; }
	}

// line 82 "src/main/java/io/dashbase/grok/freeswitch_parser.rl"
        return event;
    }
}