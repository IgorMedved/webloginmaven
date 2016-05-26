package beardreamembrace.webloginmaven;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TextPair implements WritableComparable<TextPair> {

	private Text mFirst;
	private Text mSecond;
	
	public TextPair()
	{
		set (new Text(), new Text());
	}
	
	public TextPair (String date, String url)
	{
		set (new Text (date), new Text(url));
	}
	
	
	public TextPair (Text date, Text url)
	{
		set(date, url);
	}
	
	public void set (Text date, Text url)
	{
		mFirst = date;
		mSecond = url;
	}
	
	
	public Text getDate() {
		return mFirst;
	}

	public Text getUrl() {
		return mSecond;
	}
	
	

	@Override
	public void readFields(DataInput in) throws IOException 
	{
		mFirst.readFields(in);
		mSecond.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException 
	{
		mFirst.write(out);
		mSecond.write(out);
	}

	
	
	@Override
	public String toString()
	{
		return mFirst + "\t" + mSecond;
	}

	@Override
	public int hashCode() 
	{
		return mFirst.hashCode() * 163 + mSecond.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TextPair other = (TextPair) obj;
		if (mFirst == null) {
			if (other.mFirst != null)
				return false;
		} else if (!mFirst.equals(other.mFirst))
			return false;
		if (mSecond == null) {
			if (other.mSecond != null)
				return false;
		} else if (!mSecond.equals(other.mSecond))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(TextPair rhs) 
	{
		int cmp = mFirst.compareTo(rhs.mFirst);
		
		if (cmp != 0) 
		{
			return cmp;
		}
		
		return mSecond.compareTo(rhs.mSecond);
	}
		
	

}
