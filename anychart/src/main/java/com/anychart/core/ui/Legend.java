package com.anychart.core.ui;

import com.anychart.APIlib;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.JsObject;
import com.anychart.core.Text;

import java.util.Locale;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import android.text.TextUtils;

// class
/**
 * Legend element.
 */
public class Legend extends Text {

    protected Legend() {

    }

    public static Legend instantiate() {
        return new Legend("new anychart.core.ui.legend()");
    }

    

    public Legend(String jsChart) {
        jsBase = "legend" + ++variableIndex;
        APIlib.getInstance().addJSLine(jsBase + " = " + jsChart + ";");
    }

    public String getJsBase() {
        return jsBase;
    }

    
    /**
     * Getter for legend align settings.
     */
    public void align() {
        APIlib.getInstance().addJSLine(jsBase + ".align();");
    }
    /**
     * Setter for legend align settings.
     */
    public com.anychart.core.ui.Legend align(com.anychart.enums.Align align) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".align(%s);", (align != null) ? align.getJsBase() : null));

        return this;
    }
    /**
     * Setter for legend align settings.
     */
    public com.anychart.core.ui.Legend align(String align) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".align(%s);", wrapQuotes(align)));

        return this;
    }
    /**
     * Getter for the legend background.
     */
    public com.anychart.core.ui.Background background() {
        return new com.anychart.core.ui.Background(jsBase + ".background()");
    }
    /**
     * Setter for the legend background.
     */
    public com.anychart.core.ui.Legend background(String settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".background(%s);", wrapQuotes(settings)));

        return this;
    }
    /**
     * Setter for the legend background.
     */
    public com.anychart.core.ui.Legend background(Boolean settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".background(%s);", settings));

        return this;
    }
    /**
     * Getter for the pointer events.
     */
    public void disablePointerEvents() {
        APIlib.getInstance().addJSLine(jsBase + ".disablePointerEvents();");
    }
    /**
     * Setter for the pointer events.
     */
    public com.anychart.core.ui.Legend disablePointerEvents(Boolean enabled) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".disablePointerEvents(%s);", enabled));

        return this;
    }
    /**
     * Gets a value for dragging.
     */
    public void drag() {
        APIlib.getInstance().addJSLine(jsBase + ".drag();");
    }
    /**
     * Allows to use drag for legend.
     */
    public com.anychart.core.ui.Legend drag(Boolean enabled) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".drag(%s);", enabled));

        return this;
    }
    /**
     * Getter for the element state (enabled or disabled).
     */
    public void enabled() {
        APIlib.getInstance().addJSLine(jsBase + ".enabled();");
    }
    /**
     * Setter for the element enabled state.
     */
    public com.anychart.core.ui.Legend enabled(Boolean enabled) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".enabled(%s);", enabled));

        return this;
    }
    /**
     * Getter for the text font color.
     */
    public void fontColor() {
        APIlib.getInstance().addJSLine(jsBase + ".fontColor();");
    }
    /**
     * Setter for the text font color.<br/>
{@link https://www.w3schools.com/html/html_colors.asp}
     */
    public com.anychart.core.ui.Legend fontColor(String color) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontColor(%s);", wrapQuotes(color)));

        return this;
    }
    /**
     * Getter for the text font decoration.
     */
    public void fontDecoration() {
        APIlib.getInstance().addJSLine(jsBase + ".fontDecoration();");
    }
    /**
     * Setter for the text font decoration.
     */
    public com.anychart.core.ui.Legend fontDecoration(com.anychart.graphics.vector.text.Decoration value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontDecoration(%s);", (value != null) ? value.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the text font decoration.
     */
    public com.anychart.core.ui.Legend fontDecoration(String value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontDecoration(%s);", wrapQuotes(value)));

        return this;
    }
    /**
     * Getter for the font family.
     */
    public void fontFamily() {
        APIlib.getInstance().addJSLine(jsBase + ".fontFamily();");
    }
    /**
     * Setter for the font family.
     */
    public com.anychart.core.ui.Legend fontFamily(String family) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontFamily(%s);", wrapQuotes(family)));

        return this;
    }
    /**
     * Getter for the text font opacity.
     */
    public void fontOpacity() {
        APIlib.getInstance().addJSLine(jsBase + ".fontOpacity();");
    }
    /**
     * Setter for the text font opacity. Double value from 0 to 1.
     */
    public com.anychart.core.ui.Legend fontOpacity(Number opacity) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontOpacity(%s);", opacity));

        return this;
    }
    /**
     * Getter for the text font size.
     */
    public void fontSize() {
        APIlib.getInstance().addJSLine(jsBase + ".fontSize();");
    }
    /**
     * Setter for the text font size.
     */
    public com.anychart.core.ui.Legend fontSize(String size) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontSize(%s);", wrapQuotes(size)));

        return this;
    }
    /**
     * Setter for the text font size.
     */
    public com.anychart.core.ui.Legend fontSize(Number size) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontSize(%s);", size));

        return this;
    }
    /**
     * Getter for the text font style.
     */
    public void fontStyle() {
        APIlib.getInstance().addJSLine(jsBase + ".fontStyle();");
    }
    /**
     * Setter for the text font style.
     */
    public com.anychart.core.ui.Legend fontStyle(com.anychart.graphics.vector.text.FontStyle style) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontStyle(%s);", (style != null) ? style.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the text font style.
     */
    public com.anychart.core.ui.Legend fontStyle(String style) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontStyle(%s);", wrapQuotes(style)));

        return this;
    }
    /**
     * Getter for the text font variant.
     */
    public void fontVariant() {
        APIlib.getInstance().addJSLine(jsBase + ".fontVariant();");
    }
    /**
     * Setter for the text font variant.
     */
    public com.anychart.core.ui.Legend fontVariant(com.anychart.graphics.vector.text.FontVariant value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontVariant(%s);", (value != null) ? value.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the text font variant.
     */
    public com.anychart.core.ui.Legend fontVariant(String value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontVariant(%s);", wrapQuotes(value)));

        return this;
    }
    /**
     * Getter for the text font weight.
     */
    public void fontWeight() {
        APIlib.getInstance().addJSLine(jsBase + ".fontWeight();");
    }
    /**
     * Setter for the text font weight.<br/>
{@link https://www.w3schools.com/cssref/pr_font_weight.asp}
     */
    public com.anychart.core.ui.Legend fontWeight(String weight) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontWeight(%s);", wrapQuotes(weight)));

        return this;
    }
    /**
     * Setter for the text font weight.<br/>
{@link https://www.w3schools.com/cssref/pr_font_weight.asp}
     */
    public com.anychart.core.ui.Legend fontWeight(Number weight) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".fontWeight(%s);", weight));

        return this;
    }
    /**
     * Returns pixel bounds of the legend.
     */
    public com.anychart.math.Rect getPixelBounds() {
        return new com.anychart.math.Rect(jsBase + ".getPixelBounds()");
    }
    /**
     * Getter for remain bounds after legend.
     */
    public com.anychart.math.Rect getRemainingBounds() {
        return new com.anychart.math.Rect(jsBase + ".getRemainingBounds()");
    }
    /**
     * Getter for the text horizontal align.
     */
    public void hAlign() {
        APIlib.getInstance().addJSLine(jsBase + ".hAlign();");
    }
    /**
     * Setter for the text horizontal align.
     */
    public com.anychart.core.ui.Legend hAlign(com.anychart.graphics.vector.text.HAlign align) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".hAlign(%s);", (align != null) ? align.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the text horizontal align.
     */
    public com.anychart.core.ui.Legend hAlign(String align) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".hAlign(%s);", wrapQuotes(align)));

        return this;
    }
    /**
     * Getter for the legend height.
     */
    public void height() {
        APIlib.getInstance().addJSLine(jsBase + ".height();");
    }
    /**
     * Setter for the legend height.
     */
    public com.anychart.core.ui.Legend height(Number height) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".height(%s);", height));

        return this;
    }
    /**
     * Setter for the legend height.
     */
    public com.anychart.core.ui.Legend height(String height) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".height(%s);", wrapQuotes(height)));

        return this;
    }
    /**
     * Getter for hover cursor settings.
     */
    public void hoverCursor() {
        APIlib.getInstance().addJSLine(jsBase + ".hoverCursor();");
    }
    /**
     * Setter for hover cursor settings.
     */
    public com.anychart.core.ui.Legend hoverCursor(com.anychart.enums.Cursor cursorTypr) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".hoverCursor(%s);", (cursorTypr != null) ? cursorTypr.getJsBase() : null));

        return this;
    }
    /**
     * Setter for hover cursor settings.
     */
    public com.anychart.core.ui.Legend hoverCursor(String cursorTypr) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".hoverCursor(%s);", wrapQuotes(cursorTypr)));

        return this;
    }
    /**
     * Getter for the icon size.
     */
    public void iconSize() {
        APIlib.getInstance().addJSLine(jsBase + ".iconSize();");
    }
    /**
     * Setter for the icon size.
     */
    public com.anychart.core.ui.Legend iconSize(Number size) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".iconSize(%s);", size));

        return this;
    }
    /**
     * Setter for the icon size.
     */
    public com.anychart.core.ui.Legend iconSize(String size) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".iconSize(%s);", wrapQuotes(size)));

        return this;
    }
    /**
     * Getter for spacing between icon and text in a legend item.
     */
    public void iconTextSpacing() {
        APIlib.getInstance().addJSLine(jsBase + ".iconTextSpacing();");
    }
    /**
     * Setter for spacing between icon and text in a legend item.
     */
    public com.anychart.core.ui.Legend iconTextSpacing(String spacing) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".iconTextSpacing(%s);", wrapQuotes(spacing)));

        return this;
    }
    /**
     * Setter for spacing between icon and text in a legend item.
     */
    public com.anychart.core.ui.Legend iconTextSpacing(Number spacing) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".iconTextSpacing(%s);", spacing));

        return this;
    }
    /**
     * Getter for inverted settings.
     */
    public void inverted() {
        APIlib.getInstance().addJSLine(jsBase + ".inverted();");
    }
    /**
     * Setter for inverted settings.
     */
    public com.anychart.core.ui.Legend inverted(Boolean enabled) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".inverted(%s);", enabled));

        return this;
    }
    /**
     * Getter for custom items.
     */
    public void items() {
        APIlib.getInstance().addJSLine(jsBase + ".items();");
    }
    /**
     * Setter for custom items.
     */
    public com.anychart.core.ui.Legend items(com.anychart.core.ui.legend.LegendItemProvider[] itemsList) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".items(%s);", arrayToString(itemsList)));

        return this;
    }
    /**
     * Getter for items text formatter.
     */
    public void itemsFormat() {
        APIlib.getInstance().addJSLine(jsBase + ".itemsFormat();");
    }
    /**
     * Setter for items text formatter.<br/>
{docs:Stock_Charts/Legend#items}Learn more about using itemsFormat() method.{docs}
     */
    public com.anychart.core.ui.Legend itemsFormat(String format) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".itemsFormat(%s);", wrapQuotes(format)));

        return this;
    }
    /**
     * Getter for items formatter.
     */
    public void itemsFormatter() {
        APIlib.getInstance().addJSLine(jsBase + ".itemsFormatter();");
    }
    /**
     * Setter for items formatter.
     */
    public com.anychart.core.ui.Legend itemsFormatter(com.anychart.core.ui.legend.LegendItemProvider[] formatterFunction) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".itemsFormatter(%s);", arrayToString(formatterFunction)));

        return this;
    }
    /**
     * Getter for items layout.
     */
    public void itemsLayout() {
        APIlib.getInstance().addJSLine(jsBase + ".itemsLayout();");
    }
    /**
     * Setter for items layout.
     */
    public com.anychart.core.ui.Legend itemsLayout(com.anychart.enums.LegendLayout layout) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".itemsLayout(%s);", (layout != null) ? layout.getJsBase() : null));

        return this;
    }
    /**
     * Setter for items layout.
     */
    public com.anychart.core.ui.Legend itemsLayout(String layout) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".itemsLayout(%s);", wrapQuotes(layout)));

        return this;
    }
    /**
     * Getter for items source mode.
     */
    public void itemsSourceMode() {
        APIlib.getInstance().addJSLine(jsBase + ".itemsSourceMode();");
    }
    /**
     * Setter for items source mode.
     */
    public com.anychart.core.ui.Legend itemsSourceMode(com.anychart.enums.LegendItemsSourceMode mode) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".itemsSourceMode(%s);", (mode != null) ? mode.getJsBase() : null));

        return this;
    }
    /**
     * Setter for items source mode.
     */
    public com.anychart.core.ui.Legend itemsSourceMode(String mode) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".itemsSourceMode(%s);", wrapQuotes(mode)));

        return this;
    }
    /**
     * Getter for items spacing settings.
     */
    public void itemsSpacing() {
        APIlib.getInstance().addJSLine(jsBase + ".itemsSpacing();");
    }
    /**
     * Setter for items spacing settings.
     */
    public com.anychart.core.ui.Legend itemsSpacing(String spacing) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".itemsSpacing(%s);", wrapQuotes(spacing)));

        return this;
    }
    /**
     * Setter for items spacing settings.
     */
    public com.anychart.core.ui.Legend itemsSpacing(Number spacing) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".itemsSpacing(%s);", spacing));

        return this;
    }
    /**
     * Getter for the text letter spacing.
     */
    public void letterSpacing() {
        APIlib.getInstance().addJSLine(jsBase + ".letterSpacing();");
    }
    /**
     * Setter for the text letter spacing.<br/>
{@link https://www.w3schools.com/cssref/pr_text_letter-spacing.asp}
     */
    public com.anychart.core.ui.Legend letterSpacing(String spacing) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".letterSpacing(%s);", wrapQuotes(spacing)));

        return this;
    }
    /**
     * Setter for the text letter spacing.<br/>
{@link https://www.w3schools.com/cssref/pr_text_letter-spacing.asp}
     */
    public com.anychart.core.ui.Legend letterSpacing(Number spacing) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".letterSpacing(%s);", spacing));

        return this;
    }
    /**
     * Getter for the text line height.
     */
    public void lineHeight() {
        APIlib.getInstance().addJSLine(jsBase + ".lineHeight();");
    }
    /**
     * Setter for the text line height.<br/>
{@link https://www.w3schools.com/cssref/pr_dim_line-height.asp}
     */
    public com.anychart.core.ui.Legend lineHeight(String height) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".lineHeight(%s);", wrapQuotes(height)));

        return this;
    }
    /**
     * Setter for the text line height.<br/>
{@link https://www.w3schools.com/cssref/pr_dim_line-height.asp}
     */
    public com.anychart.core.ui.Legend lineHeight(Number height) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".lineHeight(%s);", height));

        return this;
    }
    /**
     * Getter for margin settings.
     */
    public com.anychart.core.utils.Margin margin() {
        return new com.anychart.core.utils.Margin(jsBase + ".margin()");
    }
    /**
     * Setter for the legend margin in pixels using a single value.
     */
    public com.anychart.core.ui.Legend margin(Number[] margin) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s);", Arrays.toString(margin)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single value.
     */
    public com.anychart.core.ui.Legend margin(String[] margin) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s);", arrayToStringWrapQuotes(margin)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single value.
     */
    public com.anychart.core.ui.Legend margin(String margin) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s);", wrapQuotes(margin)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single value.
     */
    public com.anychart.core.ui.Legend margin(Number margin) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s);", margin));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(String value1, String value2, String value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", wrapQuotes(value1), wrapQuotes(value2), wrapQuotes(value3), wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(String value1, String value2, String value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", wrapQuotes(value1), wrapQuotes(value2), wrapQuotes(value3), value4));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(String value1, String value2, Number value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", wrapQuotes(value1), wrapQuotes(value2), value3, wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(String value1, String value2, Number value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", wrapQuotes(value1), wrapQuotes(value2), value3, value4));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(String value1, Number value2, String value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", wrapQuotes(value1), value2, wrapQuotes(value3), wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(String value1, Number value2, String value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", wrapQuotes(value1), value2, wrapQuotes(value3), value4));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(String value1, Number value2, Number value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", wrapQuotes(value1), value2, value3, wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(String value1, Number value2, Number value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", wrapQuotes(value1), value2, value3, value4));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(Number value1, String value2, String value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", value1, wrapQuotes(value2), wrapQuotes(value3), wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(Number value1, String value2, String value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", value1, wrapQuotes(value2), wrapQuotes(value3), value4));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(Number value1, String value2, Number value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", value1, wrapQuotes(value2), value3, wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(Number value1, String value2, Number value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", value1, wrapQuotes(value2), value3, value4));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(Number value1, Number value2, String value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", value1, value2, wrapQuotes(value3), wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(Number value1, Number value2, String value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", value1, value2, wrapQuotes(value3), value4));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(Number value1, Number value2, Number value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", value1, value2, value3, wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend margin in pixels using a single simple values.
     */
    public com.anychart.core.ui.Legend margin(Number value1, Number value2, Number value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".margin(%s, %s, %s, %s);", value1, value2, value3, value4));

        return this;
    }
    /**
     * Getter for the maximum height.
     */
    public void maxHeight() {
        APIlib.getInstance().addJSLine(jsBase + ".maxHeight();");
    }
    /**
     * Setter for the maximum height.
     */
    public com.anychart.core.ui.Legend maxHeight(Number height) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".maxHeight(%s);", height));

        return this;
    }
    /**
     * Setter for the maximum height.
     */
    public com.anychart.core.ui.Legend maxHeight(String height) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".maxHeight(%s);", wrapQuotes(height)));

        return this;
    }
    /**
     * Getter for the maximum width.
     */
    public void maxWidth() {
        APIlib.getInstance().addJSLine(jsBase + ".maxWidth();");
    }
    /**
     * Setter for the maximum width.
     */
    public com.anychart.core.ui.Legend maxWidth(Number width) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".maxWidth(%s);", width));

        return this;
    }
    /**
     * Setter for the maximum width.
     */
    public com.anychart.core.ui.Legend maxWidth(String width) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".maxWidth(%s);", wrapQuotes(width)));

        return this;
    }
    /**
     * Getter for legend padding settings.
     */
    public com.anychart.core.utils.Padding padding() {
        return new com.anychart.core.utils.Padding(jsBase + ".padding()");
    }
    /**
     * Setter for the legend padding in pixels using a single value.
     */
    public com.anychart.core.ui.Legend padding(Number[] padding) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s);", Arrays.toString(padding)));

        return this;
    }
    /**
     * Setter for the legend padding in pixels using a single value.
     */
    public com.anychart.core.ui.Legend padding(String[] padding) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s);", arrayToStringWrapQuotes(padding)));

        return this;
    }
    /**
     * Setter for the legend padding in pixels using a single value.
     */
    public com.anychart.core.ui.Legend padding(String padding) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s);", wrapQuotes(padding)));

        return this;
    }
    /**
     * Setter for the legend padding in pixels using a single value.
     */
    public com.anychart.core.ui.Legend padding(Number padding) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s);", padding));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(String value1, String value2, String value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", wrapQuotes(value1), wrapQuotes(value2), wrapQuotes(value3), wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(String value1, String value2, String value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", wrapQuotes(value1), wrapQuotes(value2), wrapQuotes(value3), value4));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(String value1, String value2, Number value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", wrapQuotes(value1), wrapQuotes(value2), value3, wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(String value1, String value2, Number value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", wrapQuotes(value1), wrapQuotes(value2), value3, value4));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(String value1, Number value2, String value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", wrapQuotes(value1), value2, wrapQuotes(value3), wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(String value1, Number value2, String value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", wrapQuotes(value1), value2, wrapQuotes(value3), value4));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(String value1, Number value2, Number value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", wrapQuotes(value1), value2, value3, wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(String value1, Number value2, Number value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", wrapQuotes(value1), value2, value3, value4));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(Number value1, String value2, String value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", value1, wrapQuotes(value2), wrapQuotes(value3), wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(Number value1, String value2, String value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", value1, wrapQuotes(value2), wrapQuotes(value3), value4));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(Number value1, String value2, Number value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", value1, wrapQuotes(value2), value3, wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(Number value1, String value2, Number value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", value1, wrapQuotes(value2), value3, value4));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(Number value1, Number value2, String value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", value1, value2, wrapQuotes(value3), wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(Number value1, Number value2, String value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", value1, value2, wrapQuotes(value3), value4));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(Number value1, Number value2, Number value3, String value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", value1, value2, value3, wrapQuotes(value4)));

        return this;
    }
    /**
     * Setter for the legend padding setting in pixels using a several value.
     */
    public com.anychart.core.ui.Legend padding(Number value1, Number value2, Number value3, Number value4) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".padding(%s, %s, %s, %s);", value1, value2, value3, value4));

        return this;
    }
    /**
     * Getter for paginator settings.
     */
    public com.anychart.core.ui.Paginator paginator() {
        return new com.anychart.core.ui.Paginator(jsBase + ".paginator()");
    }
    /**
     * Setter for paginator settings.
     */
    public com.anychart.core.ui.Legend paginator(String settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".paginator(%s);", wrapQuotes(settings)));

        return this;
    }
    /**
     * Setter for paginator settings.
     */
    public com.anychart.core.ui.Legend paginator(Boolean settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".paginator(%s);", settings));

        return this;
    }
    /**
     * Getter for legend position settings.
     */
    public void position() {
        APIlib.getInstance().addJSLine(jsBase + ".position();");
    }
    /**
     * Setter for legend position setting.
     */
    public com.anychart.core.ui.Legend position(com.anychart.enums.Orientation position) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".position(%s);", (position != null) ? position.getJsBase() : null));

        return this;
    }
    /**
     * Setter for legend position setting.
     */
    public com.anychart.core.ui.Legend position(String position) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".position(%s);", wrapQuotes(position)));

        return this;
    }
    /**
     * Getter for the position mode.
     */
    public void positionMode() {
        APIlib.getInstance().addJSLine(jsBase + ".positionMode();");
    }
    /**
     * Setter for the position mode.
     */
    public com.anychart.core.ui.Legend positionMode(com.anychart.enums.LegendPositionMode mode) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".positionMode(%s);", (mode != null) ? mode.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the position mode.
     */
    public com.anychart.core.ui.Legend positionMode(String mode) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".positionMode(%s);", wrapQuotes(mode)));

        return this;
    }
    /**
     * Prints all elements on related stage.
     */
    public void print(com.anychart.graphics.vector.PaperSize paperSizeOrOptions, Boolean landscape) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".print(%s, %s);", (paperSizeOrOptions != null) ? paperSizeOrOptions.getJsBase() : null, landscape));
    }
    /**
     * Prints all elements on related stage.
     */
    public void print(String paperSizeOrOptions, Boolean landscape) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".print(%s, %s);", wrapQuotes(paperSizeOrOptions), landscape));
    }
    /**
     * Removes all listeners from an object. You can also optionally remove listeners of some particular type.
     */
    public void removeAllListeners(String type) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".removeAllListeners(%s);", wrapQuotes(type)));
    }
    /**
     * Getter for the text selectable option.
     */
    public void selectable() {
        APIlib.getInstance().addJSLine(jsBase + ".selectable();");
    }
    /**
     * Setter for the text selectable.
     */
    public com.anychart.core.ui.Legend selectable(Boolean enabled) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".selectable(%s);", enabled));

        return this;
    }
    /**
     * Getter for the text direction.
     */
    public void textDirection() {
        APIlib.getInstance().addJSLine(jsBase + ".textDirection();");
    }
    /**
     * Setter for the text direction.
     */
    public com.anychart.core.ui.Legend textDirection(com.anychart.graphics.vector.text.Direction direction) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".textDirection(%s);", (direction != null) ? direction.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the text direction.
     */
    public com.anychart.core.ui.Legend textDirection(String direction) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".textDirection(%s);", wrapQuotes(direction)));

        return this;
    }
    /**
     * Getter for the text indent.
     */
    public void textIndent() {
        APIlib.getInstance().addJSLine(jsBase + ".textIndent();");
    }
    /**
     * Setter for the text indent.
     */
    public com.anychart.core.ui.Legend textIndent(Number indent) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".textIndent(%s);", indent));

        return this;
    }
    /**
     * Getter for the text overflow settings.
     */
    public void textOverflow() {
        APIlib.getInstance().addJSLine(jsBase + ".textOverflow();");
    }
    /**
     * Setter for the text overflow settings.
     */
    public com.anychart.core.ui.Legend textOverflow(com.anychart.graphics.vector.text.TextOverflow value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".textOverflow(%s);", (value != null) ? value.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the text overflow settings.
     */
    public com.anychart.core.ui.Legend textOverflow(String value) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".textOverflow(%s);", wrapQuotes(value)));

        return this;
    }
    /**
     * Getter for the full text appearance settings.
     */
    public void textSettings() {
        APIlib.getInstance().addJSLine(jsBase + ".textSettings();");
    }
    /**
     * Getter for all text appearance settings.
     */
    public void textSettings(String name) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".textSettings(%s);", wrapQuotes(name)));
    }
    /**
     * Setter for the text appearance settings.
     */
    public com.anychart.core.ui.Legend textSettings(String name, String settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".textSettings(%s, %s);", wrapQuotes(name), wrapQuotes(settings)));

        return this;
    }
    /**
     * Setter for the text appearance settings.
     */
    public com.anychart.core.ui.Legend textSettings(String name, Number settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".textSettings(%s, %s);", wrapQuotes(name), settings));

        return this;
    }
    /**
     * Setter for the text appearance settings.
     */
    public com.anychart.core.ui.Legend textSettings(String name, Boolean settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".textSettings(%s, %s);", wrapQuotes(name), settings));

        return this;
    }
    /**
     * Getter for the legend title.
     */
    public com.anychart.core.ui.Title title() {
        return new com.anychart.core.ui.Title(jsBase + ".title()");
    }
    /**
     * Setter for the legend title.
     */
    public com.anychart.core.ui.Legend title(Boolean settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".title(%s);", settings));

        return this;
    }
    /**
     * Setter for the legend title.
     */
    public com.anychart.core.ui.Legend title(String settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".title(%s);", wrapQuotes(settings)));

        return this;
    }
    /**
     * Getter for the legend title format function.
     */
    public void titleFormat() {
        APIlib.getInstance().addJSLine(jsBase + ".titleFormat();");
    }
    /**
     * Setter for the legend title format function.
If set, formats title. Currently supported in Stock only.
{docs:Common_Settings/Text_Formatters}Learn more about using titleFormat() method.{docs}
     */
    public com.anychart.core.ui.Legend titleFormat(String format) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".titleFormat(%s);", wrapQuotes(format)));

        return this;
    }
    /**
     * Getter for title separator settings.
     */
    public com.anychart.core.ui.Separator titleSeparator() {
        return new com.anychart.core.ui.Separator(jsBase + ".titleSeparator()");
    }
    /**
     * Setter for title separator settings.
     */
    public com.anychart.core.ui.Legend titleSeparator(String settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".titleSeparator(%s);", wrapQuotes(settings)));

        return this;
    }
    /**
     * Setter for title separator settings.
     */
    public com.anychart.core.ui.Legend titleSeparator(Boolean settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".titleSeparator(%s);", settings));

        return this;
    }
    /**
     * Getter for the legend tooltip.
     */
    public com.anychart.core.ui.Tooltip tooltip() {
        return new com.anychart.core.ui.Tooltip(jsBase + ".tooltip()");
    }
    /**
     * Setter for legend tooltip.
     */
    public com.anychart.core.ui.Legend tooltip(String settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".tooltip(%s);", wrapQuotes(settings)));

        return this;
    }
    /**
     * Setter for legend tooltip.
     */
    public com.anychart.core.ui.Legend tooltip(Boolean settings) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".tooltip(%s);", settings));

        return this;
    }
    public void setOnClickListener(com.anychart.chart.common.listener.ListenersInterface.OnClickListener listener) {
        StringBuilder js = new StringBuilder();

        js.append(jsBase).append(".listen('pointClick', function(e) {");

        if (listener.getFields() != null) {
            js.append("var result = ");
            for (String field : listener.getFields()) {
                js.append(String.format(Locale.US, "'%1$s' + ':' + e.point.get('%1$s') + ',' +", field));
            }
            js.setLength(js.length() - 8);
            js.append(";");

            js.append("android.onClick(result);");
        } else {
            js.append("android.onClick(null);");
        }
        js.append("});");

        com.anychart.chart.common.listener.ListenersInterface.getInstance().setOnClickListener(listener);

        APIlib.getInstance().addJSLine(js.toString());
    }
    /**
     * Removes an event listener which was added with listen() by the key returned by listen() or listenOnce().
     */
    public void unlistenByKey(String key) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".unlistenByKey(%s);", wrapQuotes(key)));
    }
    /**
     * Getter for the useHTML flag.
     */
    public void useHtml() {
        APIlib.getInstance().addJSLine(jsBase + ".useHtml();");
    }
    /**
     * Setter for flag useHTML.
     */
    public com.anychart.core.ui.Legend useHtml(Boolean enabled) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".useHtml(%s);", enabled));

        return this;
    }
    /**
     * Getter for the text vertical align.
     */
    public void vAlign() {
        APIlib.getInstance().addJSLine(jsBase + ".vAlign();");
    }
    /**
     * Setter for the text vertical align.
     */
    public com.anychart.core.ui.Legend vAlign(com.anychart.graphics.vector.text.VAlign align) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".vAlign(%s);", (align != null) ? align.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the text vertical align.
     */
    public com.anychart.core.ui.Legend vAlign(String align) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".vAlign(%s);", wrapQuotes(align)));

        return this;
    }
    /**
     * Getter for the legend width.
     */
    public void width() {
        APIlib.getInstance().addJSLine(jsBase + ".width();");
    }
    /**
     * Setter for the legend width.
     */
    public com.anychart.core.ui.Legend width(Number width) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".width(%s);", width));

        return this;
    }
    /**
     * Setter for the legend width.
     */
    public com.anychart.core.ui.Legend width(String width) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".width(%s);", wrapQuotes(width)));

        return this;
    }
    /**
     * Getter for the word-break mode.
     */
    public void wordBreak() {
        APIlib.getInstance().addJSLine(jsBase + ".wordBreak();");
    }
    /**
     * Setter for the word-break mode.
     */
    public com.anychart.core.ui.Legend wordBreak(com.anychart.enums.WordBreak mode) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".wordBreak(%s);", (mode != null) ? mode.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the word-break mode.
     */
    public com.anychart.core.ui.Legend wordBreak(String mode) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".wordBreak(%s);", wrapQuotes(mode)));

        return this;
    }
    /**
     * Getter for the word-wrap mode.
     */
    public void wordWrap() {
        APIlib.getInstance().addJSLine(jsBase + ".wordWrap();");
    }
    /**
     * Setter for the word-wrap mode.
     */
    public com.anychart.core.ui.Legend wordWrap(com.anychart.enums.WordWrap mode) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".wordWrap(%s);", (mode != null) ? mode.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the word-wrap mode.
     */
    public com.anychart.core.ui.Legend wordWrap(String mode) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".wordWrap(%s);", wrapQuotes(mode)));

        return this;
    }
    /**
     * Getter for the Z-index of the element.
     */
    public void zIndex() {
        APIlib.getInstance().addJSLine(jsBase + ".zIndex();");
    }
    /**
     * Setter for the Z-index of the element.
     */
    public com.anychart.core.ui.Legend zIndex(Number zIndex) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".zIndex(%s);", zIndex));

        return this;
    }
    /**
     * Getter for the container.
     */
    public com.anychart.graphics.vector.Layer container() {
        return new com.anychart.graphics.vector.Layer(jsBase + ".container()");
    }
    /**
     * Setter for the container.
     */
    public com.anychart.core.ui.Legend container(com.anychart.graphics.vector.Layer element) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".container(%s);", (element != null) ? element.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the container.
     */
    public com.anychart.core.ui.Legend container(com.anychart.graphics.vector.Stage element) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".container(%s);", (element != null) ? element.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the container.
     */
    public com.anychart.core.ui.Legend container(String element) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".container(%s);", wrapQuotes(element)));

        return this;
    }
    /**
     * Getter for the parent bounds.<br>
Bounds that would be used in case of percent size calculations. Expects pixel values only.
     */
    public com.anychart.math.Rect parentBounds() {
        return new com.anychart.math.Rect(jsBase + ".parentBounds()");
    }
    /**
     * Setter for the parent bounds using single value.<br>
Bounds that would be used in case of percent size calculations. Expects pixel values only.
     */
    public com.anychart.core.ui.Legend parentBounds(com.anychart.math.Rect bounds) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".parentBounds(%s);", (bounds != null) ? bounds.getJsBase() : null));

        return this;
    }
    /**
     * Setter for the parent bounds using single value.<br>
Bounds that would be used in case of percent size calculations. Expects pixel values only.
     */
    public com.anychart.core.ui.Legend parentBounds(String bounds) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".parentBounds(%s);", wrapQuotes(bounds)));

        return this;
    }
    /**
     * Setter for the parent bounds using single value.<br>
Bounds that would be used in case of percent size calculations. Expects pixel values only.
     */
    public com.anychart.core.ui.Legend parentBounds(Number bounds) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".parentBounds(%s);", bounds));

        return this;
    }
    /**
     * Setter for the parent bounds using several values.<br>
Bounds that would be used in case of percent size calculations. Expects pixel values only.
     */
    public com.anychart.core.ui.Legend parentBounds(Number left, Number top, Number width, Number height) {
        APIlib.getInstance().addJSLine(String.format(Locale.US, jsBase + ".parentBounds(%s, %s, %s, %s);", left, top, width, height));

        return this;
    }

}