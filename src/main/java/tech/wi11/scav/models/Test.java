package tech.wi11.scav.models;

import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a SpeedCurve test.
 */
public class Test {
	public String timezone;	
	
	public String day;
	
	public long timestamp;
	
	public String test;
	
	public String region;
	
	public int run;
	
	public String browser;
	
	public String browser_version;
	
	public int viewport_width;
			
	public int viewport_height;
	
	@SerializedName("byte")
	public long bytes;
	
	public long render;
	
	public long visually_complete;
	
	public long dom;
	
	public long loaded;

    public long size;

    public long image_saving;

    public long requests;

    public long pagespeed;

    public long speedindex;

    public long html_requests;

    public long html_size;

    public long css_requests;

    public long css_size;

    public long js_requests;

    public long js_size;

    public long image_requests;

    public long image_size;

    public long font_requests;

    public long font_size;

    public long text_requests;

    public long text_size;

    public long flash_requests;

    public long flash_size;

    public long other_requests;

    public long other_size;
	
	public String har;
	
	public String screen;

	/**
	 * Returns a tab-separated String representation of the test.
	 * @return 
	 */
	public String getTextRow() {
		SimpleDateFormat formatter = new SimpleDateFormat("y.M.d k:m");
		return String.format("%s\t%d\t%s\t%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%s\t%s",
					formatter.format(new Date(this.timestamp * 1000)),
					this.run,
					this.browser,
					this.browser_version,
					this.viewport_width,
					this.viewport_height,
					this.bytes,
					this.render,
					this.visually_complete,
					this.dom,
					this.loaded,
					this.size,
					this.image_saving,
					this.requests,
					this.pagespeed,
					this.speedindex,
					this.html_requests,
					this.html_size,
					this.css_requests,
					this.css_size,
					this.js_requests,
					this.js_size,
					this.image_requests,
					this.image_size,
					this.font_requests,
					this.font_size,
					this.text_requests,
					this.text_size,
					this.flash_requests,
					this.flash_size,
					this.other_requests,
					this.other_size,
					this.har,
					this.screen);
	}
}
