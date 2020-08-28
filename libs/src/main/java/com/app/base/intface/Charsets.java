package com.app.base.intface;

import java.nio.charset.Charset;

public enum Charsets {

    GBK("GBK"),
    UTF_8("UTF-8"),
    ASCII("ASCII"),
    CESU_8("CESU-8"),
    UTF_16("UTF-16"),
    UTF_16BE("UTF-16BE"),
    UTF_16LE("UTF-16LE"),
    UTF_16LE_BOM("UTF-16LE-BOM"),
    UTF_32("UTF-32"),
    UTF_32LE("UTF-32LE"),
    UTF_32BE("UTF-32BE"),
    UTF_32LE_BOM("UTF-32LE-BOM"),
    UTF_32BE_BOM("UTF-32BE-BOM"),
    ISO_8859_1("ISO-8859-1"),
    ISO_8859_2("ISO-8859-2"),
    ISO_8859_4("ISO-8859-4"),
    ISO_8859_5("ISO-8859-5"),
    ISO_8859_7("ISO-8859-7"),
    ISO_8859_9("ISO-8859-9"),
    ISO_8859_13("ISO-8859-13"),
    ISO_8859_15("ISO-8859-15"),
    KOI8_R("KOI8-R"),
    KOI8_U("KOI8_U"),
    MS1250("MS1250"),
    MS1251("MS1251"),
    MS1252("MS1252"),
    MS1253("MS1253"),
    MS1254("MS1254"),
    MS1257("MS1257"),
    IBM437("IBM437"),
    IBM737("IBM737"),
    IBM775("IBM775"),
    IBM850("IBM850"),
    IBM852("IBM852"),
    IBM855("IBM855"),
    IBM857("IBM857"),
    IBM858("IBM858"),
    IBM862("IBM862"),
    IBM866("IBM866"),
    IBM874("IBM874");

    private String charset;

    Charsets(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }

    public Charset getCharsets() {
        return Charset.forName(charset);
    }
}
