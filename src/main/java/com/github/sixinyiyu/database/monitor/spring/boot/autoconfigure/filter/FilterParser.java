package com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.filter;

import static java.io.StreamTokenizer.TT_EOF;
import static java.io.StreamTokenizer.TT_WORD;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FilterParser {

	private StreamTokenizer tokenizer;
	private StringReader stringReader;
	private final String input;

	public FilterParser(String input) {
		this.input = input;
	}

	public List<FilterPattern> parse()  {
		this.stringReader = new StringReader(input);

		this.tokenizer = new StreamTokenizer(stringReader);
		try {
			return doParse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private List<FilterPattern> doParse() throws IOException {
		ArrayList<FilterPattern> patterns = new ArrayList<>();
		tokenizer.ordinaryChar('.'); // 指定.为普通符号
		tokenizer.ordinaryChar('/');
		tokenizer.wordChars('_', '_');

		tokenizer.ordinaryChars('0', '9');
		tokenizer.wordChars('0', '9');

		tokenizer.quoteChar('`');
		tokenizer.quoteChar('\'');
		tokenizer.quoteChar('"');

		tokenizer.nextToken();

		FilterPattern p;
		while ((p = parseFilterPattern()) != null)
			patterns.add(p);

		return patterns;
	}

	private void skipToken(char token) throws IOException {
		if (tokenizer.ttype != token) {
			throw new IOException("Expected '" + token + "', saw: " + tokenizer.toString());
		}
		tokenizer.nextToken();
	}

	private FilterPattern parseFilterPattern() throws IOException {
		System.out.println(tokenizer.ttype);
		if (tokenizer.ttype == TT_EOF) // 到了流末尾
			return null;

//		if (tokenizer.ttype != TT_WORD)//已读到一个文字标记的常量
//			throw new IOException("expected [include, exclude, blacklist] in filter definition.");

//		tokenizer.nextToken();

		Pattern dbPattern = parsePattern();
		skipToken('.'); // database.table 
		Pattern tablePattern = parsePattern();

		FilterPattern ret = new FilterPattern(dbPattern, tablePattern);

		if (tokenizer.ttype == ',') {//多个分隔
			tokenizer.nextToken();
		}

		return ret;
	}

	private Pattern parsePattern() throws IOException {
		Pattern pattern;
		switch (tokenizer.ttype) {
		case '/':
			pattern = Pattern.compile(parseRegexp());
			break;
		case '*':
			pattern = Pattern.compile("");
			break;
		case TT_WORD:
		case '`':
		case '\'':
		case '"':
			pattern = Pattern.compile("^" + tokenizer.sval + "$");
			break;
		default:
			throw new IOException("Expected string or regexp, saw '" + Character.toString((char) tokenizer.ttype));
		}
		tokenizer.nextToken();
		return pattern;
	}

	private String parseRegexp() throws IOException {
		char ch, lastChar = 0;
		String s = "";
		while (true) {
			ch = (char) stringReader.read();
			if (ch == '/' && lastChar != '\\')
				break;

			s += ch;
			lastChar = ch;
		}
		return s;
	}
	
	
	public static void main(String[] args) throws IOException {
		String text = "sound_test.*,sound.user";
		
		FilterParser parse = new FilterParser(text);
		
		parse.parse();
	}
}
