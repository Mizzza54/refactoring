package ru.akirakozov.sd.refactoring.utils;

/**
 * @author Michael Gerasimov
 */
public class HtmlBuilder {
    public static String addHtml(String renderTag) {
        return addTag(HtmlTags.HTML, renderTag);
    }

    public static String addBody(String renderTag) {
        return addTag(HtmlTags.BODY, renderTag);
    }

    public static String addH1(String text) {
        return addTag(HtmlTags.H1, text);
    }

    public static String addBr(String text) {
        return addTag(HtmlTags.BR, text);
    }

    private static String addTag(HtmlTags tag, String innerRenderTag) {
        return (tag.hasStartTag() ? getOpenTag(tag) : "") + innerRenderTag + getCloseTag(tag);
    }

    private static String getOpenTag(HtmlTags tag) {
        return String.format(tag.needStartLineBreak() ? "<%s>\n" : "<%s>", tag.getExternalName());
    }

    private static String getCloseTag(HtmlTags tag) {
        return String.format(tag.needEndLineBreak() ? "</%s>\n" : "</%s>", tag.getExternalName());
    }
}
